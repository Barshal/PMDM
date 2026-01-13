package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.entergable2.databinding.ActivityMainBinding
import com.example.entergable2.model.Categoria
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Creamos el binding para obtener los elementos de la parte grafica
    private lateinit var binding: ActivityMainBinding // no inicializar aqui
    private val listaCategorias = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inicializar aqui
        setContentView(binding.root)



        obtenerCategorias()
        acciones()
    }

    private fun acciones() {
        binding.btnNavCarrito.setOnClickListener(this)
        binding.spinnerCategorias.onItemSelectedListener
    }

    private fun obtenerCategorias() {
        val url = "https://dummyjson.com/products/categories"
        val request: JsonArrayRequest = JsonArrayRequest(url, {
            Log.v("conexion", "Conexion correcta categorias")
            procesarPeticionCategorias(it)
        }, {
            Log.v("conexion", "Conexion fallida categorias")
        })
        // Añadirla request
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun procesarPeticionCategorias(categoriasArray: JSONArray) {
        val gson = Gson()
        for (i in 0..categoriasArray.length() - 1) {
            val categoriasJson: JSONObject = categoriasArray.getJSONObject(i)
            val categoria: Categoria =
                gson.fromJson(categoriasJson.toString(), Categoria::class.java)
            // Me creo mi lista de opciones para el spinner
            listaCategorias.add(categoria.categoryName.toString())
            Log.v("conexion", categoria.categoryName.toString())
        }
       configurarSpinner()

    }

    private fun configurarSpinner() {
        // Creamos el Adapter. Es el "puente" entre los datos y la vista.
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaCategorias
        )
        binding.spinnerCategorias.adapter = adapter
        //Añadimos el listener para capturar la selección
        binding.spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Podemos obtener el String directamente de nuestra lista.
                val itemSeleccionado = listaCategorias[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnNavCarrito.id -> {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }
}