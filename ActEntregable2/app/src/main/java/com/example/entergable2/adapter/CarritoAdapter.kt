package com.example.entergable2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.entergable2.R
import com.example.entergable2.databinding.ItemRecyclerProductoCarritoBinding
import com.example.entergable2.model.Product

class CarritoAdapter(var lista: List<Product>, var contexto: Context) :
    RecyclerView.Adapter<CarritoAdapter.MyHolder>() {
    inner class MyHolder(var binding: ItemRecyclerProductoCarritoBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Numero de filas que se pintan
    override fun getItemCount(): Int {
        return lista.size
    }

    // Crea el holder, el patron de las filas con la inner clas que a su vez utiliza el xml creado
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyHolder {
        var binding: ItemRecyclerProductoCarritoBinding =
            ItemRecyclerProductoCarritoBinding.inflate(
                LayoutInflater.from(contexto), parent, false
            )
        return MyHolder(binding)
    }

    //Renderiza los elementos
    override fun onBindViewHolder(
        holder: MyHolder,
        position: Int
    ) {
        val item: Product = lista[position]
        holder.binding.txtProductTitle.text = item.title
        Glide.with(contexto).load(item.thumbnail).placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.iamgeRecycler)
    }

    fun actualizarLista(nuevaLista: List<Product>) {
        (this.lista as MutableList<Product>).clear()
        (this.lista as MutableList<Product>).addAll(nuevaLista)
        notifyDataSetChanged() // ¡Esta línea le dice al RecyclerView que se redibuje!
    }

    fun addlista(product: Product){
        this.lista.toMutableList().add(product)
        notifyItemInserted(lista.size -1)
        }
}


