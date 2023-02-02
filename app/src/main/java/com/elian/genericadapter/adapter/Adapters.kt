package com.elian.genericadapter.adapter

import com.elian.genericadapter.databinding.ItemOperationBinding
import com.elian.genericadapter.databinding.ItemPersonBinding
import com.elian.genericadapter.model.OperationInfo
import com.elian.genericadapter.model.Person

// We can define extension functions to reuse the binding logic if need it
fun ItemOperationBinding.bind(item: OperationInfo) = item.apply()
{
	tvFirstNumber.text = "$firstNumber"
	tvSecondNumber.text = "$secondNumber"
	tvOperationSymbol.text = operationSymbol
	tvExpectedResult.text = "$result"
}

fun ItemPersonBinding.bind(item: Person) = item.apply()
{
	tvName.text = name
	tvLastname.text = lastname
}


@Suppress("FunctionName")
fun OperationAdapter(items: List<OperationInfo>) = GenericAdapter(
	inflate = ItemOperationBinding::inflate,
) { item: OperationInfo, _ ->

	bind(item)
}.apply { submitList(items) }



@Suppress("FunctionName")
fun PersonAdapter(items: List<Person>) = GenericAdapter(
	inflate = ItemPersonBinding::inflate,
) { item: Person, _ ->

	bind(item)
}.apply { submitList(items) }



@Suppress("FunctionName")
fun OperationAndPersonAdapter(
	items: List<Any>,
	onOperationClick: ((OperationInfo) -> Unit)? = null,
	onPersonClick: ((Person) -> Unit)? = null,
) = GenericMultiItemAdapter(
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

			bind(item)

			root.setOnClickListener { onOperationClick?.invoke(item) }
		},
		ItemBinding(ItemPersonBinding::inflate) { item: Person, _ ->

			bind(item)

			root.setOnClickListener { onPersonClick?.invoke(item) }
		},
	),
).apply { submitList(items) }