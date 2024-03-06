package com.proyecto.cuatro.appostados.ui.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proyecto.cuatro.appostados.data.model.UserRegistration
import com.proyecto.cuatro.appostados.data.model.RegistrationResponse
import com.proyecto.cuatro.appostados.data.services.UserRegisterService
import com.proyecto.cuatro.appostados.util.Validator

class UserRegistrationViewModel(private val userRegisterService: UserRegisterService) : ViewModel() {

    private val _registrationStatus = MutableLiveData<RegistrationResponse>()
    val registrationStatus: LiveData<RegistrationResponse> = _registrationStatus

    // LiveData para manejar la redirección después de un registro exitoso
    private val _redirectToLogin = MutableLiveData<Boolean>()
    val redirectToLogin: LiveData<Boolean> = _redirectToLogin

    fun registerUser(userRegistration: UserRegistration) {
        userRegisterService.registerUser(userRegistration) { success, message ->
            if (success) {
                _registrationStatus.value = RegistrationResponse(true, message)
                // Si el registro es exitoso, lanzar el evento para redirigir al LoginActivity
                _redirectToLogin.value = true
            } else {
                _registrationStatus.value = RegistrationResponse(false, message)
            }
        }
    }
}

