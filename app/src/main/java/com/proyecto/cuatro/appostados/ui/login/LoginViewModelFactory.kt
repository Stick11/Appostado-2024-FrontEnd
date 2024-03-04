package com.proyecto.cuatro.appostados.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService

class LoginViewModelFactory(private val applicationContext: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val masterService = MasterService()

            // Obtain SharedPreferences instance using applicationContext
            val sharedPreferences = applicationContext.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

            // Pass both masterService and sharedPreferences to getInstance
            val loginService = LoginServiceSingleton.getInstance(masterService, sharedPreferences)

            return LoginViewModel(applicationContext, loginService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
