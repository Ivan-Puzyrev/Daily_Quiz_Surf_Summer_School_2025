package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Difficulty
import jakarta.inject.Inject

class GetQuestionsUseCase @Inject constructor (private val repository: QuizRepository) {

    suspend operator fun invoke(category: Int, difficulty: String) =
        repository.getQuestions(category, difficulty)
}