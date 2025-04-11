package com.cosmic.youassessment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cosmic.youassessment.databinding.ItemCartBinding
import com.cosmic.youassessment.model.CartItem

class CartAdapter(
    private val onUpdateQuantity: (String, Int) -> Unit,
    private val onRemoveItem: (String) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            val product = cartItem.product
            binding.apply {
                productName.text = product.title
                productPrice.text = String.format("$%.2f", cartItem.totalPrice)
                productImage.load(product.image) {
                    crossfade(true)
                }
                quantityText.text = cartItem.quantity.toString()

                btnPlus.setOnClickListener {
                    val newQuantity = cartItem.quantity + 1
                    quantityText.text = newQuantity.toString()
                    productPrice.text = String.format("$%.2f", cartItem.totalPrice * newQuantity)
                    onUpdateQuantity(product.id.toString(), newQuantity)
                }

                btnMinus.setOnClickListener {
                    if (cartItem.quantity >= 1) {
                        val newQuantity = cartItem.quantity - 1
                        quantityText.text = newQuantity.toString()
                        onUpdateQuantity(product.id.toString(), newQuantity)
                    }
                    productPrice.text = String.format("$%.2f", cartItem.totalPrice * cartItem.quantity)
                }

                btnRemove.setOnClickListener {
                    onRemoveItem(product.id.toString())
                }
            }
        }
    }
}

class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}