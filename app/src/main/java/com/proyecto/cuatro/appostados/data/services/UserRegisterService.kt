package com.proyecto.cuatro.appostados.data.services

import com.proyecto.cuatro.appostados.data.model.UserRegistration
import com.proyecto.cuatro.appostados.data.model.RegistrationResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class UserRegisterService : MasterService() {

    fun registerUser(userRegistration: UserRegistration, callback: (Boolean, String) -> Unit) {
        // Preparar el cuerpo de la solicitud JSON a partir del objeto userRegistration
        val requestBodyJson = JSONObject().apply {
            put("name", userRegistration.name)
            put("lastName1", userRegistration.lastNames)
            put("email", userRegistration.email)
            put("password", userRegistration.password)
            // Formatea la fecha de nacimiento a un string si es necesario
            put("birthDate", SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(userRegistration.birthDate))
            put("foto", userRegistration.foto) // Asegúrate de que este sea el formato correcto esperado por tu backend
        }.toString()

        val requestBody = requestBodyJson.toRequestBody("application/json".toMediaTypeOrNull())

        // Reemplaza "/path/to/registration" con el endpoint real de tu API para el registro
        httpPostRequestAsync("/registro", requestBody) { response ->
            if (response != null && response.isSuccessful) {
                // Asigna true a la primera parte del callback para indicar éxito
                // y pasa un mensaje apropiado como segunda parte del callback
                callback(true, "Registro exitoso")
            } else {
                // Asigna false a la primera parte del callback para indicar falla
                // y pasa un mensaje de error como segunda parte del callback
                callback(false, "Error en el registro")
            }
        }
    }
}
