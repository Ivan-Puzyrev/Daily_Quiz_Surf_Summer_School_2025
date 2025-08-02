package com.ivanpuzyrev.domain

import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getCategories(): ApiResult<List<Category>>
    suspend fun getQuestions(category: Category, difficulty: Difficulty): ApiResult<List<Question>>
    suspend fun saveGameResult(gameResult: GameResult)
    suspend fun deleteGameResult(gameResultId: Int): Boolean
    fun getGameResults(): Flow<List<GameResult>>
}