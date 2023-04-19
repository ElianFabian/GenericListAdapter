package com.elian.genericadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericSingleItemAdapter<ItemT : Any, VB : ViewBinding>(
	private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	private inline val onBind: GenericSingleItemAdapter<ItemT, VB>.(
		item: ItemT,
		binding: VB,
		position: Int,
	) -> Unit,
) : ListAdapter<ItemT, GenericSingleItemAdapter<ItemT, VB>.ViewHolder>(
	object : DiffUtil.ItemCallback<ItemT>()
	{
		override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT) = areItemsTheSame(oldItem, newItem)

		override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT) = areContentsTheSame(oldItem, newItem)
	}
)
{
	inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)


	@Suppress("Unused")
	fun GenericSingleItemAdapter<ItemT, VB>.getItem(position: Int): ItemT = getItem(position)

	@Suppress("Unused")
	inline fun <T> RecyclerView.setAdapterOrSubmitList(list: List<T>, getAdapter: () -> ListAdapter<T, *>)
	{
		val listAdapter = (adapter as? ListAdapter<T, RecyclerView.ViewHolder>) ?: getAdapter()

		if (adapter == null) adapter = listAdapter

		listAdapter.submitList(list)
	}


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)

		val binding = inflate(inflater, parent, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		onBind(getItem(position), holder.binding, position)
	}
}

@Suppress("FunctionName")
fun <ItemT : Any, VB : ViewBinding> GenericAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	onBind: GenericSingleItemAdapter<ItemT, VB>.(
		item: ItemT,
		binding: VB,
		position: Int,
	) -> Unit,
): ListAdapter<ItemT, out RecyclerView.ViewHolder> = GenericSingleItemAdapter(
	inflate = inflate,
	areItemsTheSame = areItemsTheSame,
	areContentsTheSame = areContentsTheSame,
) { item, binding, position ->

	onBind(item, binding, position)
}

@Suppress("FunctionName")
fun <ItemT : Any, VB : ViewBinding> GenericAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	onBind: GenericSingleItemAdapter<ItemT, VB>.(
		item: ItemT,
		binding: VB,
	) -> Unit,
): ListAdapter<ItemT, out RecyclerView.ViewHolder> = GenericSingleItemAdapter(
	inflate = inflate,
	areItemsTheSame = areItemsTheSame,
	areContentsTheSame = areContentsTheSame,
) { item, binding, _ ->

	onBind(item, binding)
}

@Suppress("FunctionName")
fun <
	A : Any,
	B : Any,
	ItemT : Pair<A, B>,
	VB : ViewBinding,
	> GenericAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	onBind: GenericSingleItemAdapter<ItemT, VB>.(
		A, B,
		binding: VB,
		position: Int,
	) -> Unit,
): ListAdapter<ItemT, out RecyclerView.ViewHolder> = GenericSingleItemAdapter(
	inflate = inflate,
	areItemsTheSame = areItemsTheSame,
	areContentsTheSame = areContentsTheSame,
) { item, binding, position ->

	onBind(item.first, item.second, binding, position)
}