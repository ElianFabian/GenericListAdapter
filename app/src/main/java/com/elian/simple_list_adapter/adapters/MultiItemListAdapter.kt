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

	protected inline fun <T : ItemT> MultiItemListAdapter<T>.getItem(position: Int): T = currentList[position]

	protected inline fun <T : ItemT> MultiItemListAdapter<T>.getItemOrNull(position: Int): T? = currentList.getOrNull(position)


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
		holder.bindingData.onBind(this, holder.binding, position, holder)
	}

	override fun getItemViewType(position: Int): Int {
		val item = getItem(position)

		return itemClassToViewType[item::class]!!
	}


	inner class MultiItemViewHolder(
		val binding: ViewBinding,
		val bindingData: BindingData<ItemT>,
	) : ViewHolder(binding.root)
}


class BindingData<out ItemT : Any>(
	inline val itemClass: KClass<@UnsafeVariance ItemT>,
	inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	inline val onBind: MultiItemListAdapter<@UnsafeVariance ItemT>.(
		binding: ViewBinding,
		position: Int,
		holder: ViewHolder,
	) -> Unit,
)

@Suppress("FunctionName")
inline fun <reified VB : ViewBinding, reified ItemT : Any> Binding(
	crossinline onBind: MultiItemListAdapter<ItemT>.(
		binding: VB,
		position: Int,
		holder: ViewHolder
	) -> Unit
): BindingData<ItemT> {

	val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB = { inflater, parent, attachToParent ->
		VB::class.java
			.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
			.invoke(null, inflater, parent, attachToParent) as VB
	}

	return BindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		onBind = { binding, position, holder ->
			onBind(binding as VB, position, holder)
		}
	)
}