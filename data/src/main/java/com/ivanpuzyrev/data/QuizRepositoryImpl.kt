package com.ivanpuzyrev.data

import android.app.Application
import com.ivanpuzyrev.data.dto_model.CategoriesResponseDTO
import com.ivanpuzyrev.data.dto_model.QuestionsResponseDTO
import com.ivanpuzyrev.data.retrofit.RetrofitObject
import com.ivanpuzyrev.data.room.AppDatabase
import com.ivanpuzyrev.domain.ApiError
import com.ivanpuzyrev.domain.ApiResult
import com.ivanpuzyrev.domain.QuizRepository
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuizRepositoryImpl(application: Application) : QuizRepository {

    val mapper = Mapper()
    val apiService = RetrofitObject.apiService
    val gameResultsDao = AppDatabase.getInstance(application).gameResultsDao()

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