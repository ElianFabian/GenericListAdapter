
# SimpleListAdapter

This repository offers a simple method to create list adapters in Kotlin without the need for excessive code. It is important to note that this is not a library, but rather a demonstration of using specific classes and functions to simplify the adapter creation process. While this approach may not address every scenario, it can help you implement straightforward concepts in a clean and fast manner. As mentioned since this isn't a library that provides a certain workflow to define adapters feel free to modify the code to fit your particular requirements.

To better understand the advantages of this approach, the repository includes a simple app with 3 screens:
- SingleItemAdapter
- Nested adapters
- MultiItemAdapter

In the file with the adapter definitions you have a the same adapter twice, one applying the method proposed here and the regular one to allow an easy comparison.

The primary focus of this simplification is on the binding aspect of adapters, which is typically the most important aspect of adapter creation.

## Setup

This method only requieres the ViewBinding feature in the build.gradle app:

```
buildFeatures {  
    viewBinding true  
}
```

Then add these files to your project:

[SingleItemListAdapter](https://github.com/ElianFabian/SimpleListAdapter/blob/main/app/src/main/java/com/elian/simple_list_adapter/adapter/SimpleSingleItemListAdapter.kt)

[MultiItemListAdapter](https://github.com/ElianFabian/SimpleListAdapter/blob/main/app/src/main/java/com/elian/simple_list_adapter/adapter/SimpleMultiItemListAdapter.kt)

## Usage

### SingleItemAdapter
Let's define a simple **single-item type adapter**! In this case, we will create an adapter for simple arithmetic operations.

We have this **data class** for the item:
```kt
data class OperationInfo(  
    val firstNumber: Int,  
    val secondNumber: Int,  
    val operationSymbol: String,  
    val result: Int,  
    val uuid: String = UUID.randomUUID().toString(),  
)
```
Next, we have the ** XML** layout that we will use for the item. We won't show the XML code here to keep it simple:![item_layout](https://user-images.githubusercontent.com/86477169/213011000-de3a436c-2efa-4376-8755-1c6ad792d12d.PNG)

Now, let's create our adapter. We'll start with a simple version:
```kt
@Suppress("FunctionName")
fun OperationAdapter() = SimpleListAdapter(
    inflate = ItemOperationBinding::inflate,
    areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
) { operation: OperationInfo, binding, position ->

    binding.apply()
    {
        tvFirstNumber.text = "${operation.firstNumber}"
        tvSecondNumber.text = "${operation.secondNumber}"
        tvOperationSymbol.text = operation.operationSymbol
        tvExpectedResult.text = "${operation.result}"
    }
}
```
And there you have it! A list adapter that's ready to use. Let's go into more detail:

As you can see, we're defining a function to create the adapter. Since it's meant to be used like a regular adapter, we'll use PascalCase naming convention (but of course, you can name it however you prefer).

We then indicate which *Binding class we're going to use by passing the inflate method as a reference. In the lambda block, we define the binding logic.

We also need to specify which item class we want to use. We could do this by using type parameters, but I think it's more elegant to put it in the lambda parameter.

Now let's say we want to be able to modify our list without any problem, pass an onItemClick lambda and a list when creating an instance of our adapter:
```kt
@Suppress("FunctionName")
fun OperationAdapter(
    items: List<OperationInfo>,
    onItemClick: (operation: OperationInfo) -> Unit,
) = SimpleListAdapter(
    inflate = ItemOperationBinding::inflate,
    areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
) { operation: OperationInfo, binding, _ ->

    binding.apply()
    {
        tvFirstNumber.text = "${operation.firstNumber}"
        tvSecondNumber.text = "${operation.secondNumber}"
        tvOperationSymbol.text = operation.operationSymbol
        tvExpectedResult.text = "${operation.result}"
    }

    binding.root.setOnClickListener { onItemClick(operation) }

}.apply { submitList(items) }
```
Now we're done! We can use it like any regular list adapter. By the way, you can also give a value to the areContentsTheSame parameter, but most of the time, it wouldn't be necessary since its default value is:

```kt
areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
```
It's also worth mentioning that the scope of the lambda is the adapter's one, so you can access its functions without any problem. However, since getItem() is marked as protected we've defined several extension functions inside the custom adapter class for **getItem()** and **getItemOrNull()** to use when needed. Also, since the return type of the SimpleListAdapter function is just ListAdapter, you won't be able to access those extension functions outside of the lambda to respect the original restriction of the getItem function in the ListAdapter class.

Now we could use it like this in an Activity or a Fragment:
```kt
val operationAdapter = OperationAdapter(
    items = listOfOperation,
    onItemClick = { operation ->
        operation.apply()
        {
            Toast.makeText(
                applicationContext,
                "$firstNumber $operationSymbol $secondNumber = $result",
                Toast.LENGTH_SHORT,
            ).show()
        }
    },
)

binding.recyclerView.adapter = operationAdapter
```
Feel free to check this [file](https://github.com/ElianFabian/SimpleListAdapter/blob/main/app/src/main/java/com/elian/simple_list_adapter/ui/single_item/SingleItemAdapters.kt) to compare this solution to the regular one.

Also if you want to see how to do the same thing with **nested adapters** check this [one](https://github.com/ElianFabian/SimpleListAdapter/blob/main/app/src/main/java/com/elian/simple_list_adapter/ui/nested_items/NestedItemsAdapters.kt).

<br>

### MultiItemAdapter
Let's now define a **simple multi-item type adapter**! It's actually quite similar to what we have seen so far, but instead of a binding block, we have a list of binding blocks.

For this adapter, we will be creating a chat message display. Here are our **sealed data classes**:
```kt
sealed class Message
{
    val uuid: String = UUID.randomUUID().toString()
}

data class UserMessage(
    val content: String,
    val hour: String,
) : Message()

data class OtherUserMessage(
    val senderName: String,
    val content: String,
    val hour: String,
) : Message()
```

The own user message layout:
<p align="left">
  <img src="https://user-images.githubusercontent.com/86477169/236632639-dd883076-bcb6-468e-a3a8-1b38ed290595.png" height="250" />
</p>

The other user message layout:
<p align="left">
  <img src="https://user-images.githubusercontent.com/86477169/236632705-d7107ccb-6666-4d7a-82b8-4c2a69005690.png" height="250" />
</p>

Now, we are ready to define our adapter:
```kt
@Suppress("FunctionName")
fun MessagesAdapter(
    messages: List<Message>,
    onUserMessageClick: (message: UserMessage) -> Unit,
    onOtherUserMessageClick: (message: OtherUserMessage) -> Unit,
) = SimpleListAdapter(
    areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
    itemBindings = listOf(
        Binding(ItemUserMessageBinding::inflate) { message: UserMessage, binding, position ->

            binding.apply()
            {
                tvContent.text = message.content
                tvTime.text = message.hour
            }

            binding.root.setOnClickListener { onUserMessageClick(message) }
        },
        Binding(ItemOtherUserMessageBinding::inflate) { message: OtherUserMessage, binding, position ->

            binding.apply()
            {
                tvSenderName.text = message.senderName
                tvContent.text = message.content
                tvTime.text = message.hour
            }

            binding.root.setOnClickListener { onOtherUserMessageClick(message) }
        },
    ),
).apply { submitList(messages) }
```

In this case to specify the bindings we make use of the Binding() function, what it simply does is to return the data needed for the adapter to know how to bind any item type to its view.

And there it is, a simple, clean, and ready-to-use multi-item adapter! Feel free to check out this [file](https://github.com/ElianFabian/SimpleListAdapter/blob/main/app/src/main/java/com/elian/simple_list_adapter/ui/multi_item/MultiItemAdapter.kt) to compare this solution to the regular one.
