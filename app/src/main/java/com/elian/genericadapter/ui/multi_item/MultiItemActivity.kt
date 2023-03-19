package com.elian.genericadapter.ui.multi_item

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elian.genericadapter.databinding.ActivityGenericListBinding
import com.elian.genericadapter.model.OtherUserMessage
import com.elian.genericadapter.model.UserMessage

class MultiItemActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityGenericListBinding


	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		binding = ActivityGenericListBinding.inflate(layoutInflater)

		setContentView(binding.root)

		initUi()
	}


	private fun initUi()
	{
		val messagesAdapter = MessagesAdapter_New(
			messages = messages,
			onUserMessageClick = {
				Toast.makeText(this, "You sent '${it.content}' at ${it.hour}", Toast.LENGTH_SHORT).show()
			},
			onOtherUserMessageClick = {
				Toast.makeText(this, "${it.senderName} sent '${it.content}' at ${it.hour}", Toast.LENGTH_SHORT).show()
			}
		)
//		val messagesAdapter = MessagesAdapter_Old(
//			messages = messages,
//			onUserMessageClick = {
//				Toast.makeText(this, "You sent '${it.content}' at ${it.hour}", Toast.LENGTH_SHORT).show()
//			},
//			onOtherUserMessageClick = {
//				Toast.makeText(this, "${it.senderName} sent '${it.content}' at ${it.hour}", Toast.LENGTH_SHORT).show()
//			}
//		)

		binding.recyclerView.adapter = messagesAdapter

		binding.fabShuffle.setOnClickListener()
		{
			messagesAdapter.submitList(messages.shuffled())
		}
	}
}


private val messages = listOf(
	UserMessage("Hello!", "10:30 AM"),
	OtherUserMessage("John", "Hi there!", "10:35 AM"),
	UserMessage("How are you?", "10:32 AM"),
	OtherUserMessage("Mary", "I'm good, thanks. How about you?", "10:38 AM"),
	UserMessage("What are you up to today?", "10:40 AM"),
	OtherUserMessage("Steve", "Just hanging out at home. How about you?", "10:42 AM"),
	UserMessage("Same here, just taking it easy.", "10:45 AM"),
	OtherUserMessage("Kate", "Have you seen the new movie that just came out?", "10:50 AM"),
	UserMessage("No, I haven't had a chance to see it yet.", "10:55 AM"),
	OtherUserMessage("Tom", "It's really good, you should definitely check it out.", "10:58 AM"),
	UserMessage("Thanks for the recommendation!", "11:00 AM"),
	OtherUserMessage("Emma", "No problem, let me know what you think.", "11:05 AM"),
	UserMessage("Will do!", "11:10 AM"),
	OtherUserMessage("David", "Hey, did you hear about the new restaurant that just opened?", "11:15 AM"),
	UserMessage("No, I haven't. What's it called?", "11:20 AM"),
	OtherUserMessage("Emily", "It's called The Bistro. I heard the food is amazing.", "11:25 AM"),
	UserMessage("Sounds great, I'll have to check it out!", "11:30 AM"),
	OtherUserMessage("Jason", "Hey, did you get a chance to look at those documents?", "11:35 AM"),
	UserMessage("Not yet, but I'll take a look as soon as I can.", "11:40 AM"),
	OtherUserMessage("Sophie", "Thanks, I appreciate it.", "11:45 AM"),
	UserMessage("No problem!", "11:50 AM"),
	OtherUserMessage("Liam", "Are you still interested in joining our team?", "11:55 AM"),
	UserMessage("Yes, definitely! Can you send me more information about it?", "12:00 PM"),
	OtherUserMessage("Olivia", "Sure, I'll send you an email with all the details.", "12:05 PM"),
	UserMessage("Thanks, I look forward to hearing from you!", "12:10 PM"),
	OtherUserMessage("Jacob", "Hey, do you want to grab lunch later?", "12:15 PM"),
	UserMessage("Sure, where do you want to go?", "12:20 PM"),
	OtherUserMessage("Ava", "How about that new sandwich place that just opened up?", "12:25 PM"),
	UserMessage("Sounds good to me!", "12:30 PM"),
)