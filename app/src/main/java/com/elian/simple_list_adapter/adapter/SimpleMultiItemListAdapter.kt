package com.elian.simple_list_adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class SimpleMultiItemListAdapter<ItemT : Any>(
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	itemBindings: List<BindingData<ItemT>>,
) : ListAdapter<ItemT, SimpleMultiItemListAdapter<ItemT>.ViewHolder>(
	object : DiffUtil.ItemCallback<ItemT>()
	{
		override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT) = areItemsTheSame(oldItem, newItem)

		override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT) = areContentsTheSame(oldItem, newItem)
	}
)
{
	inner class ViewHolder(
		val binding: ViewBinding,
		val bindingData: BindingData<ItemT>,
	) : RecyclerView.ViewHolder(binding.root)


	private val bindings = itemBindings.distinct()
	private val itemClassToViewType = itemBindings.mapIndexed { index, data -> data.itemClass to index }.toMap()


	fun SimpleMultiItemListAdapter<ItemT>.getItem(position: Int): ItemT = getItem(position)
	fun SimpleMultiItemListAdapter<ItemT>.getItemOrNull(position: Int): ItemT? = kotlin.runCatching { getItem(position) }.getOrNull()


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)

		val itemBindingData = bindings[viewType]

		val binding = itemBindingData.inflate(inflater, parent, false)

		return ViewHolder(binding, itemBindingData)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		holder.bindingData.onBind(this, getItem(position), holder.binding, position)
	}

	override fun getItemViewType(position: Int): Int
	{
		val item = getItem(position)

		return itemClassToViewType[item::class]!!
	}
}



@Suppress("FunctionName")
fun <ItemT : Any> SimpleListAdapter(
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	itemBindings: List<BindingData<ItemT>>,
): ListAdapter<ItemT, out RecyclerView.ViewHolder>
{
	return SimpleMultiItemListAdapter(
		areItemsTheSame = areItemsTheSame,
		areContentsTheSame = areContentsTheSame,
		itemBindings = itemBindings,
	)
}

class BindingData<out ItemT : Any>(
	inline val itemClass: KClass<@UnsafeVariance ItemT>,
	inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	inline val onBind: SimpleMultiItemListAdapter<@UnsafeVariance ItemT>.(
		item: @UnsafeVariance ItemT,
		binding: ViewBinding,
		position: Int,
	) -> Unit,
)

@Suppress("FunctionName", "UNCHECKED_CAST")
inline fun <reified ItemT : Any, VB : ViewBinding> Binding(
	noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	noinline onBind: SimpleMultiItemListAdapter<ItemT>.(
		item: ItemT,
		binding: VB,
		position: Int,
	) -> Unit,
): BindingData<ItemT>
{
	return BindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		onBind = onBind as SimpleMultiItemListAdapter<@UnsafeVariance ItemT>.(
			item: @UnsafeVariance ItemT,
			binding: ViewBinding,
			position: Int,
		) -> Unit,
	)
}