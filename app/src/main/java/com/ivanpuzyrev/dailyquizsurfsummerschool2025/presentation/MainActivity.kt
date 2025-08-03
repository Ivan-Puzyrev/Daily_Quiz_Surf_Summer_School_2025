package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.GameScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.HistoryScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StatisticsScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation.AppNavGraph
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation.ScreenRoute
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation.rememberNavigationState
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            DailyQuizSurfSummerSchool2025Theme {

                val navigationState = rememberNavigationState()

                AppNavGraph(
                    navHostController = navigationState.navHostController,
                    gameScreen = {
                        GameScreen { navigationState.navigateTo(ScreenRoute.ROUTE_HISTORY) }
                    },
                    historyScreen = {
                        HistoryScreen(
                            { navigationState.navigateToStatistics(it) },
                            { navigationState.navigateToStarScreen() })
                    },
                    statisticsScreen = { StatisticsScreen(it) { navigationState.navigateToStarScreen() } }
                )
            }
        }
    }
}