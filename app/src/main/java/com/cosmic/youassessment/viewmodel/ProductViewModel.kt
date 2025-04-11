package com.cosmic.youassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmic.youassessment.model.Product
import com.cosmic.youassessment.repository.CartRepository
import com.cosmic.youassessment.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                productRepository.refreshProducts()
                productRepository.getProductsStream().collect { products ->
                    _uiState.value = if (products.isEmpty()) {
                        ProductUiState.Empty
                    } else {
                        ProductUiState.Success(products)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ProductUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                cartRepository.addToCart(product)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    sealed class ProductUiState {
        object Loading : ProductUiState()
        object Empty : ProductUiState()
        data class Success(val products: List<Product>) : ProductUiState()
        data class Error(val message: String) : ProductUiState()
    }
}