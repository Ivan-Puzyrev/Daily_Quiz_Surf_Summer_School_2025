package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Gray
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.LightGray
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.LightGreen
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Red
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Transparent
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White
import com.ivanpuzyrev.domain.entities.Answer
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(gameResult: GameResult, onPlayAgainClick: () -> Unit) {


    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(top = 50.dp, bottom = 20.dp)) {
                Text(
                    "Результаты",
                    Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    White,
                    32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Категория: ${gameResult.category}",
                    color = White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Сложность: ${
                        when (gameResult.difficulty) {
                            Difficulty.EASY -> "Низкая"
                            Difficulty.MEDIUM -> "Средняя"
                            Difficulty.HARD -> "Высокая"
                            Difficulty.UNKNOWN -> ""
                        }
                    }",
                    color = White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }


        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            GameResultCard(PaddingValues(bottom = 10.dp), gameResult) {
                onPlayAgainClick()
            }
            gameResult.answers.forEachIndexed { index, answer ->
                AnswerCard(answer, index + 1, gameResult.answers.size)
            }
        }
    }
}

@Composable
fun AnswerCard(answer: Answer, number: Int, quantity: Int) {
    val isItCorrect = answer.selectedAnswer == answer.question.correctAnswer

    Card(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        shape = RoundedCornerShape(46.dp)
    ) {
        Column(modifier = Modifier.padding(25.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Вопрос $number из $quantity",
                    color = Gray,
                    fontWeight = FontWeight.Bold
                )
                if (isItCorrect) {
                    Image(
                        painter = painterResource(R.drawable.correct_radio_button),
                        contentDescription = "Wrong Radio Button",
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.wrong_radio_button),
                        contentDescription = "Wrong Radio Button",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                text = answer.question.question,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            RadioGroupResult(answer)
        }

    }
}

@Composable
fun RadioGroupResult(answer: Answer) {

    val radioOptions by remember {

        mutableStateOf(
            mutableListOf<String>().apply {
                addAll(answer.question.incorrectAnswers)
                add(answer.question.correctAnswer)
                shuffle()
            }.toList()
        )

    }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            val isItCorrectAnswer = text == answer.question.correctAnswer
            val isItSelectedAnswer = text == answer.selectedAnswer

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                border = if (isItCorrectAnswer) BorderStroke(
                    1.dp,
                    LightGreen
                ) else if (isItSelectedAnswer) BorderStroke(1.dp, Red) else BorderStroke(
                    width = 0.dp,
                    color = Transparent
                ),
                colors = if (isItCorrectAnswer || isItSelectedAnswer) CardDefaults.outlinedCardColors(
                    containerColor = White
                ) else CardDefaults.outlinedCardColors(
                    containerColor = LightGray
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuizRadioButton(
                        isCorrectAnswer = isItCorrectAnswer,
                        isWrongAnswer = isItSelectedAnswer,
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            }
        }
    }
}

//@Preview
@Composable
fun PreviewRadioGroupResult() {
    DailyQuizSurfSummerSchool2025Theme {
        val question = Question("sadsd", "1", listOf("2", "3", "4"))
        val answer = Answer(question, "2")
        RadioGroupResult(answer)
    }

}

//@Preview
@Composable
fun PreviewAnswerCard() {
    DailyQuizSurfSummerSchool2025Theme {
        val question = Question("sadsd", "1", listOf("2", "3", "4"))
        val answer = Answer(question, "2")
        AnswerCard(answer, 3, 5)
    }
}

@Preview
@Composable
fun PreviewStatisticsScreen() {
    DailyQuizSurfSummerSchool2025Theme {
        val question1 = Question("sadsd", "1", listOf("2", "3", "4"))
        val answer1 = Answer(question1, "2")
        val question2 = Question("sadsd", "1", listOf("2", "3", "4"))
        val answer2 = Answer(question2, "2")

        val gameResult = GameResult(
            date = "01.02.2023",
            time = "13:23",
            category = "General",
            difficulty = Difficulty.EASY,
            correctAnswers = 3,
            answers = listOf(answer1, answer2)
        )
        StatisticsScreen(gameResult, {})
    }
}