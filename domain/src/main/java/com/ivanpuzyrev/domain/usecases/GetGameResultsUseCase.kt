package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository

class GetGameResultsUseCase(private val repository: QuizRepository) {

    operator fun invoke() = repository.getGameResults()
}