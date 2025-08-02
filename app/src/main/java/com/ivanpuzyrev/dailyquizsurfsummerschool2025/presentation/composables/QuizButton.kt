package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables

import android.widget.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Gray
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.Indigo
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.ui.theme.White

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