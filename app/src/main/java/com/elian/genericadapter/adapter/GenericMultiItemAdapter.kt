package com.elian.genericadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class GenericMultiItemAdapter<ItemT : Any>(
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	itemBindings: List<BindingData<ItemT, *>>,
) : ListAdapter<ItemT, GenericMultiItemAdapter<ItemT>.ViewHolder>(
	object : DiffUtil.ItemCallback<ItemT>()
	{
		override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT) = areItemsTheSame(oldItem, newItem)

		override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT) = areContentsTheSame(oldItem, newItem)
	}
)
{
	inner class ViewHolder(
		val binding: ViewBinding,
		val bindingData: BindingData<ItemT, *>,
	) : RecyclerView.ViewHolder(binding.root)


	private val bindings = itemBindings.distinct()
	private val itemClassToViewType = itemBindings.mapIndexed { index, data -> data.itemClass to index }.toMap()


	fun GenericMultiItemAdapter<ItemT>.getItem(position: Int): ItemT = getItem(position)
	fun GenericMultiItemAdapter<ItemT>.getItemOrNull(position: Int): ItemT? = kotlin.runCatching { getItem(position) }.getOrNull()


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
fun <ItemT : Any> GenericAdapter(
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	itemBindings: List<BindingData<ItemT, *>>,
): ListAdapter<ItemT, out RecyclerView.ViewHolder>
{
	return GenericMultiItemAdapter(
		areItemsTheSame = areItemsTheSame,
		areContentsTheSame = areContentsTheSame,
		itemBindings = itemBindings,
	)
}

data class BindingData<out ItemT : Any, VB : ViewBinding>(
	val itemClass: KClass<@UnsafeVariance ItemT>,
	val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	val onBind: GenericMultiItemAdapter<@UnsafeVariance ItemT>.(
		item: @UnsafeVariance ItemT,
		binding: ViewBinding,
		position: Int,
	) -> Unit,
)

@Suppress("FunctionName")
inline fun <reified ItemT : Any, VB : ViewBinding> Binding(
	noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	noinline onBind: GenericMultiItemAdapter<@UnsafeVariance ItemT>.(
		item: ItemT,
		binding: VB,
	) -> Unit,
): BindingData<ItemT, VB>
{
	return Binding(
		inflate = inflate,
		onBind = { item, binding, _ ->
			onBind(item, binding)
		},
	)
}

@Suppress("FunctionName", "UNCHECKED_CAST")
inline fun <reified ItemT : Any, VB : ViewBinding> Binding(
	noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	noinline onBind: GenericMultiItemAdapter<ItemT>.(
		item: ItemT,
		binding: VB,
		position: Int,
	) -> Unit,
): BindingData<ItemT, VB>
{
	return BindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		onBind = onBind as GenericMultiItemAdapter<@UnsafeVariance ItemT>.(
			item: @UnsafeVariance ItemT,
			binding: ViewBinding,
			position: Int,
		) -> Unit,
	)
}