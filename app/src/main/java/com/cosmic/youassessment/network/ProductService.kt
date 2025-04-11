package com.cosmic.youassessment.network

import com.cosmic.youassessment.model.Product
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(id: Int): Product
}