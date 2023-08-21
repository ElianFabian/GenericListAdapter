@file:Suppress("NOTHING_TO_INLINE")

package com.elian.simple_list_adapter.adapters

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

	protected abstract fun onBind(binding: VB, position: Int, holder: RecyclerView.ViewHolder)

	@SuppressWarnings("RedundantOverride")
	override fun getItem(position: Int): ItemT = super.getItem(position)

	protected inline fun getItemOrNull(position: Int): ItemT? = currentList.getOrNull(position)

	protected fun onCreateViewHolder(binding: VB): SingleItemViewHolder {
		return SingleItemViewHolder(binding)
	}

	final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleItemViewHolder {
		val inflater = LayoutInflater.from(parent.context)

		val binding = inflate(inflater, parent, false)

		return onCreateViewHolder(binding)
	}

	final override fun onBindViewHolder(holder: SingleItemViewHolder, position: Int) {
		onBind(holder.binding, position, holder)
	}


	inner class SingleItemViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}