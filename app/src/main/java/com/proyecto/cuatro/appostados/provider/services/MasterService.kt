package com.proyecto.cuatro.appostados.provider.services

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.RequestBody

open class MasterService {
    protected val client = OkHttpClient()
    protected val baseUrl = ""

    protected fun httpRequest(endpoint: String): Response? {
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

    protected fun httpPostRequest(endpoint: String, requestBody: RequestBody): Response? {
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
    protected fun httpPutRequest(endpoint: String, requestBody: RequestBody): Response? {
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
    protected fun httpDeleteRequest(endpoint: String): Response? {
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
}
