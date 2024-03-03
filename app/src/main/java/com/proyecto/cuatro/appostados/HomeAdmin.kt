package com.proyecto.cuatro.appostados

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.ui.login.LoginActivity
import com.proyecto.cuatro.appostados.ui.login.LoginViewModel
import com.proyecto.cuatro.appostados.ui.login.LoginViewModelFactory
class HomeAdmin : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences // LOCAL STORAGE
    private lateinit var loginService: LoginService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)
    }

    fun logOut(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        loginService.logout()
    }
}