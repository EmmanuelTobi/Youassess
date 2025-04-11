package com.cosmic.youassessment.di

import androidx.lifecycle.ViewModelProvider
import com.cosmic.youassessment.viewmodel.AuthViewModelFactory
import com.cosmic.youassessment.viewmodel.CartViewModelFactory
import com.cosmic.youassessment.viewmodel.ProductViewModelFactory

object ViewModelFactoryProvider {
    fun productViewModelFactory(): ViewModelProvider.Factory {
        return ProductViewModelFactory()
    }

    fun cartViewModelFactory(): ViewModelProvider.Factory {
        return CartViewModelFactory()
    }

    fun authViewModelFactory(): ViewModelProvider.Factory {
        return AuthViewModelFactory()
    }

}