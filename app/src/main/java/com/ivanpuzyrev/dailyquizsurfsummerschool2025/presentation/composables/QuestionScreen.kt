package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.R
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Lavender
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.LightGray
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Purple
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Transparent
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White
import com.ivanpuzyrev.domain.entities.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    question: Question,
    numberOfQuestion: Int,
    totalNumberOfQuestion: Int,
    onButtonClicked: (String) -> Unit
) {

    val options =
        mutableListOf<String>().apply {
            addAll(question.incorrectAnswers)
            add(question.correctAnswer)
            shuffle()
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp),
                title = {
                    Image(
                        modifier = Modifier.height(40.dp),
                        painter = painterResource(R.drawable.daily_quiz),
                        contentDescription = "Daily Quiz Logo"
                    )
                })

        }
    ) { paddings ->
        var isButtonEnabled by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(paddings).fillMaxHeight()) {

            Card(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                shape = RoundedCornerShape(46.dp)
            ) {
                Column(modifier = Modifier.padding(32.dp).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Вопрос $numberOfQuestion из $totalNumberOfQuestion",
                            color = Lavender,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            text = question.question,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )


                        RadioGroup(radioOptions = options, onOptionsSelected = {
                            isButtonEnabled = true
                            selectedOption = it
                        })
                    }
                    QuizButton(
                        text = if (numberOfQuestion == totalNumberOfQuestion) "ЗАВЕРШИТЬ" else "ДАЛЕЕ",
                        enabled = isButtonEnabled,
                        onClick = {
                            isButtonEnabled = false
                            onButtonClicked(selectedOption)
                        })
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "Вернуться к предыдущим вопросам нельзя",
                textAlign = TextAlign.Center,
                color = White,
                fontSize = 10.sp
            )
        }

    }
}

@Preview
@Composable
fun QuestionScreenPreview() {
    DailyQuizSurfSummerSchool2025Theme {
        val question = Question(
            "Как переводится слово Apple?",
            "Яблоко",
            listOf("Груша", "Апельсин", "Ананас")
        )
        QuestionScreen(
            question = question,
            numberOfQuestion = 1,
            totalNumberOfQuestion = 5
        ) { }
    }
}


@Composable
fun RadioGroup(radioOptions: List<String>, onOptionsSelected: (String) -> Unit) {

    val selectedOption = remember { mutableStateOf("") }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            val selected = text == selectedOption.value

            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selected),
                        onClick = {
                            onOptionsSelected(text)
                            selectedOption.value = text
                        },
                        role = Role.RadioButton,
                    )
                    .padding(vertical = 8.dp),
                border = if (selected) BorderStroke(1.dp, Purple) else BorderStroke(
                    width = 0.dp,
                    color = Transparent
                ),
                colors = if (!selected) CardDefaults.outlinedCardColors(containerColor = LightGray) else CardDefaults.outlinedCardColors(
                    containerColor = White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(selectedColor = Purple),
                        selected = (selected),
                        onClick = null,
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