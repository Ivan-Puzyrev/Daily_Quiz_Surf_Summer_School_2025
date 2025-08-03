package com.ivanpuzyrev.data

import android.app.Application
import android.util.Log
import com.ivanpuzyrev.data.dto_model.CategoriesResponseDTO
import com.ivanpuzyrev.data.dto_model.QuestionsResponseDTO
import com.ivanpuzyrev.data.retrofit.ApiService
import com.ivanpuzyrev.data.retrofit.RetrofitObject
import com.ivanpuzyrev.data.room.AppDatabase
import com.ivanpuzyrev.data.room.GameResultsDao
import com.ivanpuzyrev.domain.ApiError
import com.ivanpuzyrev.domain.ApiResult
import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl @Inject constructor (
    private val mapper: Mapper,
    private val apiService: ApiService,
    private val gameResultsDao: GameResultsDao
) : QuizRepository {


    override suspend fun deleteGameResult(gameResultId: Int): Boolean {
        return gameResultsDao.deleteGameResult(gameResultId) > 0
    }

    override suspend fun getCategories(): ApiResult<List<Category>> {
        val categoriesResponseDTO = safeApiCall { apiService.getCategories() }
        return when (categoriesResponseDTO) {
            is ApiResult.Error -> ApiResult.Error(
                if (categoriesResponseDTO.error is ApiError.Network) ApiError.Network else ApiError.Unknown
            )

            is ApiResult.Success<CategoriesResponseDTO> -> ApiResult.Success(
                mapper.mapCategoryDtoListToEntityList(categoriesResponseDTO.data.categories)
            )
        }
    }

    override fun getGameResults(): Flow<List<GameResult>> {
        return gameResultsDao.getGameResults()
            .map { mapper.mapGameResultDbModelListToEntityList(it) }
    }

    override suspend fun getQuestions(
        category: Int,
        difficulty: String
    ): ApiResult<List<Question>> {
        val questionsResponseDTO = safeApiCall {
            apiService.getQuestions(
                category = category,
                difficulty = difficulty
            )
        }
        Log.d("QuizRepositoryImpl", questionsResponseDTO.toString())
        return when (questionsResponseDTO) {
            is ApiResult.Error -> ApiResult.Error(
                if (questionsResponseDTO.error is ApiError.Network) ApiError.Network else ApiError.Unknown
            )

            is ApiResult.Success<QuestionsResponseDTO> -> if (questionsResponseDTO.data.responseCode ==
                0
            ) ApiResult.Success(
                mapper.mapQuestionDtoListToEntityList(
                    questionsResponseDTO.data.questions
                )
            ) else ApiResult.Error(
                ApiError.Unknown
            )
        }
    }

    override suspend fun saveGameResult(gameResult: GameResult) {
        gameResultsDao.addGameResult(mapper.mapEntityToGameResultDbModel(gameResult))
    }
}