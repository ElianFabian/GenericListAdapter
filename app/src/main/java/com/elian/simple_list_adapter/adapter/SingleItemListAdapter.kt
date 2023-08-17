@file:Suppress("NOTHING_TO_INLINE")

package com.elian.simple_list_adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class SingleItemListAdapter<VB : ViewBinding, ItemT : Any>(
	protected inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	diffCallback: DiffUtil.ItemCallback<ItemT>,
) : ListAdapter<ItemT, SingleItemListAdapter<VB, ItemT>.SingleItemViewHolder>(diffCallback) {

	protected abstract fun onBindItem(binding: VB, item: ItemT, position: Int)

	protected inline fun getItemOrNull(position: Int): ItemT? = currentList.getOrNull(position)

	protected open fun onCreateViewHolder(binding: VB): SingleItemViewHolder {
		return SingleItemViewHolder(binding)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleItemViewHolder {
		val inflater = LayoutInflater.from(parent.context)

		val binding = inflate(inflater, parent, false)

		return onCreateViewHolder(binding)
	}

	override fun onBindViewHolder(holder: SingleItemViewHolder, position: Int) {
		onBindItem(holder.binding, getItem(position), position)
	}


	open inner class SingleItemViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}