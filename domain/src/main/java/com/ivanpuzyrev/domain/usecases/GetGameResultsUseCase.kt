package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import jakarta.inject.Inject

class GetGameResultsUseCase @Inject constructor (private val repository: QuizRepository) {

    operator fun invoke() = repository.getGameResults()
}