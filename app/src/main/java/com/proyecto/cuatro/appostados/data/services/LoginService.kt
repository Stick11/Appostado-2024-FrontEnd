package com.proyecto.cuatro.appostados.data.services

import android.content.SharedPreferences
import com.google.gson.Gson
import com.proyecto.cuatro.appostados.data.model.LoggedInUser
import com.proyecto.cuatro.appostados.data.model.UserCredentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class LoginService(private val masterService: MasterService, private val sharedPreferences: SharedPreferences) {

    private var _user: LoggedInUser? = null
        get() {
            if (field == null) {
                // Attempt to load the user from SharedPreferences
                val userJson = sharedPreferences.getString("loggedInUser", null)
                if (userJson != null) {
                    return Gson().fromJson(userJson, LoggedInUser::class.java)
                }
            }
            return field
        }
        private set

    val isLoggedIn: Boolean
        get() = user != null

    val user: LoggedInUser?
        get() = _user

    init {
        // Attempt to load the user on initialization
        _user = null
    }

    fun logout() {
        _user = null
        sharedPreferences.edit().remove("loggedInUser").apply()
    }

    fun getUserSistema(): LoggedInUser? {
        return user
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        val credentials = UserCredentials(username, password)
        val requestBody = Gson().toJson(credentials)
            .toRequestBody("application/json".toMediaTypeOrNull())

        /*
        val response = masterService.httpPostRequest("api/login", requestBody)
        if (response != null && response.isSuccessful) {
            val responseBody = response.body?.string()
            val loggedInUserJson = JSONObject(responseBody)

            val userId = loggedInUserJson.optInt("userId")
            val displayName = loggedInUserJson.optString("displayName")
            val rolUSer = loggedInUserJson.optInt("rolUser")

            val loggedInUser = LoggedInUser(userId.toString(), displayName, rolUSer)
            setLoggedInUser(loggedInUser)
            return Result.Success(loggedInUser)
        } else {
            return Result.Error(Exception("Failed to authenticate user"))
        }

        */


        val result = masterService.login(username, password)
        when (result) {
            is Result.Success -> {
                val user = result.data
                setLoggedInUser(user)
                return Result.Success(user)
            }
            is Result.Error -> {
                return result
            }
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        this._user = loggedInUser
        // Save the user to SharedPreferences
        sharedPreferences.edit().putString("loggedInUser", Gson().toJson(loggedInUser)).apply()
    }

}