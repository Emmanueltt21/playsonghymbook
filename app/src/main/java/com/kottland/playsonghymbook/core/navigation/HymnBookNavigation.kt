package com.kottland.playsonghymbook.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import com.kottland.playsonghymbook.feature.home.HomeScreen
import com.kottland.playsonghymbook.feature.list.AllHymnsScreen
import com.kottland.playsonghymbook.feature.favorites.FavoritesScreen
import com.kottland.playsonghymbook.feature.settings.SettingsScreen
import com.kottland.playsonghymbook.feature.details.HymnDetailsScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AllHymns : Screen("all_hymns")
    object Favorites : Screen("favorites")
    object Settings : Screen("settings")
    object Details : Screen("details/{number}") {
        fun createRoute(number: Int) = "details/$number"
    }
}

@Composable
fun HymnBookNavigation(
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues = PaddingValues()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { number ->
                    navController.navigate(Screen.Details.createRoute(number))
                },
                onNavigateToAllHymns = {
                    navController.navigate(Screen.AllHymns.route)
                }
            )
        }
        
        composable(Screen.AllHymns.route) {
            AllHymnsScreen(
                onNavigateToDetails = { number ->
                    navController.navigate(Screen.Details.createRoute(number))
                }
            )
        }
        
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onNavigateToDetails = { number ->
                    navController.navigate(Screen.Details.createRoute(number))
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("number") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val number = backStackEntry.arguments?.getInt("number") ?: 1
            HymnDetailsScreen(
                hymnNumber = number,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHymn = { newNumber ->
                    navController.navigate(Screen.Details.createRoute(newNumber)) {
                        popUpTo(Screen.Details.route) { inclusive = true }
                    }
                }
            )
        }
    }
}