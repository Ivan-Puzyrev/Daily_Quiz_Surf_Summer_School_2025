package com.ivanpuzyrev.data

import android.text.Html
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ivanpuzyrev.data.db_model.GameResultDbModel
import com.ivanpuzyrev.data.dto_model.CategoryDTO
import com.ivanpuzyrev.data.dto_model.QuestionDTO
import com.ivanpuzyrev.domain.entities.Answer
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question

class Mapper {

    private val gson = Gson()

    private fun mapQuestionDtoToEntity(questionDTO: QuestionDTO): Question {
        return Question(
            question = Html.fromHtml(questionDTO.question, Html.FROM_HTML_MODE_LEGACY).toString(),
            correctAnswer = Html.fromHtml(questionDTO.correctAnswer, Html.FROM_HTML_MODE_LEGACY)
                .toString(),
            incorrectAnswers = questionDTO.incorrectAnswers.map {
                Html.fromHtml(
                    it,
                    Html.FROM_HTML_MODE_LEGACY
                ).toString()
            }
        )
    }

    fun mapQuestionDtoListToEntityList(listQuestionDTO: List<QuestionDTO>): List<Question> {
        return listQuestionDTO.map { mapQuestionDtoToEntity(it) }
    }

    private fun mapCategoryDtoToEntity(categoryDTO: CategoryDTO): Category {
        return Category(
            id = categoryDTO.id,
            name = categoryDTO.name
        )
    }

    fun mapCategoryDtoListToEntityList(listCategoryDTO: List<CategoryDTO>): List<Category> {
        return listCategoryDTO.map { mapCategoryDtoToEntity(it) }
    }

    fun mapGameResultDbModelToEntity(gameResultDbModel: GameResultDbModel): GameResult {
        val type = object : TypeToken<List<Answer>>() {}.type

        return GameResult(
            id = gameResultDbModel.id,
            date = gameResultDbModel.date,
            time = gameResultDbModel.time,
            category = gameResultDbModel.category,
            difficulty = gameResultDbModel.difficulty,
            correctAnswers = gameResultDbModel.correctAnswers,
            answers = gson.fromJson<List<Answer>>(gameResultDbModel.answers, type)
        )
    }

    fun mapEntityToGameResultDbModel(gameResult: GameResult): GameResultDbModel {
        return GameResultDbModel(
            id = gameResult.id,
            date = gameResult.date,
            time = gameResult.time,
            category = gameResult.category,
            difficulty = gameResult.difficulty,
            correctAnswers = gameResult.correctAnswers,
            answers = gson.toJson(gameResult.answers)
        )
    }

    fun mapGameResultDbModelListToEntityList(listGameResultDbModel: List<GameResultDbModel>): List<GameResult> {
        return listGameResultDbModel.map { mapGameResultDbModelToEntity(it) }
    }

}