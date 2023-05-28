package com.elian.simple_list_adapter.ui.single_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elian.simple_list_adapter.adapter.Binding
import com.elian.simple_list_adapter.adapter.SimpleListAdapter
import com.elian.simple_list_adapter.databinding.ItemOperationBinding
import com.elian.simple_list_adapter.model.OperationInfo

//region New way

@Suppress("FunctionName")
fun OperationAdapter_New(
	items: List<OperationInfo>,
	onItemClick: (operation: OperationInfo) -> Unit,
) = SimpleListAdapter(
	inflate = ItemOperationBinding::inflate,
	areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
) { binding, operation: OperationInfo, _ ->

	binding.apply()
	{
		tvFirstNumber.text = "${operation.firstNumber}"
		tvSecondNumber.text = "${operation.secondNumber}"
		tvOperationSymbol.text = operation.operationSymbol
		tvExpectedResult.text = "${operation.result}"
	}

	binding.root.setOnClickListener { onItemClick(operation) }

}.apply { submitList(items) }

//endregion

//region Old way

class OperationAdapter_Old(
	items: List<OperationInfo>,
	private inline val onItemClick: (operation: OperationInfo) -> Unit,
) : ListAdapter<OperationInfo, OperationAdapter_Old.ViewHolder>(
	object : DiffUtil.ItemCallback<OperationInfo>() {
		override fun areItemsTheSame(oldItem: OperationInfo, newItem: OperationInfo) = oldItem.uuid == newItem.uuid

		override fun areContentsTheSame(oldItem: OperationInfo, newItem: OperationInfo) = oldItem == newItem
	}
) {
	init {
		submitList(items)
	}

	class ViewHolder(val binding: ItemOperationBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemOperationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val operation = getItem(position)

		holder.binding.apply()
		{
			tvFirstNumber.text = "${operation.firstNumber}"
			tvSecondNumber.text = "${operation.secondNumber}"
			tvOperationSymbol.text = operation.operationSymbol
			tvExpectedResult.text = "${operation.result}"
		}

		holder.binding.root.setOnClickListener { onItemClick(operation) }
	}
}

//endregion