package com.proyecto.cuatro.appostados.data.services

import com.proyecto.cuatro.appostados.data.model.LoggedInUser
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.RequestBody
import java.io.IOException
import java.util.UUID

open class MasterService {
    protected val client = OkHttpClient()
    protected val baseUrl = ""

    fun httpGetRequest(endpoint: String): Response? {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .build()

        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun httpGetBodyRequest(endpoint: String, requestBody: RequestBody): Response? {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .post(requestBody)
            .build()

        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun httpPostRequest(endpoint: String, requestBody: RequestBody): Response? {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .post(requestBody)
            .build()

        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun httpPutRequest(endpoint: String, requestBody: RequestBody): Response? {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .put(requestBody)
            .build()

        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun httpDeleteRequest(endpoint: String): Response? {
        val request = Request.Builder()
            .url(baseUrl + endpoint)
            .delete()
            .build()

        return try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

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
    }
}
