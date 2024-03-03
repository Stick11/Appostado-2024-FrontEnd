package com.proyecto.cuatro.appostados

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.cuatro.appostados.databinding.ActivityMainBinding
import android.content.Intent
import com.proyecto.cuatro.appostados.ui.login.LoginActivity
import android.content.Context
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startActivity(Intent(this, LoginActivity::class.java))

        val masterService = MasterService()
        val loginService = LoginServiceSingleton.getInstance(masterService)
        val isLoggedIn = loginService.isLoggedIn // CON ESTO SABEMOS SI EL USUARIO ESTA LOGEADO

        if (isLoggedIn) {

            // SI ESTA LOGUEADO CONSULTADOS PARA VER QUE TIPO DE USUARIO ES
            val user = loginService.getUserSistema()
            if (user != null) {
                when (user.rolUser) {
                    1 ->  startActivity(Intent(this, HomeAdmin::class.java))
                    //2 ->  startActivity(Intent(this, HomeAdmin::class.java))
                    else ->  startActivity(Intent(this, LoginActivity::class.java))
                }
            }

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

}