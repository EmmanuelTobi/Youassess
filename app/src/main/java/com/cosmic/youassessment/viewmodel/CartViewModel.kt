package com.cosmic.youassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmic.youassessment.model.CartItem
import com.cosmic.youassessment.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                cartRepository.getCartItems(),
                cartRepository.getCartTotal()
            ) { items, total ->
                if (items.isEmpty()) {
                    CartUiState.Empty
                } else {
                    CartUiState.Success(items, total)
                }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateQuantity(productId, quantity)
            } catch (e: Exception) {
                _uiState.value = CartUiState.Error(e.message ?: "Failed to update quantity")
            }
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            try {
                cartRepository.removeFromCart(productId)
            } catch (e: Exception) {
                _uiState.value = CartUiState.Error(e.message ?: "Failed to remove item")
            }
        }
    }

    sealed class CartUiState {
        object Loading : CartUiState()
        object Empty : CartUiState()
        data class Success(
            val items: List<CartItem>,
            val total: Double
        ) : CartUiState()
        data class Error(val message: String) : CartUiState()
    }
}