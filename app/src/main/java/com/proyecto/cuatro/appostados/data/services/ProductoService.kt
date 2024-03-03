package com.proyecto.cuatro.appostados.data.services

import com.proyecto.cuatro.appostados.data.model.Product
import org.json.JSONObject
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class ProductService : MasterService() {

    fun getProductById(productId: Int): Product? {
        val endpoint = "/product/$productId"
        val response = httpGetRequest(endpoint)
        if (response != null && response.isSuccessful) {
            val responseBody = response.body?.string()
            val productJson = responseBody?.let { JSONObject(it) }
            val id = productJson?.optInt("id")
            val name = productJson?.optString("name")
            if (id != null && name != null) {
                return Product(id, name)
            }
        }
        return null
    }

    fun createProduct(name: String): Product? {

        val requestBody = "{\"name\": \"$name\"}".toRequestBody("application/json".toMediaTypeOrNull())

        val response = httpPostRequest("/products", requestBody)

        if (response != null && response.isSuccessful) {
            val responseBody = response.body?.string()
            val productJson = responseBody?.let { JSONObject(it) }
            val id = productJson?.optInt("id")
            val productName = productJson?.optString("name")
            if (id != null && productName != null) {
                return Product(id, productName)
            }
        }

        return null
    }

    fun updateProduct(productId: Int, newName: String): Product? {

        val requestBody = "{\"name\": \"$newName\"}".toRequestBody("application/json".toMediaTypeOrNull())

        val response = httpPutRequest("/products/$productId", requestBody)

        if (response != null && response.isSuccessful) {
            val responseBody = response.body?.string()
            val productJson = responseBody?.let { JSONObject(it) }
            val id = productJson?.optInt("id")
            val productName = productJson?.optString("name")
            if (id != null && productName != null) {
                return Product(id, productName)
            }
        }

        return null
    }

}