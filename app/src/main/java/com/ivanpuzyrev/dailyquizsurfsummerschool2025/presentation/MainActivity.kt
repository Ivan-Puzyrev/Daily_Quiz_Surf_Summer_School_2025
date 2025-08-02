package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.os.Bundle
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
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.SettingScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StartScreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.domain.entities.Category
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyQuizSurfSummerSchool2025Theme {

                val viewModel: MainViewModel = viewModel()

                val mainScreenState = viewModel.mainScreenState.collectAsState()

                when (mainScreenState.value) {
                    is MainScreenState.Initial -> {
                        StartScreen(
                            isLoading = false,
                            isError = false,
                            onHistoryClick = { },
                            onStartClick = { viewModel.goToSettingsScreen() }
                        )
                    }

                    is MainScreenState.StartScreenLoading -> {
                        StartScreen(
                            isLoading = true,
                            isError = false,
                            onHistoryClick = { },
                            onStartClick = { viewModel.goToSettingsScreen() }
                        )
                    }

                    is MainScreenState.StartScreenError -> {
                        StartScreen(
                            isLoading = false,
                            isError = true,
                            onHistoryClick = { },
                            onStartClick = { viewModel.goToSettingsScreen() }
                        )
                    }

                    is MainScreenState.Settings -> {
                        SettingScreen(
                            categoriesList = (mainScreenState.value as MainScreenState.Settings).categoriesList,
                            isError = false,
                            onButtonClicked = { categoryId: Int, difficulty: String ->
                                viewModel.getQuestions(categoryId, difficulty)
                            }
                        )
                    }
                    is MainScreenState.SettingsError -> {
                        SettingScreen(
                            categoriesList = (mainScreenState.value as MainScreenState.SettingsError).categoriesList,
                            isError = true,
                            onButtonClicked = { categoryId: Int, difficulty: String ->
                                viewModel.getQuestions(categoryId, difficulty)
                            }
                        )
                    }
                    is MainScreenState.SettingsLoading -> {
                        Scaffold {paddings ->
                            Box (modifier = Modifier.padding(paddings).fillMaxSize()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }

                    }
                    else -> {

                    }
                }

            }
        }
    }
}