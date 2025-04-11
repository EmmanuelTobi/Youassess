package com.cosmic.youassessment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import android.graphics.Color
import android.widget.Toast
import com.cosmic.youassessment.databinding.FragmentAuthBinding
import com.cosmic.youassessment.di.ViewModelFactoryProvider
import com.cosmic.youassessment.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        ViewModelFactoryProvider.authViewModelFactory()
    }

    private var isLogin = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeUiState()
    }

    private fun setupViews() {
        binding.toggleAuthModeButton.setOnClickListener {
            isLogin = !isLogin
            updateAuthMode()
        }

        binding.authButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (isLogin) {
                viewModel.login(email, password)
            } else {
                viewModel.signUp(email, password)
            }
        }
    }

    private fun updateAuthMode() {
        binding.titleText.text = if (isLogin) "Login" else "Sign Up"
        binding.authButton.text = if (isLogin) "Login" else "Sign Up"
        binding.toggleAuthModeButton.text = if (isLogin) "Need an account? Sign Up" else "Already have an account? Login"
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is AuthViewModel.AuthUiState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.authButton.isEnabled = false
                            binding.errorText.isVisible = false
                        }
                        is AuthViewModel.AuthUiState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.authButton.isEnabled = true
                            binding.errorText.isVisible = true
                            binding.errorText.text = state.message
                        }
                        is AuthViewModel.AuthUiState.Authenticated -> {
                            binding.progressBar.isVisible = false
                            binding.authButton.isEnabled = true
                            binding.errorText.apply {
                                isVisible = true
                                setTextColor(Color.parseColor("#4CAF50"))
                                text = if (!isLogin) {
                                    "Successfully signed up! Welcome to YouAssessment!"
                                } else {
                                    "Welcome back!"
                                }
                            }
                            val message = if (!isLogin) "Account created successfully!" else "Successfully logged in!"
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            binding.progressBar.isVisible = false
                            binding.authButton.isEnabled = true
                            binding.errorText.isVisible = false
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}