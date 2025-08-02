package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import android.graphics.Paint
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.R
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Indigo
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(isLoading: Boolean, isError: Boolean, onHistoryClick: () -> Unit) {

    DailyQuizSurfSummerSchool2025Theme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
                    title = {
                        Button(
                            onClick = { onHistoryClick() }
                        ) {
                            Text(
                                text = "История",
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontSize = 12.sp,
                                color = Indigo
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                modifier = Modifier.height(16.dp),
                                imageVector = Icons.Default.History,
                                contentDescription = "History",
                                tint = Indigo
                            )
                        }
                    })

            }
        ) { paddings ->
            Column(
                modifier = Modifier
                    .padding(paddings)
                    .fillMaxSize()
                    .padding(top = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.daily_quiz),
                    contentDescription = "Daily Quiz Logo"
                )
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(100.dp))
                } else {
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 32.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(46.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 24.dp, horizontal = 32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Добро пожаловать\nв DailyQuiz!",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    lineHeight = TextUnit(30f, TextUnitType.Sp)
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = Indigo),
                                    onClick = {},
                                    shape = RoundedCornerShape(12.dp)

                                ) {
                                    Text(
                                        text = "НАЧАТЬ ВИКТОРИНУ",
                                        fontWeight = FontWeight.Bold,
                                        color = White
                                    )
                                }
                            }
                        }
                        if (isError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                color = White,
                                text = "Ошибка! Попробуйте еще раз",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun PreviewStartScreen() {
    StartScreen(true, true, onHistoryClick = { })
}