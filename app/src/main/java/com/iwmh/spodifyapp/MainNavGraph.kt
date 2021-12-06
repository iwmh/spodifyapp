package com.iwmh.spodifyapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iwmh.spodifyapp.repository.MainPagingSource
import com.iwmh.spodifyapp.view.home.HomeScreen
import com.iwmh.spodifyapp.view.library.LibraryScreen
import com.iwmh.spodifyapp.view.library.LibraryScreenViewModel
import com.iwmh.spodifyapp.view.search.SearchScreen
import javax.inject.Inject

@Composable
fun MainNavGraph (
        navController: NavHostController,
        modifier: Modifier = Modifier,
        mainPagingSource: MainPagingSource
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
            val libraryScreenViewModel: LibraryScreenViewModel = viewModel(
                   factory = LibraryScreenViewModel.provideFactory(mainPagingSource)
            )
            LibraryScreen(libraryScreenViewModel)
        }
    }

}
