package com.ivanpuzyrev.domain

import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getCategories(): ApiResult<List<String>>
    suspend fun getQuestions(category: String, difficulty: Difficulty): ApiResult<List<Question>>
    suspend fun saveGameResult(gameResult: GameResult)
    suspend fun getGameResults(): Flow<List<GameResult>>
}