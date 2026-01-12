package com.example.entergable2.network

import android.content.Context
import com.example.entergable2.model.Categoria

interface CategoryApi {
    /**
     * Obtiene una lista de todas las categorías desde el endpoint.
     * @param context Contexto de la aplicación.
     * @param onSuccess Callback que se ejecuta con la lista de categorías si la llamada es exitosa.
     * @param onError Callback que se ejecuta con un mensaje de error si algo falla.
     */
    fun obtenerCategorias(
        context: Context,
        onSuccess: (List<Categoria>) -> Unit, // <-- Devolverá una LISTA de Categoria
        onError: (String) -> Unit
    )
}