package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import com.ivanpuzyrev.domain.entities.GameResult

sealed class HistoryScreenState {

    object Initial : HistoryScreenState()
    object NoRecords: HistoryScreenState()
    data class GameResults (val gameResults: List<GameResult>): HistoryScreenState()



}