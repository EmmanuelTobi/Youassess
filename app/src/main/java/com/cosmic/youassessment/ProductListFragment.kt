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
import com.cosmic.youassessment.adapter.ProductAdapter
import com.cosmic.youassessment.databinding.FragmentProductListBinding
import com.cosmic.youassessment.di.ViewModelFactoryProvider
import com.cosmic.youassessment.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProductViewModel by viewModels {
        ViewModelFactoryProvider.productViewModelFactory()
    }
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            viewModel.addToCart(product)
            Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
        }
        binding.productRecyclerView.adapter = productAdapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ProductViewModel.ProductUiState.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is ProductViewModel.ProductUiState.Success -> {
                            binding.progressBar.isVisible = false
                            productAdapter.submitList(state.products)
                        }
                        is ProductViewModel.ProductUiState.Empty -> {
                            binding.progressBar.isVisible = false
                            // Handle empty state
                        }
                        is ProductViewModel.ProductUiState.Error -> {
                            binding.progressBar.isVisible = false
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