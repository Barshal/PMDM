// Pega este código en tu archivo C:/.../network/ProductoRepository.kt

package com.example.entergable2.network

import android.content.Context
import com.android.volley.Request // <-- Asegúrate de importar esto
import com.android.volley.toolbox.Volley
// Importa los modelos y la interfaz que hemos definido
import com.example.entergable2.model.Producto
import com.example.entergable2.model.ProductoResponse
// Importa la herramienta que acabamos de crear
import com.example.entergable2.network.GsonRequest

// Hacemos que el Repositorio implemente la interfaz que define sus responsabilidades
object ProductoRepository : ProductoApi {

    private fun getQueue(context: Context) = Volley.newRequestQueue(context.applicationContext)

    /**
     * Implementación para obtener TODOS los productos.
     */
    override fun obtenerTodosLosProductos(
        context: Context,
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "https://dummyjson.com/products"

        // Usamos nuestra herramienta GsonRequest
        val request = GsonRequest(
            Request.Method.GET,
            url,
            ProductoResponse::class.java, // Le decimos qué tipo de objeto esperamos
            { response ->
                // Éxito: Extraemos la lista del objeto de respuesta
                onSuccess(response.products)
            },
            { error ->
                // Error
                onError(error.message ?: "Error al obtener todos los productos.")
            }
        )
        // Añadimos la petición a la cola para que se ejecute
        getQueue(context).add(request)
    }

    /**
     * Implementación para obtener productos filtrados por categoría.
     */
    override fun obtenerProductosPorCategoria(
        context: Context,
        categoria: String,
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "https://dummyjson.com/products/category/$categoria"

        // Usamos nuestra herramienta GsonRequest de nuevo
        val request = GsonRequest(
            Request.Method.GET,
            url,
            ProductoResponse::class.java, // También esperamos un objeto ProductResponse
            { response ->
                // Éxito: Extraemos la lista del objeto de respuesta
                onSuccess(response.products)
            },
            { error ->
                // Error
                onError(error.message ?: "Error al obtener productos de la categoría '$categoria'.")
            }
        )
        // Añadimos la petición a la cola para que se ejecute
        getQueue(context).add(request)
    }
}
