package com.example.entergable2.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.entergable2.model.Categoria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken // <-- ¡Este import es clave para las listas!

// 'object' crea una instancia única (Singleton) y también implementa nuestra interfaz.
object CategoryRepository : CategoryApi {

    // Esta función privada gestiona la cola de Volley.
    // Al estar en el Singleton, la cola se reutiliza eficientemente.
    private fun getQueue(context: Context) = Volley.newRequestQueue(context.applicationContext)

    // Aquí implementamos el método definido en la interfaz CategoryApi.
    override fun obtenerCategorias(
        context: Context,
        onSuccess: (List<Categoria>) -> Unit,
        onError: (String) -> Unit
    ) {
        // La URL del endpoint que devuelve un array de categorías.
        val url = "https://dummyjson.com/products/categories"

        // Creamos la petición de tipo String con Volley.
        val stringRequest = StringRequest(
            Request.Method.GET, // El metodo de la petición
            url,
            { response -> // Este bloque se ejecuta si la llamada es EXITOSA.
                Log.d("REPO_SUCCESS", "Respuesta de categorías: $response")
                try {
                    val gson = Gson()

                    // Para que Gson sepa que el JSON es una lista de 'Categoria',
                    // necesitamos crear un 'TypeToken' que represente ese tipo especifico.
                    val tipoLista = object : TypeToken<List<Categoria>>() {}.type
                    val categorias = gson.fromJson<List<Categoria>>(response, tipoLista)
                    // ------------------------------------

                    // todo fue bien, llamamos al callback 'onSuccess'
                    // y le pasamos la lista de categorías ya convertida.
                    onSuccess(categorias)

                } catch (e: Exception) {
                    Log.e("REPO_GSON_ERROR", "Error al parsear la lista: ${e.message}")
                    // Si falla el parseo, llamamos al callback de error.
                    onError("Error al procesar los datos recibidos.")
                }
            },
            { error -> // Este bloque se ejecuta si la llamada FALLA (problemas de red, etc.).
                Log.e("REPO_VOLLEY_ERROR", "Error en Volley: ${error.message}")
                // Llamamos al callback de error con un mensaje descriptivo.
                onError("No se pudo conectar con el servidor.")
            }
        )

        // Finalmente, añadimos nuestra petición a la cola para que Volley la ejecute.
        getQueue(context).add(stringRequest)
    }
}
