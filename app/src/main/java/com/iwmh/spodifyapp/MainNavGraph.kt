package com.iwmh.spodifyapp

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.iwmh.spodifyapp.view.home.HomeScreen
import com.iwmh.spodifyapp.view.library.LibraryScreen
import com.iwmh.spodifyapp.view.search.SearchScreen

@ExperimentalAnimationApi
@Composable
fun MainNavGraph(
        navController: NavHostController,
        modifier: Modifier = Modifier
){
    AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier
    ){
        composable(Screen.Home.route){
            HomeScreen(name = "Hiroshi")
        }
        composable(Screen.Search.route){
            SearchScreen(name = "Hiroshi")
        }
        composable(Screen.Library.route){
            LibraryScreen(name = "Hiroshi")
        }
    }

}
