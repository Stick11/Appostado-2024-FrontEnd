package com.proyecto.cuatro.appostados.data.model

import java.util.Date

data class UserRegistration(
    val name: String,
    val lastNames: String,
    val birthDate: Long,
    val foto : String,
    val email: String,
    val password: String
)

data class RegistrationResponse(
    val success: Boolean,
    val message: String
)

