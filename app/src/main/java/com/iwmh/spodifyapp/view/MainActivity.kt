package com.iwmh.spodifyapp.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iwmh.spodifyapp.repository.model.Secret
import com.iwmh.spodifyapp.ui.theme.SpodifyappTheme
import com.iwmh.spodifyapp.util.Util
import com.iwmh.spodifyapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.openid.appauth.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Viewmodel for this activity.
    private val mainViewModel: MainViewModel by viewModels()

    // launcher to launch the activity for login screen.
    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->

            // If the user canceled, then return immediately
            if(activityResult.resultCode != Activity.RESULT_OK){
                return@registerForActivityResult
            }

            // Extract the authorization response and exception.
            val data = activityResult.data
            val resp = AuthorizationResponse.fromIntent(data!!)
            val ex = AuthorizationException.fromIntent(data)

            if(resp == null){
                Log.e("AuthorizationActivity", ex.toString())
                throw ex!!
            }

            // update the AuthState
            mainViewModel.updateAuthStateFromAuthResponse(resp, ex)

            // Check if the "state" matches precalculated state value.
            if(resp.state != mainViewModel.stateValue()){
                throw  Exception("Detected mismatch with state value you calculated. " + ex.toString())
            }

            // Clear the state after confirming you received the expected state.
            mainViewModel.setStateValue("")

            // Exchange the authorization code
            mainViewModel.exchangeAuthorizationCode(resp)

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Read AuthState from SharedPreferences and set it the viewmodel.
        val stateJson = mainViewModel.readAuthStateStringFromSharedPreferences()

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.spotify.com/authorize"),  // authorization endpoint
            Uri.parse("https://accounts.spotify.com/api/token"),  // token endpoint
        )

        if (stateJson != null){
            // AuthState successfully read from SharedPreferences.
            // Set it in the viewmodel.
            mainViewModel.setNewAuthState(AuthState.jsonDeserialize(stateJson))
        } else {
            // AuthState isn't stored in SharedPreferences, so the user hasn't been logged in.
            // Set the initial AuthState in the viewmodel.
            mainViewModel.setNewAuthState(AuthState(serviceConfig))
        }

        // Check if the AuthState is valid
        if(mainViewModel.isAuthorized()){
            // If the user's already logged in, show the home page.
            setContent {
                SpodifyApp(name = "Hiroshi", viewModel = mainViewModel)
            }
        } else {
            // Otherwise, show the login page.

            // ---------- ↓ AppAuth Preparation ↓ ----------
            // prepare for PKCE info
            val codeVerifier = CodeVerifierUtil.generateRandomCodeVerifier()
            val codeChallenge = CodeVerifierUtil.deriveCodeVerifierChallenge(codeVerifier)

            // Calculate the state value
            mainViewModel.calculateStateValue()

            // get secret.json file from "assets" folder
            val secretString = Util.loadJSONFromAsset(this, "secret.json")

            // deserialize the json string to Secret data class
            val secretData = Json.decodeFromString<Secret>(secretString ?: "")

            val authRequest = AuthorizationRequest.Builder(
                serviceConfig,                      // the authorization service configuration
                secretData.client_id,               // the client ID, typically pre-registered and static
                ResponseTypeValues.CODE,            // the response_type value: we want a code
                Uri.parse(secretData.redirect_url)  // the redirect URI to which the auth response is sent
            ).setScope(                             // the scopes
                "user-modify-playback-state " +
                        "user-library-modify " +
                        "playlist-read-private " +
                        "playlist-modify-public " +
                        "playlist-modify-private " +
                        "user-read-playback-state " +
                        "user-read-currently-playing"
            ).setCodeVerifier(
                codeVerifier,
                codeChallenge,
                CodeVerifierUtil.getCodeVerifierChallengeMethod()
            ).setState(
                mainViewModel.stateValue()
            ).build()

            // ---------- ↑ AppAuth Preparation ↑ ----------

            setContent {
                AuthPage(
                    viewModel= mainViewModel,
                    authRequest = authRequest,
                    launcher = launcher
                )
            }
        }

    }

}

@Composable
fun SpodifyApp(name: String, viewModel: MainViewModel){
    SpodifyappTheme {
        // NavController
        val navController = rememberNavController()

        Scaffold {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                SpodifyNavHost(
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }

    }

}

@Composable
fun SpodifyNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ){
        composable("Home"){
            Greeting(name = "Hiroshi")
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = " a list of Spotify podcast information .....")
}

@Composable
fun AuthPage(
    viewModel: MainViewModel,
    authRequest: AuthorizationRequest,
    launcher: ActivityResultLauncher<Intent>
){
    Button(
        onClick = {
            val authIntent = viewModel.getAuthorizationRequestIntent(authRequest)
            launcher.launch(authIntent)
        },
    ) {
        Text(text = "auth")
    }
}
