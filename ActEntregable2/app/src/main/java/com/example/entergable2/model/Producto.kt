package com.example.entergable2.model

import com.google.gson.annotations.SerializedName

data class Producto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val url: String,
    @SerializedName("thumbnail") val imageUrl: String
)
