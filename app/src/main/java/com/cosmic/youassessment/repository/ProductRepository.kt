package com.cosmic.youassessment.repository

import com.cosmic.youassessment.model.Product
import com.cosmic.youassessment.network.ProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    fun getProductsStream(): Flow<List<Product>>
    suspend fun getProductById(id: String): Product?
    suspend fun refreshProducts()
}

class ProductRepositoryImpl(private val productService: ProductService) : ProductRepository {
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    override suspend fun getProducts(): List<Product> {
        return try {
            productService.getProducts().also {
                _products.value = it
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getProductsStream(): Flow<List<Product>> = _products.asStateFlow()

    override suspend fun getProductById(id: String): Product? {
        return try {
            productService.getProductById(id.toInt())
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun refreshProducts() {
        try {
            _products.value = productService.getProducts()
        } catch (e: Exception) {
            // Handle error
        }
    }
}