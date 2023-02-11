package com.elian.genericadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

open class GenericMultiItemAdapter<ItemT : Any>(
	private val itemBindings: List<ItemBindingData<ItemT>>,
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
) : ListAdapter<ItemT, GenericMultiItemAdapter<ItemT>.ViewHolder>(
	object : DiffUtil.ItemCallback<ItemT>()
	{
		override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT) = areItemsTheSame(oldItem, newItem)

		@SuppressLint("DiffUtilEquals")
		override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT) = areContentsTheSame(oldItem, newItem)
	}
)
{
	inner class ViewHolder(val binding: ViewBinding, val bindingData: ItemBindingData<ItemT>) : RecyclerView.ViewHolder(binding.root)


	private val itemClasses = itemBindings.map { it.itemClass }.toSet()


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val inflater = LayoutInflater.from(parent.context)

		val itemBindingData = itemBindings[viewType]

		val binding = itemBindingData.inflate(inflater, parent, false)

		return ViewHolder(binding, itemBindingData)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		val item = getItem(position)

		holder.bindingData.bindBlock(holder.binding, item, holder)
	}

	override fun getItemViewType(position: Int): Int
	{
		val item = getItem(position)

		return itemClasses.indexOf(item::class)
	}
}


data class ItemBindingData<out ItemT : Any>(
	val itemClass: KClass<@UnsafeVariance ItemT>,
	val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	val bindBlock: ViewBinding.(item: @UnsafeVariance ItemT, holder: GenericMultiItemAdapter<@UnsafeVariance ItemT>.ViewHolder) -> Unit,
)

@Suppress("FunctionName", "UNCHECKED_CAST")
inline fun <reified ItemT : Any, VB : ViewBinding> ItemBinding(
	noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	noinline bindBlock: VB.(item: ItemT, holder: GenericMultiItemAdapter<ItemT>.ViewHolder) -> Unit,
): ItemBindingData<ItemT>
{
	return ItemBindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		bindBlock = bindBlock as ViewBinding.(item: ItemT, holder: GenericMultiItemAdapter<ItemT>.ViewHolder) -> Unit,
	)
}