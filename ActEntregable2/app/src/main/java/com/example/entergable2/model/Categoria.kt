package com.example.entergable2.model
import com.google.gson.annotations.SerializedName

data class Categoria (
    @SerializedName("slug") val categorySlug: String,
    @SerializedName("name") val categoryName: String,
    @SerializedName("url") val categoryUrl: String
)

