package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
                    is MainScreenState.Settings -> {
                        SettingScreen(
                            categoriesList = (mainScreenState.value as MainScreenState.Settings).categoriesList,
                            onButtonClicked = { categoryId: Int, difficulty: String ->
                                viewModel.getQuestions(categoryId, difficulty)
                            })
                    }

                    else -> {

                    }
                }

            }
        }
    }
}