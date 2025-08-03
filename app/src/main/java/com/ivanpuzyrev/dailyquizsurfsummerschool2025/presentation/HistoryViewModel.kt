package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.usecases.DeleteGameResultUseCase
import com.ivanpuzyrev.domain.usecases.GetGameResultsUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel @Inject constructor(
    private val getGameResultsUseCase: GetGameResultsUseCase,
    private val deleteGameResultUseCase: DeleteGameResultUseCase
) : ViewModel() {


    val historyScreenState: MutableStateFlow<HistoryScreenState> =
        MutableStateFlow(HistoryScreenState.Initial)


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

    fun deleteGameResult(gameResultId: Int) {
        viewModelScope.launch {
            deleteGameResultUseCase.invoke(gameResultId)
        }
    }

}