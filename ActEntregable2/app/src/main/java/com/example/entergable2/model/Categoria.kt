package com.example.entergable2.model
import com.google.gson.annotations.SerializedName

data class Categoria (
    @SerializedName("slug") val categorySlug: String? = null,
    @SerializedName("name") val categoryName: String? = null,
    @SerializedName("url") val categoryUrl: String? = null
)

