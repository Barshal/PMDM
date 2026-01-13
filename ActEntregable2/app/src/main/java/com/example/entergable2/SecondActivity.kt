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

        //Pasos para realizar la peticion a una url con volley
        //1. Peticion del tipo correcto
        //2. Añado la peticion a la pila de volley
    }


    private fun accciones() {
        binding.btnNavMain.setOnClickListener(this)
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