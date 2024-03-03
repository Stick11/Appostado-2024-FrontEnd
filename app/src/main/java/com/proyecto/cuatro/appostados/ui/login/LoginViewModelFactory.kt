package com.proyecto.cuatro.appostados.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val applicationContext: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            val masterService = MasterService()

            val loginService = LoginServiceSingleton.getInstance(masterService)

            return LoginViewModel(applicationContext, loginService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
