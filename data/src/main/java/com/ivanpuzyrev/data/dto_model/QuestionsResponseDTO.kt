package com.ivanpuzyrev.data.dto_model

import com.google.gson.annotations.SerializedName

data class QuestionsResponseDTO(
    @SerializedName("response_code") val responseCode: Int,
    @SerializedName("results") val questions: List<QuestionDTO>
)