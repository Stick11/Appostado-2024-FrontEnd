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

    fun login(email: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {
        val credentials = UserCredentials(email, password)
        val requestBody = Gson().toJson(credentials)
            .toRequestBody("application/json".toMediaTypeOrNull())

        masterService.httpPostRequestAsync("api/v1/auth/sign-in", requestBody) { result ->
            when (result) {
                is MasterService.HttpResult.Success -> {
                    val responseBody = result.response.body?.string()
                    val signInResponseJson = JSONObject(responseBody)
                    val token = signInResponseJson.getString("token")
                    val id = signInResponseJson.getJSONObject("user").getString("id")
                    val name = signInResponseJson.getJSONObject("user").getString("name")
                    val signInResponse = LoggedInUser(token, id, name)
                    callback(Result.Success(signInResponse))
                }
                is MasterService.HttpResult.Error -> {
                    callback(Result.Error(Exception("Failed to authenticate user")))
                }
            }
        }
    }




    private fun setLoggedInUser(loggedInUser: LoggedInUser?) {
        this._user = loggedInUser
        // Save the user to SharedPreferences
        sharedPreferences.edit().putString("loggedInUser", Gson().toJson(loggedInUser)).apply()
    }

}