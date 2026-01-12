package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.entergable2.databinding.ActivitySecondBinding
import com.example.entergable2.network.CategoryRepository
import com.example.entergable2.network.ProductoRepository

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        obtenerProductos()
        accciones()
    }

    private fun obtenerProductos() {
        // La llamada es muy sencilla y legible.
        ProductoRepository.obtenerTodosLosProductos(
            context = this,
            onSuccess = { listaDeProductos ->
                // ---- ESTE BLOQUE SE EJECUTA SI LA LLAMADA ES EXITOSA ----

                // 'lsitaDeProductos' es la List<Producto> que nos devuelve el repositorio.
                Log.d("MAIN_ACTIVITY_SUCCESS", "Porducto recibidao: ${listaDeProductos.size}")
                Toast.makeText(
                    this,
                    "Se encontraron ${listaDeProductos.size} productos",
                    Toast.LENGTH_LONG
                ).show()

                // Imprimir sus nombres en el Logcat.
                listaDeProductos.forEach { producto ->
                    Log.i("MAIN_ACTIVITY_ITEM", " - Producto: ${producto.title}")
                }
            },
            onError = { mensajeDeError ->
                // ---- ESTE BLOQUE SE EJECUTA SI ALGO SALIÓ MAL ----
                Log.e("MAIN_ACTIVITY_ERROR", "Error: $mensajeDeError")
                Toast.makeText(this, "Error: $mensajeDeError", Toast.LENGTH_SHORT).show()

            }
        )
    }



    private fun accciones() {
        binding.btnNavMain.setOnClickListener (this)
    }
    override fun onClick(v: View) {
        when (v.id){
            binding.btnNavMain.id -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}