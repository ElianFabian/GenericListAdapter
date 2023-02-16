package com.elian.genericadapter.adapter

import com.elian.genericadapter.databinding.ItemOperationBinding
import com.elian.genericadapter.databinding.ItemPersonBinding
import com.elian.genericadapter.model.MultiItem
import com.elian.genericadapter.model.OperationInfo
import com.elian.genericadapter.model.Person

/*
 * In this file we define all the adapters, in here were using the simple version of both GenericAdapters,
 * but in case you need a more complex use case like need to access the previous or next item
 * you should instead instantiate the GenericAdapter or use the other version of the ItemBinding.
 * You could even inherit from those classes and add extra functionality since they're marked as open.
 */


// We can define extension functions to reuse the binding logic if needed.

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

// The GenericAdapter inherits from a ListAdapter, so you should always give a value
// to the areItemsTheSame parameter.
// But, if the list is going to be static (never modified) then it won't be necessary.
// And the areContents the same in general I don't think you should define it unless you want a different behaviour.

@Suppress("FunctionName")
fun OperationAdapter(items: List<OperationInfo>) = GenericAdapter(
	inflate = ItemOperationBinding::inflate,
	areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
) { item: OperationInfo ->

	bind(item)

}.apply { submitList(items) }


@Suppress("FunctionName")
fun PersonAdapter(items: List<Person>) = GenericAdapter(
	areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
	inflate = ItemPersonBinding::inflate,
) { item: Person ->

	bind(item)

}.apply { submitList(items) }


// For a MultiItem adapter it's pretty useful to make use of a sealed class/interface
// it gives you better type safety and also a cleaner way to define the value for the areItemsTheSame parameter.

// If you do not use a sealed class you should do something like this for the areItemsTheSame parameter:
//areItemsTheSame = { oldItem, newItem ->
//	when
//	{
//		oldItem is OperationInfo && newItem is OperationInfo -> oldItem.uuid == newItem.uuid
//		oldItem is Person && newItem is Person               -> oldItem.uuid == newItem.uuid
//		else                                                 -> error("Unexpected type '${oldItem::class}' in adapter.")
//	}
//}

@Suppress("FunctionName")
fun OperationAndPersonAdapter(
	items: List<MultiItem>,
	onOperationClick: ((OperationInfo) -> Unit)? = null,
	onPersonClick: ((Person) -> Unit)? = null,
) = GenericAdapter(
	areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
	itemBindings = listOf(
		Binding(ItemOperationBinding::inflate) { item: OperationInfo ->

			bind(item)

			root.setOnClickListener { onOperationClick?.invoke(item) }
		},
		Binding(ItemPersonBinding::inflate) { item: Person ->

			bind(item)

			root.setOnClickListener { onPersonClick?.invoke(item) }
		},
	),
).apply { submitList(items) }