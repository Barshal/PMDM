package com.example.viewmodel_estudio1.data

interface PersonaRepository {
    fun obtenerPersona(): Persona
    fun obtenerPersonas(): List<Persona>
}