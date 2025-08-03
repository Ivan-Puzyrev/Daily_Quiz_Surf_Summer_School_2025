package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import com.ivanpuzyrev.domain.entities.GameResult

sealed class HistoryScreenState {

    object Initial : HistoryScreenState()
    data class NoRecords (val isDeleted: Boolean) : HistoryScreenState()
    data class GameResults (val gameResults: List<GameResult>, val isDeleted: Boolean): HistoryScreenState()



}