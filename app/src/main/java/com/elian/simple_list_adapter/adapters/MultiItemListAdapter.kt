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

	abstract val bindings: List<BindingData<ItemT>>

	protected val itemClassToViewType by lazy {
		bindings.mapIndexed { index, data -> data.itemClass to index }.toMap()
	}

	protected inline fun <T : ItemT> MultiItemListAdapter<T>.getItem(position: Int): T = currentList[position]

	protected inline fun <T : ItemT> MultiItemListAdapter<T>.getItemOrNull(position: Int): T? = currentList.getOrNull(position)


	final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiItemViewHolder {

		val inflater = LayoutInflater.from(parent.context)

		val bindingData = bindings[viewType]

		val binding = bindingData.inflate(inflater, parent, false)

		return onCreateViewHolder(binding, bindingData)
	}

	private fun onCreateViewHolder(binding: ViewBinding, bindingData: BindingData<ItemT>): MultiItemViewHolder {
		return MultiItemViewHolder(binding, bindingData)
	}

	final override fun onBindViewHolder(holder: MultiItemViewHolder, position: Int) {
		holder.bindingData.onBind(this, holder.binding, position, holder)
	}

	override fun getItemViewType(position: Int): Int {
		val item = getItem(position)

		val itemClass = item::class

		return itemClassToViewType[itemClass] ?: throw MissingBindingException(
			"No binding found for ${itemClass.qualifiedName}. Make sure to add a binding to all the item types."
		)
	}


	inner class MultiItemViewHolder(
		val binding: ViewBinding,
		val bindingData: BindingData<ItemT>,
	) : ViewHolder(binding.root)
}


class MissingBindingException(message: String) : RuntimeException(message)


class BindingData<out ItemT : Any>(
	inline val itemClass: KClass<@UnsafeVariance ItemT>,
	inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
	inline val onBind: MultiItemListAdapter<@UnsafeVariance ItemT>.(
		binding: ViewBinding,
		position: Int,
		holder: ViewHolder,
	) -> Unit,
)

inline fun <reified VB : ViewBinding, reified ItemT : Any> bindingOf(
	crossinline onBind: MultiItemListAdapter<ItemT>.(
		binding: VB,
		position: Int,
		holder: ViewHolder
	) -> Unit
): BindingData<ItemT> {

	val inflateMethod = VB::class.java
		.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)

	val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB = { inflater, parent, attachToParent ->
		inflateMethod(null, inflater, parent, attachToParent) as VB
	}

	return BindingData(
		itemClass = ItemT::class,
		inflate = inflate,
		onBind = { binding, position, holder ->
			onBind(binding as VB, position, holder)
		}
	)
}