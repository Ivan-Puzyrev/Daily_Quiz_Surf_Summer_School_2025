package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.MainScreenState.*
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StartScreen
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.domain.ApiResult
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.usecases.GetCategoriesUseCase
import com.ivanpuzyrev.domain.usecases.GetQuestionsUseCase
import com.ivanpuzyrev.domain.usecases.SaveGameResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuizRepositoryImpl(application)
    private val getCategoriesUseCase = GetCategoriesUseCase(repository)
    private val getQuestionsUseCase = GetQuestionsUseCase(repository)
    private val saveGameResultUseCase = SaveGameResultUseCase(repository)

    val mainScreenState: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState.Initial )
    var categoryList: List<Category> = emptyList()


    fun goToSettingsScreen() {
        viewModelScope.launch {
            mainScreenState.value = StartScreenLoading
            val apiResult = getCategoriesUseCase()
            when (apiResult) {
                is ApiResult.Success -> {
                    categoryList = apiResult.data
                    mainScreenState.value = Settings(categoryList)
                }
                is ApiResult.Error -> mainScreenState.value = StartScreenError
            }
        }

    }

    fun getQuestions(categoryId: Int, difficulty: String) {
        viewModelScope.launch {
            val apiResult = getQuestionsUseCase(categoryId, difficulty)
            when (apiResult) {
                is ApiResult.Success -> {
                    mainScreenState.value = SettingsLoading
                    Log.d("MainViewModel", apiResult.data.toString())
                }
                is ApiResult.Error -> {
                    mainScreenState.value = SettingsError(categoryList)
                }
            }

        }
    }


}