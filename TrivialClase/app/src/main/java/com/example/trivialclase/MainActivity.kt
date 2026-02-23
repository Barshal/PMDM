package com.example.trivialclase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.trivialclase.adapter.PregutnaAdapter
import com.example.trivialclase.databinding.ActivityMainBinding
import com.example.trivialclase.model.Pregunta
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    // https://univeuropeaes.sharepoint.com/:v:/r/sites/2025PBAS001204M2100/Documentos%20compartidos/General/Recordings/1711%20-%20Gesti%C3%B3n%20gr%C3%A1fico%20+%20logica-20251117_193312-Grabaci%C3%B3n%20de%20la%20reuni%C3%B3n.mp4?csf=1&web=1&e=huXqSa&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbE1vZGUiOiJtaXMiLCJyZWZlcnJhbFZpZXciOiJwb3N0cm9sbC1jb3B5bGluayIsInJlZmVycmFsUGxheWJhY2tTZXNzaW9uSWQiOiJkM2I3OWExMC01NTc2LTQ5NDMtYWVjNi05NjcxYjc0MDUzZTYifX0%3D
    // Creamos el binding para obtener los elementos de la parte grafica
    private lateinit var binding: ActivityMainBinding // no inicializar aqui
    private val listaProductosPregunta = mutableListOf<Pregunta>()
    private lateinit var adapter: PregutnaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inicializar aqui
        setContentView(binding.root)

        obternerPregunta()
        initRecyclerView()
    }


    private fun obternerPregunta() {
        val url = "https://opentdb.com/api.php?amount=1"
        val request = JsonObjectRequest(url, {
            Log.v("conexion", "Conexion ok pregunta")

            procesarPeticionPregunta(it)
        }, {
            Log.v("conexion", "Conexion fallida pregunta")
        })
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun procesarPeticionPregunta(param: JSONObject) {
        listaProductosPregunta.clear()
        val gson = Gson()
        val productosArray: JSONArray = param.getJSONArray("results")

        for (i in 0 until productosArray.length()) {
            val preguntaJson: JSONObject = productosArray.getJSONObject(i)
            val pregunta: Pregunta = gson.fromJson(preguntaJson.toString(), Pregunta::class.java)
            listaProductosPregunta.add(pregunta)
            Log.v("conexion", pregunta.toString())
        }

    }

    private fun initRecyclerView() {
        adapter = PregutnaAdapter(mutableListOf(), this)
        binding.rcPregunta.adapter = adapter
        binding.rcPregunta.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}