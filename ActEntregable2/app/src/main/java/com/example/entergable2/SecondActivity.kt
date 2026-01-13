package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.entergable2.databinding.ActivitySecondBinding
import com.example.entergable2.model.Product
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accciones()
        obtenerProductor()

        //Pasos para realizar la peticion a una url con volley
        //1. Peticion del tipo correcto
        //2. Añado la peticion a la pila de volley
    }


    private fun accciones() {
        binding.btnNavMain.setOnClickListener(this)
    }

    private fun obtenerProductor() {
        val url = "https://dummyjson.com/products"
        val request: JsonObjectRequest = JsonObjectRequest(url, {
            Log.v("conexion", "Conexion correcta")
            procesarPeticionProductos(it)
        }, {
            Log.v("conexion", "Conexion fallida productos")
        })
        // Añadirla request
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun procesarPeticionProductos(param: JSONObject) {
        val gson = Gson()
        val productosArray: JSONArray = param.getJSONArray("products")
        Log.v("conexion", param.toString())
        Log.v("conexion", productosArray.toString())
        for (i in 0 .. productosArray.length() -1){
            val productos: JSONObject = productosArray.getJSONObject(i)
            val producto: Product = gson.fromJson(productos.toString(), Product::class.java)
            Log.v("conexion", producto.title.toString())

        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnNavMain.id -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}