package com.proyecto.cuatro.appostados

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.cuatro.appostados.data.model.Deporte
import com.proyecto.cuatro.appostados.data.services.DeporteService
import com.proyecto.cuatro.appostados.ui.deporte.DeporteAdapter

class DeportesAdminActivity : AppCompatActivity() {

    private lateinit var deporteAdapter: DeporteAdapter
    private val deporteService = DeporteService()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deportes_admin)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                deporteAdapter.getFilter().filter(newText)
                return true
            }
        })

        obtenerDeportes()
    }

    private fun obtenerDeportes() {
        // Mostrar el diálogo de progreso
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Cargando deportes...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        // Obtener los deportes
        deporteService.getAllDeporte { deportes ->
            runOnUiThread {
                // Ocultar el diálogo de progreso
                progressDialog.dismiss()

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
        private const val NUM_COLUMNAS = 1 // Define el número de columnas deseado
    }
}