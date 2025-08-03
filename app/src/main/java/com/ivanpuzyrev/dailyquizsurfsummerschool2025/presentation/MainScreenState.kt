package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import com.ivanpuzyrev.domain.entities.Category

sealed class MainScreenState {

    object Initial : MainScreenState()

    object StartScreenError : MainScreenState()
    object StartScreenLoading : MainScreenState()
    data class Settings(val categoriesList: List<Category>) : MainScreenState()
    data class SettingsError(val categoriesList: List<Category>) : MainScreenState()
    object SettingsLoading : MainScreenState()
    data class Question(val question: com.ivanpuzyrev.domain.entities.Question, val numberOfQuestion: Int, val totalNumberOfQuestions: Int) : MainScreenState()
    data class GameFinished(val gameResult: com.ivanpuzyrev.domain.entities.GameResult): MainScreenState()
    object LastQuestion : MainScreenState()
    object GameResult : MainScreenState()

}