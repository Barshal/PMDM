package com.example.entergable2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val listaCategorias = mutableListOf<String>()

    // Lista maestra que SIEMPRE contendrá TODOS los productos de la API
    private val listaProductosCompleta = mutableListOf<Product>()
    private var carrito = mutableListOf<Product>()
    private lateinit var adapter: ProductosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicio la UI una sola vez con datos vacíos
        initMenu()
        initRecyclerView()

        // 2. Recoco los datos de la red
        obtenerCategorias()
        obtenerProductos()

        // 3. Configuro los listeners de los botones
        //acciones()
    }

//    private fun acciones() {
//        binding.btnNavCarrito.setOnClickListener(this)
//    }
//
//    // dejo la funcion a modo de estudio pero no aplica ya que el boton no se usa
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            binding.btnNavCarrito.id -> {
//                // Lógica para enviar el json entre activites
//                val gson = Gson()
//                val carritoJsonString = gson.toJson(carrito)
//                val intent = Intent(this, SecondActivity::class.java)
//                intent.putExtra("CARRITO_JSON", carritoJsonString)
//                startActivity(intent)
//            }
//        }
//    }

    /*
    * ----------------------------- LLAMADAS A LOS ENDPOINTS -----------------------------
    * */
    private fun obtenerCategorias() {
        val url = "https://dummyjson.com/products/categories"
        val request = JsonArrayRequest(url, {
            procesarPeticionCategorias(it)
        }, {
            Log.v("conexion", "Conexion fallida categorias")
        })
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun procesarPeticionCategorias(categoriasArray: JSONArray) {
        listaCategorias.clear()
        val gson = Gson()
        for (i in 0 until categoriasArray.length()) {
            val categoriaJson: JSONObject = categoriasArray.getJSONObject(i)
            val categoria: Categoria =
                gson.fromJson(categoriaJson.toString(), Categoria::class.java)
            listaCategorias.add(categoria.categoryName.toString())
        }
        // Una vez que tenemos las categorías configuramos el Spinner
        configurarSpinnerCategorias()
    }

    private fun obtenerProductos() {
        val url = "https://dummyjson.com/products"
        val request = JsonObjectRequest(url, {
            procesarPeticionProductos(it)
            Log.v("conexion", "Conexion fallida productos")

        }, {
            Log.v("conexion", "Conexion fallida productos")
        })
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun procesarPeticionProductos(param: JSONObject) {
        listaProductosCompleta.clear()
        val gson = Gson()
        val productosArray: JSONArray = param.getJSONArray("products")

        for (i in 0 until productosArray.length()) {
            val productoJson: JSONObject = productosArray.getJSONObject(i)
            val producto: Product = gson.fromJson(productoJson.toString(), Product::class.java)
            listaProductosCompleta.add(producto)
        }
        // Cuando los productos llegan actualizamos el adapter para mostrar la lista completa
        adapter.actualizarLista(listaProductosCompleta)
    }

    /*
    * ----------------------------- SPINNER -----------------------------
    * */
    private fun configurarSpinnerCategorias() {
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCategorias)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategorias.adapter = spinnerAdapter

        binding.spinnerCategorias.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val categoriaSeleccionada = listaCategorias[position]
                    // Cada vez que el usuario elige algo filtramos
                    filtrarProductos(categoriaSeleccionada)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Se deja vacío para no crashear
                }
            }
    }

    // Filtro de productos
    private fun filtrarProductos(categoria: String) {
        // Usamos la función 'filter' de Kotlin para crear una nueva lista
        val productosFiltrados = listaProductosCompleta.filter { producto ->
            producto.category.equals(categoria, ignoreCase = true)
        }
        // Actualizamos el adapter con la nueva lista filtrada
        adapter.actualizarLista(productosFiltrados)
    }

    /*
    * ----------------------------- RECYCLER VIEW -----------------------------
    * */
    private fun initRecyclerView() {
        // El adapter se crea UNA SOLA VEZ con una lista vacía.
        adapter = ProductosAdapter(mutableListOf(), this) { productoClicado ->
            carrito.add(productoClicado)
            Toast.makeText(this, "${productoClicado.title} añadido al carrito", Toast.LENGTH_SHORT)
                .show()
        }
        binding.recyclerViewProductos.adapter = adapter
        binding.recyclerViewProductos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    /*
  * ----------------------------- MENU -----------------------------
  * */
    private fun initMenu() {
        setSupportActionBar(binding.idToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.itemVercarrito -> {
               // Lógica para enviar el json entre activites
               val gson = Gson()
               val carritoJsonString = gson.toJson(carrito)
               val intent = Intent(this, SecondActivity::class.java)
               intent.putExtra("CARRITO_JSON", carritoJsonString)
               startActivity(intent)
           }
       }
        return true
    }

}