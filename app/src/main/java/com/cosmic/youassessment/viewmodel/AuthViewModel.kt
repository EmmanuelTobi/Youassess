package com.cosmic.youassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmic.youassessment.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val userId = authRepository.login(email, password)

                userId.fold(
                    onSuccess = { user ->
                        if (user.uid.isNotEmpty()) {
                            _uiState.value = AuthUiState.Authenticated(user.uid)
                        }
                    },
                    onFailure = { e ->
                        val errorMessage = when {
                            e.message?.contains("The supplied auth credential is incorrect") == true ->
                                "Invalid email or password."
                            e.message?.contains("malformed or has expired") == true ->
                                "Authentication token is invalid or has expired. Please try again."
                            else -> e.message ?: "Login failed"
                        }
                        _uiState.value = AuthUiState.Error(errorMessage)
                    }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun signUp(email: String, password: String) {
        _uiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val userId = authRepository.signUp(email, password)

                userId.fold(
                    onSuccess = { user ->
                        if (user.uid.isNotEmpty()) {
                            _uiState.value = AuthUiState.Authenticated(user.uid)
                        }
                    },
                    onFailure = { e ->
                        val errorMessage = when {
                            e.message?.contains("email address is already in use") == true ->
                                "The email address is already in use by another account."
                            e.message?.contains("The supplied auth credential is incorrect") == true ->
                                "Invalid email or password."
                            else -> e.message ?: "Sign up failed"
                        }
                        _uiState.value = AuthUiState.Error(errorMessage)
                    }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    sealed class AuthUiState {
        data object Initial : AuthUiState()
        data object Loading : AuthUiState()
        data class Authenticated(val userId: String) : AuthUiState()
        data class Error(val message: String) : AuthUiState()
    }
}