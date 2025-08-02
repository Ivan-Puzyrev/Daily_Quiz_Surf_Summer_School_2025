package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StartScreen
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.domain.ApiResult
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

    init {
        getCategories()
    }

    fun getQuestions(categoryId: Int, difficulty: String) {
        viewModelScope.launch {
            val apiResult = getQuestionsUseCase(categoryId, difficulty)
            if (apiResult is ApiResult.Success) {
                Log.d("MainViewModel", apiResult.data.toString())
            }

        }
    }

    fun getSetting() {
        viewModelScope.launch {
            Log.d("MainViewModel", getCategoriesUseCase().toString())
        }
    }

    fun getCategories()  {
        viewModelScope.launch {
            val apiResult = getCategoriesUseCase()
            if (apiResult is ApiResult.Success) {
                mainScreenState.value =  MainScreenState.Settings(apiResult.data)
            }

        }
    }


}