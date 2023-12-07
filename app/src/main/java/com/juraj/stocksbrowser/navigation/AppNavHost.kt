package com.juraj.stocksbrowser.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juraj.stocksbrowser.ui.detail.screen.DetailScreen
import com.juraj.stocksbrowser.ui.home.screen.HomeScreen
import com.juraj.stocksbrowser.ui.home.screen.InstrumentType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import com.juraj.stocksbrowser.ui.home.screen.ProfileScreen
import com.juraj.stocksbrowser.ui.home.screen.SettingsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.Home.url) {
        composable(route = NavDestinations.Home.url) {
            HomeScreen(navController)
        }
        composable(route = NavDestinations.Details.url) {
            DetailScreen(navController)
        }
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }

    }
}

sealed class NavDestinations(val url: String) {
    object Home : NavDestinations("home")
    object Details : NavDestinations("details?symbol={symbol}&type={type}") {
        fun uri(symbol: String, type: InstrumentType) =
            url.replace("{symbol}", symbol)
                .replace("{type}", type.toString())

        fun getSymbol(savedStateHandle: SavedStateHandle): String? = savedStateHandle["symbol"]
        fun getType(savedStateHandle: SavedStateHandle): InstrumentType? =
            savedStateHandle.get<String?>("type")?.let { InstrumentType.valueOf(it) }
    }
}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Profile : BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}