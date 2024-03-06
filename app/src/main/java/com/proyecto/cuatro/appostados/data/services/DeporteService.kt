package com.proyecto.cuatro.appostados.data.services

import com.proyecto.cuatro.appostados.data.model.Deporte
import okhttp3.Call
import okhttp3.Callback
import org.json.JSONObject
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import org.json.JSONArray
import okhttp3.Request
import java.io.IOException
import okhttp3.Response

class DeporteService : MasterService() {

    fun getDeporteById(productId: Int, callback: (Deporte?) -> Unit) {
        val endpoint = "/product/$productId"
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejar la falla de la solicitud
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val productJson = responseBody?.let { JSONObject(it) }
                    val id = productJson?.optInt("id")
                    val name = productJson?.optString("name")
                    val age = productJson?.optInt("age")
                    val deporte = if (id != null && name != null) {
                        Deporte(id, name, age)
                    } else {
                        null
                    }
                    callback(deporte)
                } else {
                    // Manejar la respuesta no exitosa
                    callback(null)
                }
            }
        })
    }

    fun createDeporte(name: String, callback: (Deporte?) -> Unit) {
        val requestBody = "{\"name\": \"$name\"}".toRequestBody("application/json".toMediaTypeOrNull())

        httpPostRequestAsync("/products", requestBody) { result ->
            when (result) {
                is HttpResult.Success -> {
                    val responseBody = result.response.body?.string()
                    val productJson = responseBody?.let { JSONObject(it) }
                    val id = productJson?.optInt("id")
                    val productName = productJson?.optString("name")
                    val age = productJson?.optInt("age")
                    if (id != null && productName != null) {
                        callback(Deporte(id, productName, age))
                    } else {
                        callback(null)
                    }
                }
                is HttpResult.Error -> {
                    callback(null)
                }
            }
        }
    }



    fun getAllDeporte(callback: (List<Deporte>?) -> Unit) {
        val endpoint = "/api/v1/dog"

        httpGetRequestAsync(endpoint) { response ->
            val deportesList = mutableListOf<Deporte>()

            if (response != null && response.isSuccessful) {
                val responseBody = response.body?.string()
                val jsonArray = JSONArray(responseBody)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id = jsonObject.optInt("id")
                    val nombre = jsonObject.optString("name")
                    val edad = jsonObject.optInt("age")
                    val deporte = Deporte(id, nombre, edad)
                    deportesList.add(deporte)
                }
            }

            callback(deportesList.takeIf { it.isNotEmpty() })
        }
    }

    fun updateDeporte(productId: Int, newName: String, callback: (Deporte?) -> Unit) {
        val requestBody = "{\"name\": \"$newName\"}".toRequestBody("application/json".toMediaTypeOrNull())

        httpPutRequestAsync("/products/$productId", requestBody) { response ->
            if (response != null && response.isSuccessful) {
                val responseBody = response.body?.string()
                val productJson = responseBody?.let { JSONObject(it) }
                val id = productJson?.optInt("id")
                val productName = productJson?.optString("name")
                val age = productJson?.optInt("age")
                if (id != null && productName != null) {
                    callback(Deporte(id, productName, age))
                } else {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }


}