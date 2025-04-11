package com.cosmic.youassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cosmic.youassessment.ECommerceApp
import com.cosmic.youassessment.repository.AuthRepository
import com.cosmic.youassessment.repository.ProductRepository
import com.cosmic.youassessment.repository.CartRepository

class ProductViewModelFactory : ViewModelProvider.Factory {
    private val productRepository: ProductRepository = ECommerceApp.instance.productRepository
    private val cartRepository: CartRepository = ECommerceApp.instance.cartRepository

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(productRepository, cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

class CartViewModelFactory : ViewModelProvider.Factory {
    private val cartRepository: CartRepository = ECommerceApp.instance.cartRepository

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

class AuthViewModelFactory : ViewModelProvider.Factory {
    private val authRepository: AuthRepository = ECommerceApp.instance.authRepository

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}