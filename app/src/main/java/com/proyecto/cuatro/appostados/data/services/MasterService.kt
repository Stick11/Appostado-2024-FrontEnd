package com.proyecto.cuatro.appostados.data.services

import com.proyecto.cuatro.appostados.data.model.LoggedInUser
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.RequestBody
import java.io.IOException
import java.util.UUID
import java.util.concurrent.TimeUnit
import okhttp3.Callback

open class MasterService {
    protected val client = OkHttpClient()
    protected val baseUrl = "https://appostado-jd-version.azurewebsites.net"

    fun httpGetRequestAsync(endpoint: String, callback: (Response?) -> Unit) {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .header("Authorization", "Bearer")
            .build()

        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }
        })
    }

    fun httpGetBodyRequestAsync(endpoint: String, requestBody: RequestBody, callback: (Response?) -> Unit) {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .post(requestBody)
            .header("Authorization", "Bearer")
            .build()

        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }
        })
    }

    fun httpPostRequestAsync(endpoint: String, requestBody: RequestBody, callback: (Response?) -> Unit) {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .post(requestBody)
            .header("Authorization", "Bearer")
            .build()

        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }
        })
    }

    fun httpPutRequestAsync(endpoint: String, requestBody: RequestBody, callback: (Response?) -> Unit) {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .put(requestBody)
            .header("Authorization", "Bearer")
            .build()

        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }
        })
    }

    fun httpDeleteRequestAsync(endpoint: String, callback: (Response?) -> Unit) {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .delete()
            .header("Authorization", "Bearer")
            .build()

        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }
        })
    }
/*
    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            if (username == "Kevin" && password == "12345678") {
                val user = LoggedInUser(UUID.randomUUID().toString(), username, 1)
                Result.Success(user)
            } else if(username == "Meli" && password == "12345678"){
                val user = LoggedInUser(UUID.randomUUID().toString(), username, 2)
                Result.Success(user)
            } else {
                Result.Error(IOException("Credenciales incorrectas"))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error durante el inicio de sesión", e))
        }
    }*/
}
