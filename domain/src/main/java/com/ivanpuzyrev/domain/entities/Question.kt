package com.ivanpuzyrev.domain.entities

data class Question(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>
)
