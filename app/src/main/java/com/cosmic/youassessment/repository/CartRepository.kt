package com.cosmic.youassessment.repository

import com.cosmic.youassessment.model.CartItem
import com.cosmic.youassessment.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>
    fun getCartTotal(): Flow<Double>
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(productId: String)
    suspend fun updateQuantity(productId: String, quantity: Int)
    suspend fun clearCart()
}

class CartRepositoryImpl : CartRepository {
    private val cartItems = MutableStateFlow<List<CartItem>>(emptyList())

    override fun getCartItems(): Flow<List<CartItem>> = cartItems

    override fun getCartTotal(): Flow<Double> = cartItems.map { items ->
        items.sumOf { it.totalPrice }
    }

    override suspend fun addToCart(product: Product) {
        val currentItems = cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            currentItems.add(CartItem(product))
        }

        cartItems.emit(currentItems)
    }

    override suspend fun removeFromCart(productId: String) {
        val currentItems = cartItems.value.toMutableList()
        currentItems.removeAll { it.product.id.toString() == productId }
        cartItems.emit(currentItems)
    }

    override suspend fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }

        val currentItems = cartItems.value.toMutableList()
        val item = currentItems.find { it.product.id.toString() == productId }
        item?.let {
            it.quantity = quantity
            cartItems.emit(currentItems)
        }
    }

    override suspend fun clearCart() {
        cartItems.emit(emptyList())
    }
}