package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.GameFinishedScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.GameScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.HistoryScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.QuestionScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.SettingScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StartScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StatisticsScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation.AppNavGraph
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation.ScreenRoute
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation.rememberNavigationState
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question
import kotlinx.coroutines.launch

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
                    historyScreen = { HistoryScreen({ navigationState.navigateToStatistics(it) }, { }) },
                    statisticsScreen = { StatisticsScreen(it) { } }
                )
            }
        }
    }
}