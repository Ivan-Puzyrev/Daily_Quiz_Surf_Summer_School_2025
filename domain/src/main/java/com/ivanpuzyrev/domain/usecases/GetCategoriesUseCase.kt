package com.ivanpuzyrev.domain.usecases

import com.ivanpuzyrev.domain.QuizRepository

class GetCategoriesUseCase(val repository: QuizRepository) {
    suspend operator fun invoke() = repository.getCategories()
}