package com.ivanpuzyrev.data.dto_model

import com.google.gson.annotations.SerializedName

data class QuestionDTO(
    @SerializedName("question") val question: String,
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>
)