package com.proyecto.cuatro.appostados.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.cuatro.appostados.data.services.UserRegisterService

class UserRegistrationViewModelFactory(
    private val userRegisterService: UserRegisterService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserRegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserRegistrationViewModel(userRegisterService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
