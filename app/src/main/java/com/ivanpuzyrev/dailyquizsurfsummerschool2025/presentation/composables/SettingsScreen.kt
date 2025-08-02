package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.R
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Black
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.DailyQuizSurfSummerSchool2025Theme
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.LightGray
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Purple
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Difficulty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    categoriesList: List<Category>,
    difficultyList: List<Difficulty> = listOf(
        Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD
    ),
    onButtonClicked: (Int, String) -> Unit
) {

    DailyQuizSurfSummerSchool2025Theme {
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

            var selectedCategory by remember { mutableStateOf("") }
            var selectedDifficulty by remember { mutableStateOf("") }

            Card(
                modifier = Modifier
                    .padding(paddings)
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(46.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Почти готовы!", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Осталось выбрать категорию\nи сложность викторины.",
                        textAlign = TextAlign.Center,
                        lineHeight = TextUnit(16f, TextUnitType.Sp)
                    )

                    Spacer(modifier = Modifier.height(15.dp))


                    BottomSheetSelector(
                        filterName = "Категория",
                        optionsList = categoriesList.map { it.name },
                        onOptionSelected = { selectedCategory = it })
                    BottomSheetSelector(
                        filterName = "Сложность",
                        optionsList = difficultyList.map { it.name },
                        onOptionSelected = { selectedDifficulty = it })

                    Spacer(modifier = Modifier.height(75.dp))

                    QuizButton(
                        text = "ДАЛЕЕ",
                        onClick = {
                            val selectedCategoryId = categoriesList.filter { it.name == selectedCategory }[0].id
                            val selectedDifficulty = difficultyList.filter { it.name == selectedDifficulty }[0].difficulty
                            onButtonClicked(selectedCategoryId, selectedDifficulty)
                        },
                        enabled = selectedCategory.isNotEmpty() && selectedDifficulty.isNotEmpty()
                    )


                }

            }

        }


    }
}

//@Preview
//@Composable
//fun PreviewSettingScreen() {
//    SettingScreen(
//        categoriesList = listOf(Category(1, "Auto")),
//        onButtonClicked = (Int, String) -> { }
//    )
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelector(
    filterName: String,
    optionsList: List<String>,
    onOptionSelected: (String) -> Unit
) {

    var selectedOption by remember { mutableStateOf("") }
    var isSheetOpen by remember { mutableStateOf(false) }


    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false }
        ) {
            val scrollState = rememberScrollState()

            Column(modifier = Modifier.padding(16.dp).verticalScroll(scrollState)) {
                optionsList.forEach { option ->
                    Text(
                        text = option,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = option
                                onOptionSelected(option)
                                isSheetOpen = false
                            }
                            .padding(12.dp)
                    )
                }
            }
        }
    }

    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { isSheetOpen = true },

        placeholder = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = filterName, fontWeight = FontWeight.Bold, color = Purple)
                    if (selectedOption.isNotEmpty()) {
                        Text(text = selectedOption, color = Black, fontSize = 14.sp)
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Select Category",
                    tint = Purple
                )
            }
        },
        shape = RoundedCornerShape(size = 12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = LightGray,
            disabledBorderColor = Color.Transparent
        ),
        enabled = false,
        readOnly = true
    )
}