package com.assignment.stocksbrowser.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.assignment.stocksbrowser.ui.detail.screen.DetailScreen
import com.assignment.stocksbrowser.ui.home.screen.HomeScreen
import com.assignment.stocksbrowser.ui.home.screen.InstrumentType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapHorizontalCircle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.assignment.stocksbrowser.presentation.article_screen.ArticleScreen
import com.assignment.stocksbrowser.presentation.news_screen.NewsScreen
import com.assignment.stocksbrowser.presentation.news_screen.NewsScreenViewModel
import com.assignment.stocksbrowser.ui.home.screen.NotifyScreen
import com.assignment.stocksbrowser.ui.home.screen.PortfolioScreen
import com.assignment.stocksbrowser.ui.home.screen.ProfileScreen
import com.assignment.stocksbrowser.ui.home.screen.TradingScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val argKey = "web_url"
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
        composable(route = BottomBarScreen.Trading.route) {
            TradingScreen()
        }
        composable(route = BottomBarScreen.Portfolio.route) {
            PortfolioScreen()
        }
        composable(route = BottomBarScreen.Notify.route) {
            NotifyScreen()
        }
        composable(route = "news_screen") {
            val viewModel: NewsScreenViewModel = hiltViewModel()
            NewsScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                onReadFullStoryButtonClick = { url ->
                    navController.navigate("article_screen?$argKey=$url")
                }
            )
        }
        composable(
            route = "article_screen?$argKey={$argKey}",
            arguments = listOf(navArgument(name = argKey) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ArticleScreen(
                url = backStackEntry.arguments?.getString(argKey),
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable(route = BottomBarScreen.Market.route) {
            val viewModel: NewsScreenViewModel = hiltViewModel()
            NewsScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                onReadFullStoryButtonClick = { url ->
                    navController.navigate("article_screen?$argKey=$url")
                }
            )
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
        title = "",
        icon = Icons.Default.Home
    )

    object Market : BottomBarScreen(
        route = "market",
        title = "",
        icon = Icons.Default.InsertChart
    )
    object Trading : BottomBarScreen(
        route = "trading",
        title = "",
        icon = Icons.Default.SwapHorizontalCircle
    )
    object Portfolio : BottomBarScreen(
        route = "portfolio",
        title = "",
        icon = Icons.Default.AttachMoney
    )
    object Notify : BottomBarScreen(
        route = "notify",
        title = "",
        icon = Icons.Default.Notifications
    )
    object Profile : BottomBarScreen(
        route = "profile",
        title = "",
        icon = Icons.Default.Person
    )
}