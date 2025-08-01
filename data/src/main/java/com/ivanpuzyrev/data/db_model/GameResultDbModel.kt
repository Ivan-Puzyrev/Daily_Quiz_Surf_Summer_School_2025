package com.ivanpuzyrev.data.db_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ivanpuzyrev.domain.entities.Difficulty

@Entity(tableName = "game_results")
data class GameResultDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val time: String,
    val category: String,
    val difficulty: Difficulty,
    val correctAnswers: Int,
    val answers: String
)
