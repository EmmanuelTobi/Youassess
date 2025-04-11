package com.cosmic.youassessment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cosmic.youassessment.adapter.CartAdapter
import com.cosmic.youassessment.databinding.FragmentCartBinding
import com.cosmic.youassessment.di.ViewModelFactoryProvider
import com.cosmic.youassessment.viewmodel.CartViewModel
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CartViewModel by viewModels {
        ViewModelFactoryProvider.cartViewModelFactory()
    }
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onUpdateQuantity = { productId, quantity ->
                viewModel.updateQuantity(productId, quantity)
            },
            onRemoveItem = { productId ->
                viewModel.removeFromCart(productId)
            }
        )
        binding.cartRecyclerView.adapter = cartAdapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is CartViewModel.CartUiState.Loading -> {
                            binding.cartRecyclerView.isVisible = false
                            binding.emptyCartText.isVisible = false
                            binding.checkoutContainer.isVisible = false
                        }
                        is CartViewModel.CartUiState.Success -> {
                            binding.cartRecyclerView.isVisible = true
                            binding.emptyCartText.isVisible = false
                            binding.checkoutContainer.isVisible = true
                            cartAdapter.submitList(state.items)
                            binding.totalPrice.text = String.format("$%.2f", state.total)
                        }
                        is CartViewModel.CartUiState.Empty -> {
                            binding.cartRecyclerView.isVisible = false
                            binding.emptyCartText.isVisible = true
                            binding.checkoutContainer.isVisible = false
                        }
                        is CartViewModel.CartUiState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
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