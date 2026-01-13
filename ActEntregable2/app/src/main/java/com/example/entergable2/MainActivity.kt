package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.entergable2.adapter.ProductosAdapter
import com.example.entergable2.databinding.ActivityMainBinding
import com.example.entergable2.model.Categoria
import com.example.entergable2.model.Product
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Creamos el binding para obtener los elementos de la parte grafica
    private lateinit var binding: ActivityMainBinding // no inicializar aqui
    private val listaCategorias = mutableListOf<String>()
    private val listaProductos = mutableListOf<Product>()
    private var carrito = mutableListOf<Product>()
    private lateinit var adapter: ProductosAdapter
    private lateinit var categoriaSelecionada: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inicializar aqui
        setContentView(binding.root)

        obtenerCategorias()
        obtenerProductos()

        acciones()
        initRecyclerView()
    }

    private fun acciones() {
        binding.btnNavCarrito.setOnClickListener(this)
        binding.spinnerCategorias.onItemSelectedListener
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnNavCarrito.id -> {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /*
    * ----------------------------- LLAMADAS LOS ENDPOINTS -----------------------------
    * */

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
        configurarSpinnerCategorias()
    }

    private fun obtenerProductos() {
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
        for (i in 0..productosArray.length() - 1) {
            val productos: JSONObject = productosArray.getJSONObject(i)
            val producto: Product = gson.fromJson(productos.toString(), Product::class.java)
            Log.v("conexion", producto.title.toString())
            listaProductos.add(producto)
            // Notificamos al adapter que el conjunto de datos ha cambiado.
            // Esto le dice al RecyclerView que se redibuje con la nueva información.
            adapter.notifyDataSetChanged()

        }
    }

    /*
    * ----------------------------- SPINNER -----------------------------
    * */
    private fun configurarSpinnerCategorias() {
        // Creamos el Adapter. Es el "puente" entre los datos y la vista.
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaCategorias
        )
        binding.spinnerCategorias.adapter = adapter
        //Añadimos el listener para capturar la selección
        binding.spinnerCategorias.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Podemos obtener el String directamente de nuestra lista.
                    categoriaSelecionada = listaCategorias[position]

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

    /*
    * ----------------------------- RECYCLER VIEW -----------------------------
    * */

    private fun initRecyclerView() {
        adapter = ProductosAdapter(listaProductos, this) {
            carrito.add(it)
        }
        binding.recyclerViewProductos.adapter = adapter
        binding.recyclerViewProductos.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
}