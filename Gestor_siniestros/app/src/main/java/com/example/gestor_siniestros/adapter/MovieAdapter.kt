package com.example.gestor_siniestros.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gestor_siniestros.R
import com.example.gestor_siniestros.databinding.ItemRecyclerMovieBinding
import com.example.gestor_siniestros.model.Movie

class MovieAdapter(var lista: List<Movie>, var contexto: Context) :
    RecyclerView.Adapter<MovieAdapter.MyHolder>() {

    inner class MyHolder(var binding: ItemRecyclerMovieBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        val binding = ItemRecyclerMovieBinding.inflate(
            LayoutInflater.from(contexto),
            parent,
            false
        )
        return MyHolder(binding)
    }

    // onBindViewHolder: Conecta los datos de la película con las vistas del Holder.
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val item = lista[position]

        // Asignamos los datos del item a las vistas del layout
        holder.binding.tvMovieTitle.text = item.title
        holder.binding.tvMovieYear.text = item.year
        holder.binding.tvMovieGenre.text = item.genre
        // Cargar la imagen del póster desde la URL usando Glide
        Glide.with(holder.binding.root.context) // Usamos el contexto del item
            .load(item.poster) // La URL de la imagen
            .placeholder(R.mipmap.ic_launcher) // Opcional: una imagen mientras carga
            .error(R.mipmap.ic_launcher) // Opcional: una imagen si falla la carga
            .into(holder.binding.imgMoviePoster) // El ImageView de destino
    }
}