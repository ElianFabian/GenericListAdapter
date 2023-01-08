package com.elian.genericmultiitemadapter.adapter

import com.elian.genericmultiitemadapter.databinding.ItemOperationBinding
import com.elian.genericmultiitemadapter.databinding.ItemPersonBinding
import com.elian.genericmultiitemadapter.model.OperationInfo
import com.elian.genericmultiitemadapter.model.Person

@Suppress("FunctionName")
fun OperationAdapter(items: List<OperationInfo>) = GenericAdapter(
	items = items,
	inflate = ItemOperationBinding::inflate,
) { item, _ ->

	item.apply()
	{
		tvFirstNumber.text = "$firstNumber"
		tvSecondNumber.text = "$secondNumber"
		tvOperationSymbol.text = operationSymbol
		tvExpectedResult.text = "$result"
	}
}

@Suppress("FunctionName")
fun PersonAdapter(items: List<Person>) = GenericAdapter(
	items = items,
	inflate = ItemPersonBinding::inflate,
) { item, _ ->

	item.apply()
	{
		tvName.text = name
		tvLastname.text = lastname
	}
}

@Suppress("FunctionName")
fun OperationAndPersonAdapter(
	items: List<Any>,
	onOperationClick: ((OperationInfo) -> Unit)? = null,
	onPersonClick: ((Person) -> Unit)? = null,
) = GenericMultiItemAdapter(
	items = items,
	// If the items have an id you should use it in this function
	areItemsTheSame = { oldItem, newItem ->
		when (oldItem)
		{
			is OperationInfo -> oldItem == newItem
			is Person        -> oldItem == newItem
			else             -> error("Unexpected type '${oldItem::class}' in adapter")
		}
	},
	itemBindings = listOf(
		ItemBinding(ItemOperationBinding::inflate) { item: OperationInfo, _ ->

			item.apply()
			{
				tvFirstNumber.text = "$firstNumber"
				tvOperationSymbol.text = operationSymbol
				tvSecondNumber.text = "$secondNumber"
				tvExpectedResult.text = "$result"
			}

			root.setOnClickListener { onOperationClick?.invoke(item) }
		},
		ItemBinding(ItemPersonBinding::inflate) { item: Person, position ->

			item.apply()
			{
				tvName.text = "$name ($position)"
				tvLastname.text = lastname
				println("$$$$ $name-$position")
			}

			root.setOnClickListener { onPersonClick?.invoke(item) }
		}
	),
)