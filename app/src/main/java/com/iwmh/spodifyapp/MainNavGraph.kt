package com.iwmh.spodifyapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.iwmh.spodifyapp.view.episodes.EpisodesScreen
import com.iwmh.spodifyapp.view.home.HomeScreen
import com.iwmh.spodifyapp.view.library.LibraryScreen
import com.iwmh.spodifyapp.view.search.SearchScreen

@Composable
fun MainNavGraph(
        navController: NavHostController,
        modifier: Modifier = Modifier
){
    NavHost(
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
            LibraryScreen(navController)
        }
        composable(
            route = "${Screen.Episodes.route}/{showId}",
            arguments = listOf(
                navArgument("showId"){
                    type = NavType.StringType
                }
            )
        ){ navBackStackEntry ->
            val showId = navBackStackEntry.arguments?.getString("showId")
            EpisodesScreen(showId)
        }
    }

}
