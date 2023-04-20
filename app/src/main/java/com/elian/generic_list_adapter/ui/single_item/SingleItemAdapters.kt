package com.elian.generic_list_adapter.ui.single_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elian.generic_list_adapter.adapter.GenericListAdapter
import com.elian.generic_list_adapter.databinding.ItemOperationBinding
import com.elian.generic_list_adapter.model.OperationInfo

//region New way

@Suppress("FunctionName")
fun OperationAdapter_New(items: List<OperationInfo>) = GenericListAdapter(
	inflate = ItemOperationBinding::inflate,
	areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
) { operation: OperationInfo, binding ->

	binding.apply()
	{
		tvFirstNumber.text = "${operation.firstNumber}"
		tvSecondNumber.text = "${operation.secondNumber}"
		tvOperationSymbol.text = operation.operationSymbol
		tvExpectedResult.text = "${operation.result}"
	}

}.apply { submitList(items) }

//endregion

//region Old way

class OperationAdapter_Old(items: List<OperationInfo>) : ListAdapter<OperationInfo, OperationAdapter_Old.ViewHolder>(
	object : DiffUtil.ItemCallback<OperationInfo>()
	{
		override fun areItemsTheSame(oldItem: OperationInfo, newItem: OperationInfo) = oldItem.uuid == newItem.uuid

		override fun areContentsTheSame(oldItem: OperationInfo, newItem: OperationInfo) = oldItem == newItem
	}
)
{
	init
	{
		submitList(items)
	}

	class ViewHolder(val binding: ItemOperationBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val binding = ItemOperationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		val operation = getItem(position)

		holder.binding.apply()
		{
			tvFirstNumber.text = "${operation.firstNumber}"
			tvSecondNumber.text = "${operation.secondNumber}"
			tvOperationSymbol.text = operation.operationSymbol
			tvExpectedResult.text = "${operation.result}"
		}
	}
}

//endregion