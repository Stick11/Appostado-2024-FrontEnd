package com.proyecto.cuatro.appostados.data.services

import android.os.Handler
import android.os.Looper
import com.proyecto.cuatro.appostados.data.model.UserRegistration
import com.proyecto.cuatro.appostados.data.model.RegistrationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale


class UserRegisterService : MasterService() {
    private val masterService = MasterService()
    fun registerUser(userRegistration: UserRegistration, callback: (Boolean, String) -> Unit) {
        val requestBodyJson = JSONObject().apply {
            put("name", userRegistration.name)
            put("surname", userRegistration.lastNames)
            put("email", userRegistration.email)
            put("profilePictureUrl", userRegistration.foto)
            put("dateOfBirth", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(userRegistration.birthDate))
            put("password", userRegistration.password)
        }.toString()

        val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())
        val endpoint="/api/v1/auth/sign-up"
        masterService.httpPostRequestAsync("/sport", requestBody) { response ->
            if (response != null && response.isSuccessful) {
                    val response = response
                    when (response.code) {
                        // Registro exitoso
                        200 -> postResult(true, "Registro exitoso", callback)
                        // Error de validación de datos
                        // Error de validación de datos
                        400 -> {
                            val responseBody = response.body?.string() ?: "{}" // Default to empty JSON if null
                            val jsonObject = JSONObject(responseBody)
                            val errorCode = jsonObject.optString("errorCode", "")
                            val errorMessage = when (errorCode) {
                                "InvalidPasswordException" -> "La contraseña proporcionada es inválida."
                                "UserNotFound" -> "El usuario no fue encontrado."
                                // Add more cases as needed
                                else -> jsonObject.optString("errorMessage", "Error de validación")
                            }
                            postResult(false, errorMessage, callback)
                        }
                        // Error interno del servidor
                        500 -> postResult(false, "Error interno del servidor", callback)
                        // Otro código de estado
                        else -> postResult(false, "Error desconocido: ${response.code}", callback)
                    }
                } else {
                    postResult(false, "Error de conexión", callback)
                }

            }
        }
    }

    private fun postResult(success: Boolean, message: String, callback: (Boolean, String) -> Unit) {
        Handler(Looper.getMainLooper()).post {
            callback(success, message)
        }
    }

    private fun parseErrorMessage(responseBody: String?): String? {
        return try {
            val json = JSONObject(responseBody)
            json.optString("errorMessage")
        } catch (e: Exception) {
            null
        }
    }


