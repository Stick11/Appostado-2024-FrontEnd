package com.proyecto.cuatro.appostados.data.repository

import android.content.Context

class UserRepository(private val context: Context) {
    fun saveToken(token: String) {
        SecurePreferences.storeUserToken(context.applicationContext, token)
    }

    fun clearUserData() {
        SecurePreferences.clearToken(context)
        // Also clear any other user data as needed
    }
}