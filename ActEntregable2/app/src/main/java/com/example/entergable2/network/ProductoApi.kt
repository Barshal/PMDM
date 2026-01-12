// en /network/ProductApi.kt
package com.example.entergable2.network

import android.content.Context
import com.example.entergable2.model.Producto

interface ProductoApi {


    // TODOS los productos disponibles.
    fun obtenerTodosLosProductos(
        context: Context,
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    )

    // Lista FILTRADA
    fun obtenerProductosPorCategoria(
        context: Context,
        categoria: String,
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    )
}
