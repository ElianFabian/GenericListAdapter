@file:Suppress("NOTHING_TO_INLINE")

package com.elian.simple_list_adapter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class MultiItemListAdapter<ItemT : Any>(
	diffCallback: DiffUtil.ItemCallback<ItemT>
) : ListAdapter<ItemT, MultiItemListAdapter<ItemT>.MultiItemViewHolder>(diffCallback) {

	abstract val bindingDataList: List<BindingData<ItemT>>

	protected val itemClassToViewType by lazy {
		bindingDataList.mapIndexed { index, data -> data.itemClass to index }.toMap()
	}

	protected inline fun getItemOrNull(position: Int): ItemT? = currentList.getOrNull(position)


	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiItemViewHolder {

		val inflater = LayoutInflater.from(parent.context)

		val bindingData = bindingDataList[viewType]

		val binding = bindingData.inflate(inflater, parent, false)

		return onCreateViewHolder(binding, bindingData)
	}

	protected open fun onCreateViewHolder(binding: ViewBinding, bindingData: BindingData<ItemT>): MultiItemViewHolder {
		return MultiItemViewHolder(binding, bindingData)
	}

	override fun onBindViewHolder(holder: MultiItemViewHolder, position: Int) {
		val item = getItem(position)

		holder.bindingData.onBind(holder.binding, item, position, holder)
	}

	override fun getItemViewType(position: Int): Int {
		val item = getItem(position)

		return itemClassToViewType[item::class]!!
	}


	open inner class MultiItemViewHolder(
		val binding: ViewBinding,
		val bindingData: BindingData<ItemT>,
	) : ViewHolder(binding.root)
}


class BindingData<out ItemT : Any>(
	inline val itemClass: KClass<@UnsafeVariance ItemT>,
	inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	inline val onBind: (
		binding: ViewBinding,
		item: @UnsafeVariance ItemT,
		position: Int,
		holder: ViewHolder,
	) -> Unit,
)

@Suppress("FunctionName", "UNCHECKED_CAST")
inline fun <VB : ViewBinding, reified ItemT : Any> Binding(
	noinline inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	noinline onBind: (
		binding: VB,
		item: ItemT,
		position: Int,
		holder: ViewHolder,
	) -> Unit,
): BindingData<ItemT> {

	return BindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		onBind = onBind as (
			binding: ViewBinding,
			item: @UnsafeVariance ItemT,
			position: Int,
			holder: ViewHolder,
		) -> Unit,
	)
}
