package com.example.trivialclase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trivialclase.databinding.ItemRecyclerviewPreguntaBinding
import com.example.trivialclase.model.Pregunta

class PregutnaAdapter(var lista: List<Pregunta>, var contexto: Context) :
    RecyclerView.Adapter<PregutnaAdapter.MyHolder>() {
    inner class MyHolder(var binding: ItemRecyclerviewPreguntaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return lista.size
    }

    // Crea el holder, el patron de las filas con la inner clas que a su vez utiliza el xml creado
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        var binding: ItemRecyclerviewPreguntaBinding =
            ItemRecyclerviewPreguntaBinding.inflate(
                LayoutInflater.from(contexto), parent, false
            )
        return MyHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        val item: Pregunta = lista[position]
        holder.binding.txtPregunta.text = item.question.toString()

    }

}



