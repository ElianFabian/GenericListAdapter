package com.elian.generic_list_adapter.ui.multi_item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.elian.generic_list_adapter.R
import com.elian.generic_list_adapter.adapter.Binding
import com.elian.generic_list_adapter.adapter.GenericListAdapter
import com.elian.generic_list_adapter.databinding.ItemOtherUserMessageBinding
import com.elian.generic_list_adapter.databinding.ItemUserMessageBinding
import com.elian.generic_list_adapter.model.Message
import com.elian.generic_list_adapter.model.OtherUserMessage
import com.elian.generic_list_adapter.model.UserMessage

//region New way

@Suppress("FunctionName")
fun MessagesAdapter_New(
	messages: List<Message>,
	onUserMessageClick: (message: UserMessage) -> Unit,
	onOtherUserMessageClick: (message: OtherUserMessage) -> Unit,
) = GenericListAdapter(
	areItemsTheSame = { oldItem, newItem -> oldItem.uuid == newItem.uuid },
	itemBindings = listOf(
		Binding(ItemUserMessageBinding::inflate) { message: UserMessage, binding ->

			binding.apply()
			{
				tvContent.text = message.content
				tvTime.text = message.hour
			}

			binding.root.setOnClickListener { onUserMessageClick(message) }
		},
		Binding(ItemOtherUserMessageBinding::inflate) { message: OtherUserMessage, binding ->

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

//endregion


//region Old way


// On the internet you may find different ways to implement a MultiItemAdapter, but all in the end are boilerplate code like this.

class MessagesAdapter_Old(
	messages: List<Message>,
	private val onUserMessageClick: (userMessage: UserMessage) -> Unit,
	private val onOtherUserMessageClick: (userMessage: OtherUserMessage) -> Unit,
) : ListAdapter<Message, MessagesAdapter_Old.MessageViewHolder<Message, ViewBinding>>(
	object : DiffUtil.ItemCallback<Message>()
	{
		override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.uuid == newItem.uuid
		override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
	}
)
{
	init
	{
		submitList(messages)
	}

	abstract class MessageViewHolder<out M : Message, out VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
	{
		abstract fun bind(message: @UnsafeVariance M)
	}

	inner class UserMessageViewHolder(binding: ItemUserMessageBinding) : MessageViewHolder<UserMessage, ItemUserMessageBinding>(binding)
	{
		override fun bind(message: UserMessage)
		{
			binding.apply()
			{
				tvContent.text = message.content
				tvTime.text = message.hour
			}

			binding.root.setOnClickListener { onUserMessageClick(message) }
		}
	}

	inner class OtherUserMessageViewHolder(binding: ItemOtherUserMessageBinding) : MessageViewHolder<OtherUserMessage, ItemOtherUserMessageBinding>(binding)
	{
		override fun bind(message: OtherUserMessage)
		{
			binding.apply()
			{
				tvSenderName.text = message.senderName
				tvContent.text = message.content
				tvTime.text = message.hour
			}

			binding.root.setOnClickListener { onOtherUserMessageClick(message) }
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<Message, ViewBinding>
	{
		val layoutInflater = LayoutInflater.from(parent.context)

		return when (viewType)
		{
			R.layout.item_user_message       -> UserMessageViewHolder(ItemUserMessageBinding.inflate(layoutInflater, parent, false))
			R.layout.item_other_user_message -> OtherUserMessageViewHolder(ItemOtherUserMessageBinding.inflate(layoutInflater, parent, false))
			else                             -> error("Unexpected viewType '$viewType'")
		}
	}

	override fun getItemViewType(position: Int) = when (getItem(position))
	{
		is UserMessage      -> R.layout.item_user_message
		is OtherUserMessage -> R.layout.item_other_user_message
	}

	override fun onBindViewHolder(holder: MessageViewHolder<Message, ViewBinding>, position: Int)
	{
		holder.bind(getItem(position))
	}
}

//endregion