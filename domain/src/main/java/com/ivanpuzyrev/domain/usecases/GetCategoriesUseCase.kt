package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository
import jakarta.inject.Inject

class GetCategoriesUseCase @Inject constructor (private val repository: QuizRepository) {
    suspend operator fun invoke() = repository.getCategories()
}