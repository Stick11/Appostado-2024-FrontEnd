package com.proyecto.cuatro.appostados

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.cuatro.appostados.databinding.ActivityMainBinding
import android.content.Intent
import com.proyecto.cuatro.appostados.ui.login.LoginActivity
import android.content.Context
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.MasterService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var loginService: LoginService
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val masterService = MasterService()
        loginService = LoginService(masterService)

        val isLoggedIn = loginService.isLoggedIn // CON ESTO SABEMOS SI EL USUARIO ESTA LOGEADO

        if (isLoggedIn) {
            // SI ESTA LOGUEADO CONSULTADOS PARA VER QUE TIPO DE USUARIO ES
            val user = loginService.user
            if (user != null) {
                when (user.rolUser) {
                    1 ->  startActivity(Intent(this, HomeAdmin::class.java))
                    //2 ->  startActivity(Intent(this, HomeAdmin::class.java))
                    else ->  startActivity(Intent(this, LoginActivity::class.java))// por si es uno que no sea 1 o 2 que no debería existir
                }
            }

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

}