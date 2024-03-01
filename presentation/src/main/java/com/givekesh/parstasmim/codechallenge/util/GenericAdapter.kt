package com.givekesh.parstasmim.codechallenge.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class GenericAdapter<ItemType>(
    @LayoutRes private val layoutResId: Int,
    private val bindingVariableId: Int,
    diffCallBack: DiffUtil.ItemCallback<ItemType>,
    private val itemClickListener: ((ItemType) -> Unit)? = null
) : RecyclerView.Adapter<GenericViewHolder<ItemType>>() {
    private val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<ItemType> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutResId, parent, false)
        return GenericViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<ItemType>, position: Int) {
        val item = getItem(position)
        holder.bind(item, bindingVariableId)
    }

    override fun getItemCount(): Int = differ.currentList.size

    private fun getItem(position: Int): ItemType = differ.currentList[position]

    fun updateItems(items: List<ItemType>) = differ.submitList(items)
}

class GenericViewHolder<ItemType>(
    private val binding: ViewDataBinding,
    private val itemClickListener: ((ItemType) -> Unit)?
) : ViewHolder(binding.root) {
    fun bind(item: ItemType, bindingVariableId: Int) {
        binding.apply {
            setVariable(bindingVariableId, item)
            root.setOnClickListener { itemClickListener?.invoke(item) }
        }
    }
}