package com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.MainScreenState.*
import com.ivanpuzyrev.dailyquizsurfsummerschool2025.presentation.composables.StartScreen
import com.ivanpuzyrev.data.QuizRepositoryImpl
import com.ivanpuzyrev.domain.ApiResult
import com.ivanpuzyrev.domain.entities.Answer
import com.ivanpuzyrev.domain.entities.Category
import com.ivanpuzyrev.domain.entities.Question
import com.ivanpuzyrev.domain.usecases.GetCategoriesUseCase
import com.ivanpuzyrev.domain.usecases.GetQuestionsUseCase
import com.ivanpuzyrev.domain.usecases.SaveGameResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuizRepositoryImpl(application)
    private val getCategoriesUseCase = GetCategoriesUseCase(repository)
    private val getQuestionsUseCase = GetQuestionsUseCase(repository)
    private val saveGameResultUseCase = SaveGameResultUseCase(repository)

    val mainScreenState: MutableStateFlow<MainScreenState> = MutableStateFlow(Initial)
    var categoryList: List<Category> = emptyList()
    var questionsList: List<Question> = emptyList()
    val answers = mutableListOf<Answer>()
    var currentQuestion = 1


    fun goToSettingsScreen() {
        viewModelScope.launch {
            mainScreenState.value = StartScreenLoading
            val apiResult = getCategoriesUseCase()
            when (apiResult) {
                is ApiResult.Success -> {
                    categoryList = apiResult.data
                    mainScreenState.value = Settings(categoryList)
                }

                is ApiResult.Error -> mainScreenState.value = StartScreenError
            }
        }

    }

    fun answerTheQuestion(answer: String) {
        val question = questionsList[currentQuestion-2]
        val answer = Answer(question, answer)
        answers.add(answer)
        val correctAnswers = answers.count { it.selectedAnswer == it.question.correctAnswer }
        Log.d("MainViewModel", "Correct Answers: $correctAnswers")
    }

    fun getNextQuestion() {

        mainScreenState.value =
            Question(questionsList[currentQuestion - 1], currentQuestion, TOTAL_NUMBER_OF_QUESTIONS)
        currentQuestion++
    }

    fun loadQuestions(categoryId: Int, difficulty: String) {
        viewModelScope.launch {
            mainScreenState.value = SettingsLoading
            val apiResult = getQuestionsUseCase(categoryId, difficulty)
            when (apiResult) {
                is ApiResult.Success -> {
                    questionsList = apiResult.data
                    getNextQuestion()
                }

                is ApiResult.Error -> {
                    mainScreenState.value = SettingsError(categoryList)
                    Log.d("MainViewModel", "Error: ${apiResult.error}")
                }
            }
        }
    }

    companion object {
        const val TOTAL_NUMBER_OF_QUESTIONS = 5
    }


}