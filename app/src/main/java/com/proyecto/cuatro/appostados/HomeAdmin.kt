package com.proyecto.cuatro.appostados

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService
import com.proyecto.cuatro.appostados.ui.login.LoginActivity

class HomeAdmin : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences // LOCAL STORAGE
    private lateinit var loginService: LoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        // Inicializar sharedPreferences
        sharedPreferences = getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

        // Obtener la instancia de LoginService a través del singleton
        val masterService = MasterService() // Instancia de MasterService
        loginService = LoginServiceSingleton.getInstance(masterService, sharedPreferences)
    }

    fun logOut(view: View) {
        loginService.logout()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun irDeportes(view: View){
        startActivity(Intent(this, DeportesAdminActivity::class.java))
    }
}
