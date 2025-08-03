package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.GameResult
import jakarta.inject.Inject

class SaveGameResultUseCase @Inject constructor (private val repository: QuizRepository) {
    suspend operator fun invoke(gameResult: GameResult) = repository.saveGameResult(gameResult)
}