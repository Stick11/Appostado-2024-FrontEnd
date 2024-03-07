package com.proyecto.cuatro.appostados.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.cuatro.appostados.data.repository.UserRepository
import com.proyecto.cuatro.appostados.data.services.LoginService
import com.proyecto.cuatro.appostados.data.services.LoginServiceSingleton
import com.proyecto.cuatro.appostados.data.services.MasterService

class LoginViewModelFactory(private val userRepository: UserRepository, private val loginService: LoginService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository, loginService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

