package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.usecases.GetGameResultsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuizRepositoryImpl(application)
    private val getGameResultsUseCase = GetGameResultsUseCase(repository)

    val historyScreenState: MutableStateFlow<HistoryScreenState> = MutableStateFlow(HistoryScreenState.Initial)

    init {
        getGameResults()
    }

    private fun getGameResults() {
        viewModelScope.launch {
            getGameResultsUseCase().collect {
                if (it.isEmpty()) {
                    historyScreenState.emit(HistoryScreenState.NoRecords)
                } else {
                    historyScreenState.emit(HistoryScreenState.GameResults(it))
                }
            }
        }
    }

}