package com.example.entergable2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.entergable2.databinding.ActivitySecondBinding
import com.example.entergable2.model.Product
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySecondBinding
    // Creamos una lista para guardar el carrito que recibimos
    private val carritoRecibido = mutableListOf<Product>()
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recuperarProcesarCarrito()

        acciones()
    }

    private fun acciones() {
        binding.btnNavMain.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnNavMain.id -> {

            }
        }
    }

    private fun recuperarProcesarCarrito() {
        // 1. Obtenemos el JSONArray
        val carritoJson = intent.getStringExtra("CARRITO_JSON")
        Log.v("carrito", carritoJson.toString())

        // Gson para crear la mutableListOf a partir del JSONArray
        val gson = Gson()
        val carritoArray: JSONArray = JSONArray(carritoJson)
        Log.v("carrito", carritoArray.toString())

        for (i in 0 until carritoArray.length()) {
            val productoJson: JSONObject = carritoArray.getJSONObject(i)
            val producto: Product = gson.fromJson(productoJson.toString(), Product::class.java)
            carritoRecibido.add(producto)
            Log.v("carrito",producto.title.toString())
        }
    }
}
