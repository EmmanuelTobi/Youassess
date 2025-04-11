package com.cosmic.youassessment

import android.app.Application
import com.cosmic.youassessment.repository.AuthRepository
import com.cosmic.youassessment.repository.AuthRepositoryImpl
import com.cosmic.youassessment.repository.CartRepository
import com.cosmic.youassessment.repository.CartRepositoryImpl
import com.cosmic.youassessment.repository.ProductRepository
import com.cosmic.youassessment.repository.ProductRepositoryImpl
import com.cosmic.youassessment.network.NetworkModule
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class ECommerceApp : Application() {

    lateinit var authRepository: AuthRepository
        private set

    lateinit var productRepository: ProductRepository
        private set

    lateinit var cartRepository: CartRepository
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        FirebaseApp.initializeApp(this)

        authRepository = AuthRepositoryImpl(FirebaseAuth.getInstance())
        productRepository = ProductRepositoryImpl(NetworkModule.productService)
        cartRepository = CartRepositoryImpl()
    }

    companion object {
        lateinit var instance: ECommerceApp
            private set
    }
}