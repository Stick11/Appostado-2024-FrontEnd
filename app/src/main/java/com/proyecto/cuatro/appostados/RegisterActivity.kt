package com.proyecto.cuatro.appostados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.proyecto.cuatro.appostados.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        // Ejemplo para configurar un listener para el botón de registro
        binding.registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        // Aquí implementarías la validación de los datos y la lógica para enviarlos al servidor
    }
}
