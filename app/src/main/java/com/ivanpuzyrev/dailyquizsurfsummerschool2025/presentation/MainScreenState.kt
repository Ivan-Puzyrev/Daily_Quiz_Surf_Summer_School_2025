package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import com.ivanpuzyrev.domain.entities.Category

sealed class MainScreenState {

    object Initial: MainScreenState()
//    object StartScreen: MainScreenState()
    object StartScreenError: MainScreenState()
    object StartScreenLoading: MainScreenState()
    data class Settings (val categoriesList: List<Category>): MainScreenState()
    data class SettingsError (val categoriesList: List<Category>): MainScreenState()
    object SettingsLoading : MainScreenState()
    object Question: MainScreenState()
    object LastQuestion: MainScreenState()
    object GameResult: MainScreenState()

}