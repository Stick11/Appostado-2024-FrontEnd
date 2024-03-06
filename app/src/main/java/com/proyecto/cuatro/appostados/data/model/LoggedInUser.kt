package com.proyecto.cuatro.appostados.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val token: String,
    val id: String,
    val name: String
   // val rolUser: Int
)