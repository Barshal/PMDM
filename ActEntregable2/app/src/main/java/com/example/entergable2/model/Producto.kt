package com.example.entergable2.model

import com.google.gson.annotations.SerializedName

data class Producto(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val category: String? = null,
    @SerializedName("thumbnail") val imageUrl: String? = null
)
