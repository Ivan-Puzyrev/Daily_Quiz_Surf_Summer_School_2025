package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ivanpuzyrev.domain.entities.GameResult

class NavigationState(
    val navHostController: NavHostController,
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route)
    }

    fun navigateToStatistics(gameResult: GameResult) {
        navHostController.navigate(ScreenRoute.Statistics.getRouteWithArgs(gameResult))
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}