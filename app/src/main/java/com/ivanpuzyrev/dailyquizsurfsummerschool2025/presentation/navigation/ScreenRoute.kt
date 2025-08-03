package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.navigation

import android.net.Uri
import com.google.gson.Gson
import com.ivanpuzyrev.domain.entities.GameResult

sealed class ScreenRoute(
    val route: String
) {

    object Game : ScreenRoute(ROUTE_GAME)
    object History : ScreenRoute(ROUTE_HISTORY)
    object Statistics : ScreenRoute(ROUTE_STATISTICS) {
        private const val ROUTE_FOR_ARGS = "statistics"
        fun getRouteWithArgs(gameResult: GameResult): String {
            
            val gameResultJson = Gson().toJson(gameResult)
            return "${ROUTE_FOR_ARGS}/${gameResultJson.encode()}"
        }
    }


    companion object {
        const val KEY_GAME_RESULT = "game_result"

        const val ROUTE_GAME = "game"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_STATISTICS = "statistics/{$KEY_GAME_RESULT}"

    }
}

fun String.encode(): String {
    return Uri.encode(this)
}