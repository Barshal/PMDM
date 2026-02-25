package com.example.gestor_siniestros.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gestor_siniestros.R
import com.example.gestor_siniestros.adapter.MovieAdapter
import com.example.gestor_siniestros.databinding.FragmentListBinding
import com.example.gestor_siniestros.model.Movie
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var movieAdapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter(movieList, requireContext())
        initGUI()
        // 1.- Peticion del tipo correcto
        realizarPeticionJson()
    }

    private fun initGUI() {
        binding.rvMovies.adapter =  movieAdapter
        binding.rvMovies.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }

    override fun onResume() {
        super.onResume()
        binding.btnNavegar1.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_mainFragment)
        }
    }

    private fun realizarPeticionJson() {
        val queue = Volley.newRequestQueue(context)
        val url = "https://fooapi.com/api/movies"
        val request = JsonObjectRequest(url, {
            Log.v("Volley", "Peticion correcta")
            procesarRespuesta(it)
        }, {
            Log.v("Volley", "Peticion incorrecta")
        })
        queue.add(request)
    }

    private fun ListFragment.procesarRespuesta(param: JSONObject) {
        val gson = Gson()
        val movieArray: JSONArray = param.getJSONArray("data")
        for (i in 0..movieArray.length() - 1) {
            val movieJSON: JSONObject = movieArray.getJSONObject(i)
            val movie = gson.fromJson(movieJSON.toString(), Movie::class.java)
            movieList.add(movie)
        }
        movieAdapter.notifyDataSetChanged()
        Log.v("Volley", movieList.toString())

    }


}
