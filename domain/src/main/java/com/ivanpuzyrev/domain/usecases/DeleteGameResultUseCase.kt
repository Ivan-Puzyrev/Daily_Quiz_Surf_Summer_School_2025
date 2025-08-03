package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.GameResult
import jakarta.inject.Inject

class DeleteGameResultUseCase @Inject constructor (private val quizRepository: QuizRepository) {

    suspend operator fun invoke(gameResultId: Int) = quizRepository.deleteGameResult(gameResultId)
}