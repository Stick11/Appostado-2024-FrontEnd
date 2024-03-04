package com.proyecto.cuatro.appostados.data.services

import android.content.SharedPreferences

object LoginServiceSingleton {
    private var instance: LoginService? = null

    fun getInstance(masterService: MasterService, sharedPreferences: SharedPreferences): LoginService {
        if (instance == null) {
            instance = LoginService(masterService, sharedPreferences)
        }
        return instance!!
    }
}

