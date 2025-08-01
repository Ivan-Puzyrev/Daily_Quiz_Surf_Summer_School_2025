package com.ivanpuzyrev.data.dto_model

import com.google.gson.annotations.SerializedName

data class CategoriesResponseDTO (
    @SerializedName("trivia_categories") val categories: List<CategoryDTO>
)