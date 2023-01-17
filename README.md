# GenericAdapter

This is a simple project for 2 classes that makes easier to work with adpaters for recyclerviews.

One class is **GenericAdapter** that is used for single-item adapters and the other is **GenericMultiItemAdapter** to work with more than one type of item.

Firstly, this is **not a serious project**, I just made it because I found the idea interesting and I think there may be people who may like it, at least the concept.

Now let's see how to use them:

## GenericAdapter  class

Let's first think what the essential parts of an adapter are actually relevant:
- The xml layout of the item.
- The data type of the item (which in Kotlin will regularly be a data class).
- The binding of both.

Now to better illustrate the usage I will show you an example used in the app about an item of a simple math operation:

###  The layout
![item_layout](https://user-images.githubusercontent.com/86477169/213011000-de3a436c-2efa-4376-8755-1c6ad792d12d.PNG)
This is the xml layout we're going to use (to make it simpler, I won't show xml content in this readme).

### The item data class
![item_data_class](https://user-images.githubusercontent.com/86477169/213011430-46da9f84-96b1-4fa9-870e-e0823bbdd82b.PNG)

### The binding
Finally, we're going to use the generic adapter, the way to go is to create a function that will create an instance of the GenericAdapter with the information we want:
![binding](https://user-images.githubusercontent.com/86477169/213013201-9a12652e-b937-4457-bd14-60f29aa3d190.PNG)
When we instantiate the GenericAdapter class we only have to provide the inflate function of our layout binding and a lambda with the actual binding.

The **first parameter** of the lambda is the item we want to bind (you should indicate the type of the item in the lambda if you want to avoid putting it as a generic argument between the "<>" symbols).
The **second parameter** (that in this case is not being used) is the viewholder which can be useful to have in some scenarios.
In the lambda the context or in other words the **this** is the binding class so that's why you can access the members of the binding without typing "binding.whatever" every time.

As you can see at the bottom we use the submitList() function, that function is from the ListAdapter (the class that the GenericAdapter inherits from) and that step is optional it's just for having the list full when instatiating the adapter.

As this uses a ListAdapter and assuming you're using a data class for the item, you should actually always set the **areItemsTheSame** parameter in the GenericAdapter class to have an appropiate behaviour.


![instantiation of the operation adapter](https://user-images.githubusercontent.com/86477169/213016492-ad71ff06-613d-46c8-9e59-aae0c08042f1.PNG)
Now you only have to set your recyclerview adapter with the one we created and that's all.

PD: for convention, the name of this type of function is in PascalCase as it's usage it's like instantiating a new instance of a custom class.
