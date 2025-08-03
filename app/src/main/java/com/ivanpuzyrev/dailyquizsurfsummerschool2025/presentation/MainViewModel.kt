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
import com.ivanpuzyrev.domain.entities.Difficulty
import com.ivanpuzyrev.domain.entities.GameResult
import com.ivanpuzyrev.domain.entities.Question
import com.ivanpuzyrev.domain.usecases.GetCategoriesUseCase
import com.ivanpuzyrev.domain.usecases.GetQuestionsUseCase
import com.ivanpuzyrev.domain.usecases.SaveGameResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = QuizRepositoryImpl(application)
    private val getCategoriesUseCase = GetCategoriesUseCase(repository)
    private val getQuestionsUseCase = GetQuestionsUseCase(repository)
    private val saveGameResultUseCase = SaveGameResultUseCase(repository)

    val mainScreenState: MutableStateFlow<MainScreenState> = MutableStateFlow(Initial)

    var categoryList: List<Category> = emptyList()
    var selectedCategoryId:Int = 0
    var selectedDifficulty: String = ""
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
        val question = questionsList[currentQuestion - 2]
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
            selectedDifficulty = difficulty
            selectedCategoryId = categoryId
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

    fun startTheNewGame() {
        mainScreenState.value = Initial
    }

    fun finishTheGame() {
        viewModelScope.launch {
            val zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("dd MMMM", Locale("ru"))
            val formattedDate = zonedDateTime.format(formatter)
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
            val formattedTime = zonedDateTime.format(timeFormatter)
            val difficulty = Difficulty.entries.find { it.difficulty == selectedDifficulty } ?: Difficulty.UNKNOWN
            val gameResult = GameResult(
                date = formattedDate,
                time = formattedTime,
                category = categoryList.find { it.id == selectedCategoryId }?.name ?: "",
                difficulty = difficulty,
                correctAnswers = answers.filter { it.selectedAnswer == it.question.correctAnswer }.size,
                answers = answers
            )
            mainScreenState.value = MainScreenState.GameFinished(gameResult)
            saveGameResultUseCase(gameResult)
            clearTheResults()
        }
    }

    fun clearTheResults() {
        categoryList = emptyList()
        selectedCategoryId = 0
        selectedDifficulty = ""
        questionsList = emptyList()
        answers.clear()
        currentQuestion = 1
    }

    companion object {
        const val TOTAL_NUMBER_OF_QUESTIONS = 5
    }


}