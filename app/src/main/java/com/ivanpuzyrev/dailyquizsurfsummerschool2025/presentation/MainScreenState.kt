package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

sealed class MainScreenState {

    object Initial: MainScreenState()
    object StartScreen: MainScreenState()
    object StartScreenError: MainScreenState()
    object StartScreenLoading: MainScreenState()
    object Settings: MainScreenState()
    object Question: MainScreenState()
    object LastQuestion: MainScreenState()
    object GameResult: MainScreenState()

}