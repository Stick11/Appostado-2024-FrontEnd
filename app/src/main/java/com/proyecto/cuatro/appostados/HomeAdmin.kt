package com.proyecto.cuatro.appostados

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.MasterService
import com.proyecto.cuatro.appostados.databinding.ActivityHomeAdminBinding
import com.proyecto.cuatro.appostados.databinding.ActivityLoginBinding
import com.proyecto.cuatro.appostados.ui.login.LoginActivity

class HomeAdmin : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences // LOCAL STORAGE
    private lateinit var loginService: LoginService
    private lateinit var binding: ActivityHomeAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
        val masterService = MasterService()
        loginService = LoginService(masterService, sharedPreferences)

        binding.btnCerrarSesion.setOnClickListener {
            logOut()
        }

    }


    private fun logOut() {
        loginService.logout()
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun irDeportes(view: View){
        startActivity(Intent(this, DeportesAdminActivity::class.java))
    }
}