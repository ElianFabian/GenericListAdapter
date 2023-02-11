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
![image](https://user-images.githubusercontent.com/86477169/218253563-8502eed6-a415-4365-bea6-45996addf399.png)
The uuid in here may be relevant since the GenericAdapter inherits from the ListAdapter.

### The binding
Finally, we're going to use the generic adapter, the way to go is to create a function that will create an instance of the GenericAdapter with the information we want:
![image](https://user-images.githubusercontent.com/86477169/218253729-9c4d6421-2799-4748-a1c3-9bc531846877.png)


When we instantiate the GenericAdapter class we only have to provide the inflate function of our layout binding and a lambda with the actual binding.

The **first parameter** of the lambda is the item we want to bind (you should indicate the type of the item in the lambda if you want to avoid putting it as a generic argument between the "<>" symbols).
The **second parameter** (that in this case is not being used) is the viewholder which can be useful to have in some scenarios.
In the lambda the context or in other words the **this** is the binding class so that's why you can access the members of the binding without typing "binding.whatever" every time.

As you can see at the bottom we use the submitList() function, that function is from the ListAdapter (the class that the GenericAdapter inherits from) and that step is optional it's just for having the list full when instatiating the adapter.

As this uses a ListAdapter and assuming you're using a data class for the item, you should set the **areItemsTheSame** parameter in the GenericAdapter class to have an appropiate behaviour.
But, in case your list is going to be static (never modified) it won't matter.


![instantiation of the operation adapter](https://user-images.githubusercontent.com/86477169/213016492-ad71ff06-613d-46c8-9e59-aae0c08042f1.PNG)

If we want we can add more interaction to this adding a onItemClick parameter like this:

![image](https://user-images.githubusercontent.com/86477169/218253832-49c087fb-e9e6-47a7-9ccb-9e0fb06bd5ec.png)

And then use it like this:
![image](https://user-images.githubusercontent.com/86477169/218253959-55be0b75-d2e3-4c57-bcc2-ece213eee0e7.png)

Now you only have to set your recyclerview adapter with the one we created and that's all!

PD: for convention, the name of this type of function is in PascalCase as it's usage it's like instantiating a new instance of a custom class.

## GenericMultiItemAdapter  class

This one is very similar than the previous one, to make a good use of this class we should define a sealed class/interface for best practice:

![image](https://user-images.githubusercontent.com/86477169/218254258-aa34545b-4cdd-49c1-8124-4d8267f5dc01.png)

Now let's create the adapter:
![image](https://user-images.githubusercontent.com/86477169/218254497-6d0051bf-1620-4265-ac84-05783fec18b4.png)

In this case we now have a **list of bindings** and to create them we use the ItemBinding function.

As we use a sealed class it's easy to set the value of the **areItemsTheSame**.

And this is how we use it:
![image](https://user-images.githubusercontent.com/86477169/218254383-6e2f4b00-1ab5-4666-95b4-9f323771950c.png)


# Extra
I hope you find this interesting and in case you want to understand it better try to play with the example of this repository.
