package com.elian.simple_list_adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class SimpleMultiItemListAdapter<ItemT : Any>(
	private val getItemCount: SimpleMultiItemListAdapter<ItemT>.(count: Int) -> Int = { count -> count },
	diffCallback: DiffUtil.ItemCallback<ItemT>,
	itemBindings: List<BindingData<ItemT>>,
) : ListAdapter<ItemT, SimpleMultiItemListAdapter<ItemT>.ViewHolder>(diffCallback) {
	inner class ViewHolder(
		val binding: ViewBinding,
		val bindingData: BindingData<ItemT>,
	) : RecyclerView.ViewHolder(binding.root)


	private val bindings = itemBindings.distinct()
	private val itemClassToViewType = itemBindings.mapIndexed { index, data -> data.itemClass to index }.toMap()


	fun SimpleMultiItemListAdapter<ItemT>.getItem(position: Int): ItemT = getItem(position)
	fun SimpleMultiItemListAdapter<ItemT>.getItemOrNull(position: Int): ItemT? = kotlin.runCatching { getItem(position) }.getOrNull()


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val inflater = LayoutInflater.from(parent.context)

		val itemBindingData = bindings[viewType]

		val binding = itemBindingData.inflate(inflater, parent, false)

		return ViewHolder(binding, itemBindingData)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		holder.bindingData.onBind(this, holder.binding, getItem(position), position)
	}

	override fun getItemViewType(position: Int): Int {

		val item = getItem(position)

		return itemClassToViewType[item::class]!!
	}

	override fun getItemCount() = getItemCount(this, super.getItemCount())
}

@Suppress("FunctionName")
fun <ItemT : Any> SimpleListAdapter(
	itemBindings: List<BindingData<ItemT>>,
	diffCallback: DiffUtil.ItemCallback<ItemT>,
	getItemCount: SimpleMultiItemListAdapter<ItemT>.(count: Int) -> Int = { count -> count },
): ListAdapter<ItemT, out RecyclerView.ViewHolder> {

	return SimpleMultiItemListAdapter(
		diffCallback = diffCallback,
		itemBindings = itemBindings,
		getItemCount = getItemCount,
	)
}

@Suppress("FunctionName")
fun <ItemT : Any> SimpleListAdapter(
	areItemsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	areContentsTheSame: (oldItem: ItemT, newItem: ItemT) -> Boolean = { oldItem, newItem -> oldItem == newItem },
	itemBindings: List<BindingData<ItemT>>,
	getItemCount: SimpleMultiItemListAdapter<ItemT>.(count: Int) -> Int = { count -> count },
): ListAdapter<ItemT, out RecyclerView.ViewHolder> {

	return SimpleMultiItemListAdapter(
		diffCallback = object : DiffUtil.ItemCallback<ItemT>() {
			override fun areItemsTheSame(oldItem: ItemT, newItem: ItemT) = areItemsTheSame(oldItem, newItem)
			override fun areContentsTheSame(oldItem: ItemT, newItem: ItemT) = areContentsTheSame(oldItem, newItem)
		},
		itemBindings = itemBindings,
		getItemCount = getItemCount,
	)
}

class BindingData<out ItemT : Any>(
	inline val itemClass: KClass<@UnsafeVariance ItemT>,
	inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	inline val onBind: SimpleMultiItemListAdapter<@UnsafeVariance ItemT>.(
		binding: ViewBinding,
		item: @UnsafeVariance ItemT,
		position: Int,
	) -> Unit,
)

@Suppress("FunctionName", "UNCHECKED_CAST")
inline fun <VB : ViewBinding, reified ItemT : Any> Binding(
	noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	noinline onBind: SimpleMultiItemListAdapter<ItemT>.(
		binding: VB,
		item: ItemT,
		position: Int,
	) -> Unit,
): BindingData<ItemT> {

	return BindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		onBind = onBind as SimpleMultiItemListAdapter<@UnsafeVariance ItemT>.(
			binding: ViewBinding,
			item: @UnsafeVariance ItemT,
			position: Int,
		) -> Unit,
	)
}