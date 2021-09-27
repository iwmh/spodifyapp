package com.iwmh.spodifyapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.iwmh.spodifyapp.repository.model.Secret
import com.iwmh.spodifyapp.util.Util
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.openid.appauth.*

class AuthorizationActivity : ComponentActivity(){

    // AuthorizationService and AuthorizationRequest for AppAuth
    private lateinit var authService: AuthorizationService
    private lateinit var authRequest: AuthorizationRequest

    // AuthState
    private lateinit var authState: AuthState

    // State for AppAuth
    private var stateValue = ""

    companion object {
        fun createIntent(context: Context): Intent{
            return Intent(context, AuthorizationActivity::class.java)
        }
    }

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
            val ex = AuthorizationException.fromIntent(data!!)
            if(resp != null){
                // update the AuthState
                authState.update(resp, ex)

                // Check if the "state" matches precalculated state value.
                if(resp.state != this.stateValue){
                    throw  Exception("Detected mismatch with state value you calculated.")
                }

                // Exchange the authorization code
                authService.performTokenRequest(
                    resp.createTokenExchangeRequest()
                ) { tokenResp, tokenEx ->
                    // update the AuthState
                    authState.update(tokenResp, tokenEx)
                    //
                    if (tokenResp != null){

                    } else {

                    }
                }

            } else {
                Log.e("AuthorizationActivity", ex.toString())
                throw ex!!
            }



        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ↓ AppAuth Preparation ↓
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.spotify.com/authorize"),  // authorization endpoint
            Uri.parse("https://accounts.spotify.com/api/token"),  // token endpoint
        )

        // seed an AuthState instance.
        authState = AuthState(serviceConfig)

        // prepare for PKCE info
        val codeVerifier = CodeVerifierUtil.generateRandomCodeVerifier()
        val codeChallenge = CodeVerifierUtil.deriveCodeVerifierChallenge(codeVerifier)
        val randomStringSource = "abcdefghijklmnopqrstuvwxfzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_.-~"
        for(x in 0 .. 127) stateValue += randomStringSource.random()

        // get secret.json file from "assets" folder
        val secretString = Util.loadJSONFromAsset(this, "secret.json")

        // deserialize the json string to Secret data class
        val secretData = Json.decodeFromString<Secret>(secretString ?: "")

        authRequest = AuthorizationRequest.Builder(
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
        ).setCodeVerifier(                      //
            codeVerifier,
            codeChallenge,
            CodeVerifierUtil.getCodeVerifierChallengeMethod()
        ).setState(
            stateValue
        ).build()

        authService = AuthorizationService(this)
        // ↑ AppAuth Preparation ↑

        setContent {
            AuthPage(
                authService = authService,
                authRequest = authRequest,
                launcher = launcher
            )
        }
    }

    // dispose the AuthorizationService.
    override fun onDestroy() {
        super.onDestroy()
        authService.dispose()
    }
}

@Composable
fun AuthPage(
    authService: AuthorizationService,
    authRequest: AuthorizationRequest,
    launcher: ActivityResultLauncher<Intent>
){
    Button(
        onClick = {
            val authIntent = authService.getAuthorizationRequestIntent(authRequest)
            launcher.launch(authIntent)
        },
        ) {
        Text(text = "auth")
    }
}