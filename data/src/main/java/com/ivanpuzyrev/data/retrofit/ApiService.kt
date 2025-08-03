package com.ivanpuzyrev.data.retrofit

import com.ivanpuzyrev.data.dto_model.CategoriesResponseDTO
import com.ivanpuzyrev.data.dto_model.QuestionsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String = "multiple"
    ): QuestionsResponseDTO

    @GET("api_category.php")
    suspend fun getCategories(): CategoriesResponseDTO
}