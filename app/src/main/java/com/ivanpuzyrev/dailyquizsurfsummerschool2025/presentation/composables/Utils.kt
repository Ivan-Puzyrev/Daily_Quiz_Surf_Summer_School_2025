package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.R
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Black
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Gray
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Indigo
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Transparent
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White

@Composable
fun QuizRadioButton(
    modifier: Modifier = Modifier,
    isCorrectAnswer: Boolean = false,
    isWrongAnswer: Boolean = false,
    isSelected: Boolean = false
) {
    Box(
        modifier = modifier
            .size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Image(
                painter = painterResource(R.drawable.selected_radio_button),
                contentDescription = "Selected Radio Button",
                modifier = Modifier.size(30.dp)
            )
        } else if (isCorrectAnswer) {
            Image(
                painter = painterResource(R.drawable.correct_radio_button),
                contentDescription = "Correct Radio Button",
                modifier = Modifier.size(30.dp)
            )
        } else if (isWrongAnswer) {
            Image(
                painter = painterResource(R.drawable.wrong_radio_button),
                contentDescription = "Wrong Radio Button",
                modifier = Modifier.size(30.dp)
            )
        } else {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = if (isCorrectAnswer || isWrongAnswer || isSelected) Transparent else Black,
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun QuizButton(text: String, onClick: () -> Unit, enabled: Boolean) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Indigo, disabledContainerColor = Gray),
        onClick = { onClick() },
        shape = RoundedCornerShape(12.dp),
        enabled = enabled


    ) {
        Text(
            text, Modifier.padding(6.dp), White, 18.sp, fontWeight = FontWeight.Bold
        )
    }
}