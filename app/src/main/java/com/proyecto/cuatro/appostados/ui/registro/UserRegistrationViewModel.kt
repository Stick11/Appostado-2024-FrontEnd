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

    fun registerUser(userRegistration: UserRegistration) {
        if (!Validator.isValidEmail(userRegistration.email) ||
            !Validator.isValidPassword(userRegistration.password) ||
            !Validator.isAge18OrOver(userRegistration.birthDate)) {
            _registrationStatus.value = RegistrationResponse(false, "Por favor digíte los datos correctamente")
            return
        }

        // Llamar a userRegisterService para realizar el registro en el backend
        userRegisterService.registerUser(userRegistration) { success, message ->
            _registrationStatus.value = RegistrationResponse(success, message)
        }
    }
}
