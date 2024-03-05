package com.proyecto.cuatro.appostados.ui.deporte

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.cuatro.appostados.R
import com.proyecto.cuatro.appostados.data.model.Deporte

class DeporteAdapter(private val deportes: List<Deporte>) :
    RecyclerView.Adapter<DeporteAdapter.DeporteViewHolder>() {

    inner class DeporteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewId: TextView = itemView.findViewById(R.id.textViewId)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewAge: TextView = itemView.findViewById(R.id.textViewAge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeporteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_deporte, parent, false)
        return DeporteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeporteViewHolder, position: Int) {
        val deporte = deportes[position]
        holder.textViewId.text = deporte.id.toString()
        holder.textViewName.text = deporte.name
        holder.textViewAge.text = deporte.age.toString()
    }

    override fun getItemCount(): Int {
        return deportes.size
    }
}