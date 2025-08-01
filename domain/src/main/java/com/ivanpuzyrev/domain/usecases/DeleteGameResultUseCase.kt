package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.GameResult

class DeleteGameResultUseCase(val quizRepository: QuizRepository) {

    suspend operator fun invoke(gameResultId: Int) = quizRepository.deleteGameResult(gameResultId)
}