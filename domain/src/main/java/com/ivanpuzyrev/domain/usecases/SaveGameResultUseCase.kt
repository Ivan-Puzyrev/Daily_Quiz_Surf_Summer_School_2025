package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.GameResult

class SaveGameResultUseCase(private val repository: QuizRepository) {
    suspend operator fun invoke(gameResult: GameResult) = repository.saveGameResult(gameResult)
}