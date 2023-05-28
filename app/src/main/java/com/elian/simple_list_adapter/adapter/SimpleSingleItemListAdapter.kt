package com.elian.simple_list_adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class SimpleSingleItemListAdapter<VB : ViewBinding, ItemT : Any>(
	private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	private inline val onBind: SimpleSingleItemListAdapter<VB, ItemT>.(
		binding: VB,
		item: ItemT,
		position: Int,
	) -> Unit,
) : ListAdapter<ItemT, SimpleSingleItemListAdapter<VB, ItemT>.ViewHolder>(
	object : DiffUtil.ItemCallback<ItemT>() {
		override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT) = areItemsTheSame(oldItem, newItem)

		override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT) = areContentsTheSame(oldItem, newItem)
	}
) {
	inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)


	fun SimpleSingleItemListAdapter<VB, ItemT>.getItem(position: Int): ItemT = getItem(position)
	fun SimpleSingleItemListAdapter<VB, ItemT>.getItemOrNull(position: Int): ItemT? = kotlin.runCatching { getItem(position) }.getOrNull()

	@Suppress("Unused")
	inline fun <T> RecyclerView.setAdapterOrSubmitList(list: List<T>, getAdapter: () -> ListAdapter<T, *>) {

		val listAdapter = (adapter as? ListAdapter<T, RecyclerView.ViewHolder>) ?: getAdapter()

		if (adapter == null) adapter = listAdapter

		listAdapter.submitList(list)
	}


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val inflater = LayoutInflater.from(parent.context)

		val binding = inflate(inflater, parent, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		onBind(holder.binding, getItem(position), position)
	}
}

@Suppress("FunctionName")
fun <ItemT : Any, VB : ViewBinding> SimpleListAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	onBind: SimpleSingleItemListAdapter<VB, ItemT>.(
		binding: VB,
		item: ItemT,
		position: Int,
	) -> Unit,
): ListAdapter<ItemT, out RecyclerView.ViewHolder> = SimpleSingleItemListAdapter(
	inflate = inflate,
	areItemsTheSame = areItemsTheSame,
	areContentsTheSame = areContentsTheSame,
) { binding, item, position ->

	onBind(binding, item, position)
}

@Suppress("FunctionName")
fun <
	A : Any,
	B : Any,
	ItemT : Pair<A, B>,
	VB : ViewBinding,
	> SimpleListAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	onBind: SimpleSingleItemListAdapter<VB, ItemT>.(
		binding: VB,
		A, B,
		position: Int,
	) -> Unit,
): ListAdapter<ItemT, out RecyclerView.ViewHolder> = SimpleSingleItemListAdapter(
	inflate = inflate,
	areItemsTheSame = areItemsTheSame,
	areContentsTheSame = areContentsTheSame,
) { binding, item, position ->

	onBind(binding, item.first, item.second, position)
}