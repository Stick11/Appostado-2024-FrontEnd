package com.proyecto.cuatro.appostados.data.services

import com.google.gson.Gson
import com.proyecto.cuatro.appostados.data.model.LoggedInUser
import com.proyecto.cuatro.appostados.data.model.UserCredentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class LoginService(private val masterService: MasterService) {

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null // LE QUITAMOS EL USUARIO AL SISTEMA
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        val credentials = UserCredentials(username, password)
        val requestBody = Gson().toJson(credentials)
            .toRequestBody("application/json".toMediaTypeOrNull())

        /*

        val response = masterService.httpPostRequest("api/login", requestBody)
        return if (response != null && response.isSuccessful) {
            val responseBody = response.body?.string()
            val loggedInUserJson = JSONObject(responseBody)

            val userId = loggedInUserJson.optInt("userId")
            val displayName = loggedInUserJson.optString("displayName")
            val rolUSer = loggedInUserJson.optInt("rolUser")

            val loggedInUser = LoggedInUser(userId.toString(), displayName, rolUSer)
            setLoggedInUser((loggedInUser)) // LE SETEAMOS EL USUARIO AL SISTEMA
            Result.Success(loggedInUser)


        } else {
            Result.Error(Exception("Failed to authenticate user"))
        }

        */


        val result = masterService.login(username, password)
        val loggedInUser = when (result) {
            is Result.Success -> {
                val user = result.data
                setLoggedInUser(user)
                user
            }
            is Result.Error -> {
                null
            }
        }
        setLoggedInUser((loggedInUser)) // LE SETEAMOS EL USUARIO AL SISTEMA
        Result.Success(result)
        return result

    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        this.user = loggedInUser
    }

}