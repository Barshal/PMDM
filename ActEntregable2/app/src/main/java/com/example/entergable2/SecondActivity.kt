package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.entergable2.adapter.CarritoAdapter
import com.example.entergable2.databinding.ActivitySecondBinding
import com.example.entergable2.model.Product
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.round

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var adapter: CarritoAdapter

    // Creamos una lista para guardar el carrito que recibimos
    private val carritoRecibido = mutableListOf<Product>()
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. INICIALIZA el RecyclerView con un adapter vacío
        initRecyclerView()
        initMenu()


        // 2. RECUPERA Y PROCESA los datos del carrito
        recuperarProcesarCarrito()


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
            if (producto.price != null) {
                total += producto.price
            } else {
                total += 0
            }
            Log.v("carrito", producto.title.toString())
        }
        adapter.actualizarLista(carritoRecibido)
        binding.txtTotalCarrito.text = round(total).toString() + " €"

    }

    private fun initRecyclerView() {
        // El adapter se crea UNA SOLA VEZ con una lista vacía.
        adapter = CarritoAdapter(mutableListOf(), this)
        binding.recyclerViewProductosCarrito.adapter = adapter
        binding.recyclerViewProductosCarrito.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initMenu() {
        setSupportActionBar(binding.idToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.second_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemVaciarCarrito -> {
                carritoRecibido.clear()
                adapter.actualizarLista(carritoRecibido)
                total = 0.0
                binding.txtTotalCarrito.text = round(total).toString() + " €"
            }
            R.id.itemComprar -> {
                Toast.makeText(
                    this,
                    "Enhorabuena, compra por valor de ${total} realizada",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            R.id.itemVolver -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}
