package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository

class GetCategoriesUseCase(private val repository: QuizRepository) {
    suspend operator fun invoke() = repository.getCategories()
}