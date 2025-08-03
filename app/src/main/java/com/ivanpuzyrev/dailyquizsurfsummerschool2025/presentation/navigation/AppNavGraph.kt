package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation

import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivanpuzyrev.domain.entities.GameResult

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    gameScreen: @Composable () -> Unit,
    historyScreen: @Composable () -> Unit,
    statisticsScreen: @Composable (GameResult) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoute.Game.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {

        composable(
            route = ScreenRoute.Game.route
        ) {
            gameScreen()

        }
        composable(
            route = ScreenRoute.History.route
        ) {
            historyScreen()
        }


        composable(
            route = ScreenRoute.Statistics.route
        ) {
            val gameResultString =
                Uri.decode(it.arguments?.getString(ScreenRoute.KEY_GAME_RESULT) ?: "")
            val type = object : TypeToken<GameResult>() {}.type
            val gameResult: GameResult = Gson().fromJson(gameResultString, type)
            statisticsScreen(gameResult)
        }
    }
}