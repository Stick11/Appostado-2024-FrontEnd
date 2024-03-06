package com.proyecto.cuatro.appostados.ui.deporte

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.cuatro.appostados.R
import com.proyecto.cuatro.appostados.data.model.Deporte
import android.widget.Filter

class DeporteAdapter(private var deportes: List<Deporte>) :

    RecyclerView.Adapter<DeporteAdapter.DeporteViewHolder>() {
    private var deportesListFull: List<Deporte> = deportes

    inner class DeporteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val editButton: ImageButton = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        init {
            editButton.setOnClickListener {
                val position = adapterPosition
            }

            deleteButton.setOnClickListener {
                val position = adapterPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeporteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_deporte, parent, false)
        return DeporteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeporteViewHolder, position: Int) {
        val deporte = deportes[position]
        holder.textViewName.text = deporte.name
    }

    override fun getItemCount(): Int {
        return deportes.size
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Deporte>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(deportesListFull)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in deportesListFull) {
                        if (item.name.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                deportes = results?.values as ArrayList<Deporte>
                notifyDataSetChanged()
            }
        }
    }
}