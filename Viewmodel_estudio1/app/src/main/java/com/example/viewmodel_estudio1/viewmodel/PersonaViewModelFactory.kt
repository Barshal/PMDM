package com.example.viewmodel_estudio1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel_estudio1.data.PersonaRepository

// 1. La fábrica recibe las mismas dependencias que el ViewModel
class PersonaViewModelFactory(
    private val repository: PersonaRepository
) : ViewModelProvider.Factory {

    // 2. Este es el metodo que Android llamará para crear el ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 3. Comprueba si la clase que se pide es PersonaViewModel
        if (modelClass.isAssignableFrom(PersonaViewModel::class.java)) {
            // 4. Si lo es, crea una instancia pasándole el repositorio y la devuelve
            @Suppress("UNCHECKED_CAST")
            return PersonaViewModel(repository) as T
        }
        // 5. Si se pide crear otro tipo de ViewModel, lanza un error
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
