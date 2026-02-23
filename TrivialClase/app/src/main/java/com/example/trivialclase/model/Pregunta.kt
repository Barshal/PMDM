package com.example.trivialclase.model

import com.google.gson.annotations.SerializedName

data class Pregunta (
    val type: String? = null,
    val difficulty: String? = null,
    val category: String? = null,
    val question: String? = null,

    @SerializedName("correct_answer")
    val correctAnswer: String? = null,

    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>? = null



)