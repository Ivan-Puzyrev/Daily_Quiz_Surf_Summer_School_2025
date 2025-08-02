package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.domain.usecases.GetCategoriesUseCase
import com.ivanpuzyrev.domain.usecases.GetQuestionsUseCase
import com.ivanpuzyrev.domain.usecases.SaveGameResultUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuizRepositoryImpl(application)
    private val getCategoriesUseCase = GetCategoriesUseCase(repository)
    private val getQuestionsUseCase = GetQuestionsUseCase(repository)
    private val saveGameResultUseCase = SaveGameResultUseCase(repository)

    fun getSetting() {
        viewModelScope.launch {
            Log.d("MainViewModel", getCategoriesUseCase().toString())
        }
    }


}