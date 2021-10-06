package com.iwmh.spodifyapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iwmh.spodifyapp.ui.theme.SpodifyappTheme
import com.iwmh.spodifyapp.view.home.HomeScreen
import com.iwmh.spodifyapp.viewmodel.MainViewModel

@Composable
fun SpodifyAppScreen(name: String, viewModel: MainViewModel){
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
            HomeScreen(name = "Hiroshi")
        }
    }

}
