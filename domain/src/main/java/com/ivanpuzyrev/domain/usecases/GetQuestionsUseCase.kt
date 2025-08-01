package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.Difficulty

class GetQuestionsUseCase(val repository: QuizRepository) {

    suspend operator fun invoke(category: String, difficulty: Difficulty) =
        repository.getQuestions(category, difficulty)
}