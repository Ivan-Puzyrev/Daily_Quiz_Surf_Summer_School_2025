package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository

class GetGameResultsUseCase(val repository: QuizRepository) {

    operator fun invoke() = repository.getGameResults()
}