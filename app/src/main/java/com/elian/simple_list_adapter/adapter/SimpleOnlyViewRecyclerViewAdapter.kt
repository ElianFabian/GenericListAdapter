package com.elian.simple_list_adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

private class SimpleOnlyViewRecyclerViewAdapter<VB : ViewBinding>(
	private inline val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	private val count: Int = 1,
	private inline val onBind: RecyclerView.Adapter<out RecyclerView.ViewHolder>.(
		binding: VB,
		position: Int,
	) -> Unit
) : RecyclerView.Adapter<SimpleOnlyViewRecyclerViewAdapter<VB>.ViewHolder>()
{
	inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
		binding = inflate(LayoutInflater.from(parent.context), parent, false)
	)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = onBind(holder.binding, position)

	override fun getItemCount() = count
}

@Suppress("FunctionName")
fun <VB : ViewBinding> SimpleRecyclerViewAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	itemCount: Int = 1,
) = SimpleRecyclerViewAdapter(
	inflate = inflate,
	itemCount = itemCount,
	onBind = { _: VB, _: Int -> },
)

@Suppress("FunctionName")
fun <VB : ViewBinding> SimpleRecyclerViewAdapter(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	itemCount: Int = 1,
	onBind: RecyclerView.Adapter<out RecyclerView.ViewHolder>.(
		binding: VB,
		position: Int,
	) -> Unit,
): RecyclerView.Adapter<out RecyclerView.ViewHolder> = SimpleOnlyViewRecyclerViewAdapter(
	inflate = inflate,
	count = itemCount,
	onBind = { binding, position ->

		onBind(binding, position)
	}
)