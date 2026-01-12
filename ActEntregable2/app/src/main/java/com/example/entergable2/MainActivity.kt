package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.entergable2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Creamos el binding para obtener los elementos de la parte grafica
    private lateinit var binding: ActivityMainBinding // no inicializar aqui

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inicializar aqui
        setContentView(binding.root)

        acciones()
    }

    private fun acciones() {
        binding.btnNavCarrito.setOnClickListener {this}
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