package com.kottland.playsonghymbook.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kottland.playsonghymbook.R
import com.kottland.playsonghymbook.core.navigation.HymnBookNavigation
import com.kottland.playsonghymbook.core.navigation.Screen

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomNavItems = listOf(
        BottomNavItem(Screen.Home.route, Icons.Default.Home, R.string.nav_home),
        BottomNavItem(Screen.AllHymns.route, Icons.Default.List, R.string.nav_all_hymns),
        BottomNavItem(Screen.Favorites.route, Icons.Default.Favorite, R.string.nav_favorites),
        BottomNavItem(Screen.Settings.route, Icons.Default.Settings, R.string.nav_settings)
    )
    
    val showBottomBar = currentDestination?.route in bottomNavItems.map { it.route }
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = stringResource(item.labelResId)
                                )
                            },
                            label = {
                                Text(stringResource(item.labelResId))
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        HymnBookNavigation(
            navController = navController,
            paddingValues = innerPadding
        )
    }
}