package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.R
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Yellow
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.GameResultText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameFinishedScreen(gameResult: GameResult, onPlayAgainClick: () -> Unit) {



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp),
                title = {
                    Text(
                        "Результаты",
                        Modifier.padding(6.dp),
                        White,
                        32.sp,
                        fontWeight = FontWeight.Bold
                    )
                })

        }
    ) { paddings ->

        GameResultCard(paddings, gameResult) {
            onPlayAgainClick()
        }

    }
}

@Composable
fun GameResultCard(paddings: PaddingValues, gameResult: GameResult, onPlayAgainClick: () -> Unit) {
    val gameResultText = when (gameResult.correctAnswers) {
        0 -> GameResultText.ZERO
        1 -> GameResultText.ONE
        2 -> GameResultText.TWO
        3 -> GameResultText.THREE
        4 -> GameResultText.FOUR
        5 -> GameResultText.FIVE
        else -> GameResultText.UNKNOWN
    }

    Card(
        modifier = Modifier
            .padding(paddings)
            .padding(horizontal = 20.dp),

        shape = RoundedCornerShape(46.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..gameResult.correctAnswers) {
                    Image(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        painter = painterResource(R.drawable.yellow_star),
                        contentDescription = "Yellow Star",
                    )
                }
                for (i in gameResult.correctAnswers until 5) {
                    Image(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        painter = painterResource(R.drawable.gray_star),
                        contentDescription = "Gray Star",
                    )
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 30.dp),
                text = "${gameResult.correctAnswers} из ${gameResult.answers.size}",
                color = Yellow,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
                text = gameResultText.title,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(bottom = 50.dp),
                text = gameResultText.text,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )


            QuizButton(
                text = "НАЧАТЬ ЗАНОВО",
                enabled = true,
                onClick = {
                    onPlayAgainClick()
                })
        }
    }
}

@Preview
@Composable
fun PreviewGameFinishedScreen() {
    DailyQuizSurfSummerSchool2025Theme {
        val gameResult = GameResult(
            date = "01.02.2023",
            time = "13:23",
            category = "General",
            difficulty = Difficulty.EASY,
            correctAnswers = 3,
            answers = emptyList()
        )
        GameFinishedScreen(gameResult, {})
    }
}