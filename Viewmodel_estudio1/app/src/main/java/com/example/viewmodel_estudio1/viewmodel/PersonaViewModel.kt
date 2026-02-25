package com.example.viewmodel_estudio1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodel_estudio1.data.Persona // ¡IMPORTANTE! Asegúrate de que esta ruta es correcta
import com.example.viewmodel_estudio1.data.PersonaRepository // ¡IMPORTANTE! Asegúrate de que esta ruta es correcta
import kotlinx.coroutines.launch

// 1. La clase hereda de ViewModel y recibe el repositorio en su constructor
class PersonaViewModel(private val repository: PersonaRepository) : ViewModel() {

    // 2. LiveData PRIVADO y MUTABLE para uso interno del ViewModel
    private val _personas = MutableLiveData<List<Persona>>()

    // 3. LiveData PÚBLICO e INMUTABLE que la Vista observará
    val personas: LiveData<List<Persona>> = _personas

    // 4. LiveData para gestionar el estado de carga (feedback para el usuario)
    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    // 5. Función que la Vista llamará para iniciar la carga de datos
    fun cargarPersonas() {
        // 6. Usamos viewModelScope para lanzar una corrutina segura
        viewModelScope.launch {
            // Ponemos el estado de carga a "true" antes de empezar
            _cargando.value = true

            try {
                // 7. Llamamos a la función (suspend) del repositorio
                val listaPersonas = repository.obtenerPersonas()

                // 8. Actualizamos el LiveData con los datos recibidos
                _personas.value = listaPersonas
            } catch (e: Exception) {
                // Opcional pero recomendado: Manejar errores
                println("Error al cargar personas: ${e.message}")
            } finally {
                // Al finalizar (con éxito o error), ocultamos el indicador de carga
                _cargando.value = false
            }
        }
    }
}
