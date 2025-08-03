package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.MainScreenState
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.MainViewModel

@Composable
fun GameScreen(onHistoryClick: () -> Unit) {
    val viewModel: MainViewModel = viewModel()

    val mainScreenState = viewModel.mainScreenState.collectAsState()

    when (mainScreenState.value) {
        is MainScreenState.Initial -> {
            StartScreen(
                isLoading = false,
                isError = false,
                onHistoryClick = { onHistoryClick() },
                onStartClick = { viewModel.goToSettingsScreen() })
        }

        is MainScreenState.StartScreenLoading -> {
            StartScreen(
                isLoading = true,
                isError = false,
                onHistoryClick = { onHistoryClick() },
                onStartClick = { viewModel.goToSettingsScreen() })
        }

        is MainScreenState.StartScreenError -> {
            StartScreen(
                isLoading = false,
                isError = true,
                onHistoryClick = { onHistoryClick() },
                onStartClick = { viewModel.goToSettingsScreen() })
        }

        is MainScreenState.Settings -> {
            SettingScreen(
                categoriesList = (mainScreenState.value as MainScreenState.Settings).categoriesList,
                isError = false,
                onButtonClicked = { categoryId: Int, difficulty: String ->
                    viewModel.loadQuestions(categoryId, difficulty)
                }
            )
        }

        is MainScreenState.SettingsError -> {
            SettingScreen(
                categoriesList = (mainScreenState.value as MainScreenState.SettingsError).categoriesList,
                isError = true,
                onButtonClicked = { categoryId: Int, difficulty: String ->
                    viewModel.loadQuestions(categoryId, difficulty)
                }
            )
        }

        is MainScreenState.SettingsLoading -> {
            Scaffold { paddings ->
                Box(
                    modifier = Modifier
                        .padding(paddings)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        is MainScreenState.Question -> {
            val questionState = (mainScreenState.value as MainScreenState.Question)
            if (questionState.numberOfQuestion != questionState.totalNumberOfQuestions) {
                QuestionScreen(
                    questionState.question,
                    questionState.numberOfQuestion,
                    questionState.totalNumberOfQuestions
                ) {
                    viewModel.answerTheQuestion(it)
                    viewModel.getNextQuestion()
                }
            } else {
                QuestionScreen(
                    questionState.question,
                    questionState.numberOfQuestion,
                    questionState.totalNumberOfQuestions
                ) {
                    viewModel.answerTheQuestion(it)
                    viewModel.finishTheGame()
                }
            }
        }

        is MainScreenState.GameFinished -> {
            val gameFinishedState = (mainScreenState.value as MainScreenState.GameFinished)
            GameFinishedScreen(
                gameResult = gameFinishedState.gameResult,
                onPlayAgainClick = { viewModel.startTheNewGame() }
            )
        }
    }
}