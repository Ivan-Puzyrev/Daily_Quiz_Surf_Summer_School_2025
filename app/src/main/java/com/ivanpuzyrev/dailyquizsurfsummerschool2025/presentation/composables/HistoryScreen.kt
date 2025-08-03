package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.R
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.HistoryScreenState
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.HistoryViewModel
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Purple
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onHistoryRecordClick: (GameResult) -> Unit, onStartTheGameClick: () -> Unit) {

    val viewModel: HistoryViewModel = viewModel()

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
        val screenState = viewModel.historyScreenState.collectAsState(HistoryScreenState.Initial)

        when (screenState.value) {
            is HistoryScreenState.NoRecords -> {
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
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 40.dp),
                            fontSize = 25.sp,
                            lineHeight = TextUnit(30.0f, TextUnitType.Sp),
                            text = "Вы еще не проходили\nни одной викторины",
                            textAlign = TextAlign.Center
                        )
                        QuizButton(
                            text = "НАЧАТЬ ВИКТОРИНУ",
                            enabled = true,
                            onClick = {
                                onStartTheGameClick()
                            })
                    }
                }
            }

            is HistoryScreenState.GameResults -> {
                val gameResults = (screenState.value as HistoryScreenState.GameResults).gameResults
                Column(
                    modifier = Modifier
                        .padding(paddings)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    var deletingItemId by remember { mutableIntStateOf(-1) }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(items = gameResults, key = { it.id }) { gameResult ->
                            GameResultCard(
                                gameResult = gameResult,
                                onHistoryRecordClick = { onHistoryRecordClick(it) },
                                onLongHistoryRecordClick = {
                                    deletingItemId = it
                                },
                                onDeleteHistoryRecordClicked = { viewModel.deleteGameResult(it) },
                                deletingItemId = deletingItemId
                            )
                            Spacer(Modifier.height(28.dp))
                        }
                    }
                    Image(
                        modifier = Modifier
                            .padding(vertical = 40.dp)
                            .height(40.dp),
                        painter = painterResource(R.drawable.daily_quiz),
                        contentDescription = "Daily Quiz Logo"
                    )
                }
            }

            is HistoryScreenState.Initial -> {

            }
        }


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
        HistoryScreen({}, {})
    }
}

@Composable
fun GameResultCard(
    gameResult: GameResult,
    onHistoryRecordClick: (GameResult) -> Unit,
    onLongHistoryRecordClick: (Int) -> Unit,
    onDeleteHistoryRecordClicked: (Int) -> Unit,
    deletingItemId: Int
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .combinedClickable(
                onClick = { onHistoryRecordClick(gameResult) },
                onLongClick = { onLongHistoryRecordClick(gameResult.id) }
            ),
        shape = RoundedCornerShape(46.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = "Quiz ${gameResult.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Purple
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 1..gameResult.correctAnswers) {
                        Image(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(18.dp),
                            painter = painterResource(R.drawable.yellow_star),
                            contentDescription = "Yellow Star",
                        )
                    }
                    for (i in gameResult.correctAnswers until 5) {
                        Image(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(18.dp),
                            painter = painterResource(R.drawable.gray_star),
                            contentDescription = "Gray Star",
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = gameResult.date,
                    fontSize = 14.sp
                )
                Text(
                    text = gameResult.time,
                    fontSize = 14.sp
                )

            }


            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Категория: ${gameResult.category}",
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
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            DropdownMenu(
                expanded = deletingItemId == gameResult.id,
                onDismissRequest = { onLongHistoryRecordClick(-1) },
                offset = DpOffset(x = 150.dp, y = (-200).dp),
                shape = RoundedCornerShape(18.dp),
                containerColor = White,
                border = BorderStroke(1.dp, Purple),
                shadowElevation = 0.dp

            ) {
                Row(modifier = Modifier.padding(8.dp).clickable{
                    onLongHistoryRecordClick(-1)
                    onDeleteHistoryRecordClicked(gameResult.id)
                },
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.trash_icon), "Delete icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(modifier = Modifier.width(150.dp), text = "Удалить")

                }

            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewResultCard() {
//    DailyQuizSurfSummerSchool2025Theme {
//        val gameResult = GameResult(
//            date = "01.02.2023",
//            time = "13:23",
//            category = "General",
//            difficulty = Difficulty.EASY,
//            correctAnswers = 3,
//            answers = emptyList()
//        )
//        GameResultCard(gameResult) {
//
//        }
//    }
//}