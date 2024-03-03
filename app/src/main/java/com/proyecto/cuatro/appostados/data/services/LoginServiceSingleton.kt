package com.proyecto.cuatro.appostados.data.services

object LoginServiceSingleton {
    private var instance: LoginService? = null

    fun getInstance(masterService: MasterService): LoginService {
        if (instance == null) {
            instance = LoginService(masterService)
        }
        return instance!!
    }
}