package com.proyecto.cuatro.appostados.data.model

import java.util.Date

data class UserRegistration(
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val birthDate: Date,
    val email: String,
    val password: String
)

data class RegistrationResponse(
    val success: Boolean,
    val message: String
)

