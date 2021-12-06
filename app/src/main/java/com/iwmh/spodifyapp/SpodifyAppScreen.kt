package com.iwmh.spodifyapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iwmh.spodifyapp.ui.theme.SpodifyappTheme

sealed class Screen(val route: String, val name: String, val iconVector: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Library : Screen("library", "Library", Icons.Default.List)
}

@Composable
fun SpodifyAppScreen(name: String, viewModel: MainViewModel){
    SpodifyappTheme {
        val items = listOf(
            Screen.Home,
            Screen.Search,
            Screen.Library
        )
        // NavController
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach{ screen ->
                        BottomNavigationItem(
                            icon = {Icon(screen.iconVector, contentDescription = null)},
                            label = {Text(screen.name)},
                            selected = currentDestination?.hierarchy?.any{it.route == screen.route} == true,
                            onClick = {
                                navController.navigate(screen.route){
                                    // TODO: Make sure to limit the max stack number.
//                                    popUpTo(navController.graph.findStartDestination().id){
//                                        saveState = true
//                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }

                }

            }
        ) { innerPadding ->
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                MainNavGraph(
                    navController = navController,
                    Modifier.padding(innerPadding)
                )
            }
        }
    }
}

