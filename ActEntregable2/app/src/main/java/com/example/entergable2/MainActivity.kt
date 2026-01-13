package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.entergable2.network.CategoryRepository
import com.example.entergable2.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Creamos el binding para obtener los elementos de la parte grafica
    private lateinit var binding: ActivityMainBinding // no inicializar aqui

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inicializar aqui
        setContentView(binding.root)
        obtenerCategorias()
        acciones()
    }

    private fun obtenerCategorias() {
        // La llamada es muy sencilla y legible.
        CategoryRepository.obtenerCategorias(
            context = this,
            onSuccess = { listaDeCategorias ->
                // ---- ESTE BLOQUE SE EJECUTA SI LA LLAMADA ES EXITOSA ----

                // 'listaDeCategorias' es la List<Categoria> que nos devuelve el repositorio.
                Log.d("MAIN_ACTIVITY_SUCCESS", "Categorías recibidas: ${listaDeCategorias.size}")
                Toast.makeText(
                    this,
                    "Se encontraron ${listaDeCategorias.size} categorías",
                    Toast.LENGTH_LONG
                ).show()

                // Imprimir sus nombres en el Logcat.
                listaDeCategorias.forEach { categoria ->
                    Log.i("MAIN_ACTIVITY_ITEM", " - Categoría: ${categoria.categoryName}")
                }
            },
            onError = { mensajeDeError ->
                // ---- ESTE BLOQUE SE EJECUTA SI ALGO SALIÓ MAL ----
                Log.e("MAIN_ACTIVITY_ERROR", "Error: $mensajeDeError")
                Toast.makeText(this, "Error: $mensajeDeError", Toast.LENGTH_SHORT).show()

            }
        )
    }

    private fun acciones() {
        binding.btnNavCarrito.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnNavCarrito.id -> {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }
}