package com.ivanpuzyrev.data.dto_model

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)