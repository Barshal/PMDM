package com.example.entergable2.model

// Esta clase representa la respuesta completa de la API.
// La variable 'products' debe tener el mismo nombre que la clave en el JSON.
data class ProductoResponse(
    val products: List<Producto>
)
