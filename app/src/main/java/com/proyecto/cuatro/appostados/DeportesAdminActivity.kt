package com.proyecto.cuatro.appostados

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.cuatro.appostados.data.model.Deporte
import com.proyecto.cuatro.appostados.data.services.DeporteService
import com.proyecto.cuatro.appostados.ui.deporte.DeporteAdapter

class DeportesAdminActivity : AppCompatActivity() {

    private lateinit var deporteAdapter: DeporteAdapter
    private val deporteService = DeporteService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deportes_admin)
        obtenerDeportes()
    }

    private fun obtenerDeportes() {
        deporteService.getAllDeporte { deportes ->
            runOnUiThread {
                if (deportes != null) {
                    mostrarDeportes(deportes)
                } else {
                    // Manejar el caso en que no se puedan obtener los deportes
                }
            }
        }
    }

    private fun mostrarDeportes(deportes: List<Deporte>) {
        deporteAdapter = DeporteAdapter(deportes)
        val recyclerViewSports = findViewById<RecyclerView>(R.id.recyclerViewSports)

        val layoutManager = GridLayoutManager(this, NUM_COLUMNAS)
        recyclerViewSports.layoutManager = layoutManager
        recyclerViewSports.adapter = deporteAdapter
    }

    companion object {
        private const val NUM_COLUMNAS = 3 // Define el número de columnas deseado
    }
}