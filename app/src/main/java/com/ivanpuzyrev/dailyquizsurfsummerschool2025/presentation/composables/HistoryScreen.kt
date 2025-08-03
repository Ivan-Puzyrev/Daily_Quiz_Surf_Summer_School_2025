package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(gameResult: GameResult, onHistoryRecordClick: () -> Unit) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp),
                title = {
                    Text(
                        "История",
                        Modifier.padding(6.dp),
                        White,
                        32.sp,
                        fontWeight = FontWeight.Bold
                    )
                })

        }
    ) { paddings ->
        paddings.toString()
    }
}

@Preview
@Composable
fun PreviewHistoryScreen() {
    DailyQuizSurfSummerSchool2025Theme {
        val gameResult = GameResult(
            date = "01.02.2023",
            time = "13:23",
            category = "General",
            difficulty = Difficulty.EASY,
            correctAnswers = 3,
            answers = emptyList()
        )
        HistoryScreen(gameResult, {})
    }
}