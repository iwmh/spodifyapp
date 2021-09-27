package com.iwmh.spodifyapp.view

import android.content.Intent
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
import com.iwmh.spodifyapp.ui.theme.SpodifyappTheme
import com.iwmh.spodifyapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // viewmodel for the Activity
    private val mainViewModel: MainViewModel by viewModels()

    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d("MainActivity", activityResult.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpodifyApp(name = "Hiroshi", viewModel = mainViewModel)
        }
        launcher.launch(AuthorizationActivity.createIntent(context = this))
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
            Greeting(name = "Hiroshi", viewModel = viewModel)
        }
    }

}

@Composable
fun Greeting(name: String, viewModel: MainViewModel) {
    Text(text = " a list of Spotify podcast information .....")
}
