package com.elian.simple_list_adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("FunctionName")
inline fun <T : Any> DiffCallback(
	crossinline areContentsTheSame: (oldItem: T, newItem: T) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	crossinline areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
) = object : DiffUtil.ItemCallback<T>() {
	override fun areItemsTheSame(oldItem: T, newItem: T) = areItemsTheSame(oldItem, newItem)
	override fun areContentsTheSame(oldItem: T, newItem: T) = areContentsTheSame(oldItem, newItem)
}

inline fun <T> RecyclerView.setAdapterOrSubmitList(list: List<T>, getAdapter: () -> ListAdapter<T, *>) {

	val listAdapter = (adapter as? ListAdapter<T, RecyclerView.ViewHolder>) ?: getAdapter()

	if (adapter == null) adapter = listAdapter

	listAdapter.submitList(list)
}