package com.example.entergable2.network


import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Una clase de petición de Volley personalizada que convierte una respuesta JSON
 * en un objeto (o lista) del tipo especificado, usando Gson.
 *
 * @param T El tipo de objeto en el que se debe convertir el JSON.
 */
class GsonRequest<T> : Request<T> {

    private val gson = Gson()
    private val clazz: Class<T>?
    private val typeToken: TypeToken<T>?
    private val headers: MutableMap<String, String>?
    private val listener: Response.Listener<T>

    /**
     * Constructor para peticiones que esperan un único objeto JSON.
     * Ejemplo: ProductResponse::class.java
     */
    constructor(
        method: Int,
        url: String,
        clazz: Class<T>,
        listener: Response.Listener<T>,
        errorListener: Response.ErrorListener,
        headers: MutableMap<String, String>? = null
    ) : super(method, url, errorListener) {
        this.clazz = clazz
        this.typeToken = null
        this.headers = headers
        this.listener = listener
    }

    /**
     * Constructor para peticiones que esperan un array JSON (una lista de objetos).
     * Ejemplo: object : TypeToken<List<Producto>>() {}
     */
    constructor(
        method: Int,
        url: String,
        typeToken: TypeToken<T>,
        listener: Response.Listener<T>,
        errorListener: Response.ErrorListener,
        headers: MutableMap<String, String>? = null
    ) : super(method, url, errorListener) {
        this.clazz = null
        this.typeToken = typeToken
        this.headers = headers
        this.listener = listener
    }


    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )

            val result: T = when {
                // Si se usó el constructor para listas (con TypeToken), usamos este.
                typeToken != null -> gson.fromJson(json, typeToken.type)
                // Si no, usamos el constructor para objetos simples (con Class).
                else -> gson.fromJson(json, clazz)
            }

            Response.success(result, HttpHeaderParser.parseCacheHeaders(response))

        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }
}
