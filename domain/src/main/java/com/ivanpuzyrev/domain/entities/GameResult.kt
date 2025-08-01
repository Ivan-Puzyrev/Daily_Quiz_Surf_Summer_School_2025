package com.ivanpuzyrev.domain.entities

data class GameResult(
    val id: Int,
    val date: String,
    val time: String,
    val category: String,
    val difficulty: Difficulty,
    val correctAnswers: Int,
    val answers: List<Answer>
)