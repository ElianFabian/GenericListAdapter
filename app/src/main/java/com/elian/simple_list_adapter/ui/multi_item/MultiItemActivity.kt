package com.elian.simple_list_adapter.ui.multi_item

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elian.simple_list_adapter.databinding.ActivityGenericListBinding
import com.elian.simple_list_adapter.model.OtherUserMessage
import com.elian.simple_list_adapter.model.UserMessage

class MultiItemActivity : AppCompatActivity() {

	private lateinit var binding: ActivityGenericListBinding


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityGenericListBinding.inflate(layoutInflater)

		setContentView(binding.root)

		initUi()
	}


	private fun initUi() {
		val messagesAdapter = MessageAdapter_New(
			messages = messages,
			onUserMessageClick = { message ->
				Toast.makeText(this, "You sent '${message.content}' at ${message.hour}", Toast.LENGTH_SHORT).show()
			},
			onOtherUserMessageClick = { message ->
				Toast.makeText(this, "${message.senderName} sent '${message.content}' at ${message.hour}", Toast.LENGTH_SHORT).show()
			}
		)
//		val messagesAdapter = MessagesAdapter_Old(
//			messages = messages,
//			onUserMessageClick = { message ->
//				Toast.makeText(this, "You sent '${message.content}' at ${message.hour}", Toast.LENGTH_SHORT).show()
//			},
//			onOtherUserMessageClick = { message ->
//				Toast.makeText(this, "${message.senderName} sent '${message.content}' at ${message.hour}", Toast.LENGTH_SHORT).show()
//			}
//		)

		binding.recyclerView.adapter = messagesAdapter

		binding.fabShuffle.setOnClickListener {
			messagesAdapter.submitList(messages.shuffled())
		}
	}
}


private val messages = listOf(
	UserMessage("Hey Alice, hey Bob! I'm really excited about this project we're working on.", "2:00 PM"),
	OtherUserMessage("Alice", "Me too! It's been such a long time in the making, but I think we're finally getting close to making it a reality.", "2:05 PM"),
	OtherUserMessage("Bob", "Definitely. I think we've got a great team together and we're all passionate about the idea.", "2:10 PM"),
	UserMessage("Yeah, I really think this project could change our lives. It's a big undertaking, but I think it's worth it.", "2:15 PM"),
	OtherUserMessage("Alice", "Agreed. It's not often that you get a chance to work on something that has the potential to make such a big impact.", "2:20 PM"),
	OtherUserMessage("Bob", "And not just on our lives, but on the lives of others as well. That's what really motivates me.", "2:25 PM"),
	UserMessage("Absolutely. I think that's what makes this project so special. We're not just doing it for ourselves, but for the greater good.", "2:30 PM"),
	OtherUserMessage("Alice", "So, where do we go from here? What's the next step?", "2:35 PM"),
	UserMessage("Well, we still have some work to do on the prototype, but I think once we have that nailed down, we can start reaching out to potential investors and partners.", "2:40 PM"),
	OtherUserMessage("Bob", "And we'll need to start thinking about marketing and branding as well. We want to make sure our message is clear and resonates with our target audience.", "2:45 PM"),
	UserMessage("Definitely. And we'll also need to think about scalability. If this project takes off, we'll need to be prepared to handle growth.", "2:50 PM"),
	OtherUserMessage("Alice", "It sounds like we have a lot of work ahead of us, but I'm ready for the challenge.", "2:55 PM"),
	OtherUserMessage("Bob", "Same here. I really believe in this project and I think we have what it takes to make it a success.", "3:00 PM"),
	UserMessage("I couldn't agree more. I think this project has the potential to not only change our lives, but to make a real difference in the world.", "3:05 PM"),
	OtherUserMessage("Alice", "And that's what it's all about, right? Making a difference.", "3:10 PM"),
	OtherUserMessage("Bob", "Absolutely. Let's get to work and make this project a reality.", "3:15 PM"),
	UserMessage("Agreed. I'm excited to see where this journey takes us.", "3:20 PM"),
	UserMessage("Hey Alice, Bob, I've been thinking a lot about our project lately.", "10:00 AM"),
	OtherUserMessage("Alice", "Oh yeah? What's on your mind?", "10:05 AM"),
	OtherUserMessage("Bob", "I'm all ears. What do you want to talk about?", "10:05 AM"),
	UserMessage("Well, I've been doing some research on visual scripting and I think we could really make a big impact in this space.", "10:10 AM"),
	OtherUserMessage("Alice", "I agree. There are some existing tools out there, but they all have limitations that we could overcome with our approach.", "10:15 AM"),
	OtherUserMessage("Bob", "Definitely. We have some really innovative ideas that could make visual scripting more accessible and powerful than ever before.", "10:20 AM"),
	UserMessage("Exactly. I think our project has the potential to revolutionize the way people create applications.", "10:25 AM"),
	OtherUserMessage("Alice", "It's exciting to think about the impact we could have on the industry.", "10:30 AM"),
	OtherUserMessage("Bob", "But we still have a lot of work to do to make this a reality.", "10:35 AM"),
	UserMessage("Agreed. We need to focus on creating a user-friendly interface that can handle complex logic and actions.", "10:40 AM"),
	OtherUserMessage("Alice", "And we need to make sure that the visual scripting language we create is intuitive and easy to learn for developers of all skill levels.", "10:45 AM"),
	OtherUserMessage("Bob", "We also need to think about scalability and performance. We don't want our tool to be slow and unwieldy.", "10:50 AM"),
	UserMessage("Absolutely. That's why we need to be very intentional about our design and architecture choices.", "10:55 AM"),
	OtherUserMessage("Alice", "And we need to keep testing and refining our tool as we go, to make sure we're on the right track.", "11:00 AM"),
	OtherUserMessage("Bob", "Right. We don't want to spend months or years building something that ultimately doesn't work for our users.", "11:05 AM"),
	UserMessage("Definitely. We need to keep the user experience top of mind throughout the entire development process.", "11:10 AM"),
	OtherUserMessage("Alice", "But if we can create something truly innovative and useful, it could be a game-changer for the industry.", "11:15 AM"),
	OtherUserMessage("Bob", "And it could be a huge boost for our careers, too. Imagine being the team behind the next big thing in visual scripting!", "11:20 AM"),
	UserMessage("I know, it's a really exciting opportunity. But we need to stay focused and work hard to make it a reality.", "11:25 AM"),
	OtherUserMessage("Alice", "Agreed. Let's make it happen!", "11:30 AM"),
	OtherUserMessage("Bob", "Let's do it!", "11:35 AM"),
	UserMessage("Great. I'll set up a meeting for us to go over our progress and discuss next steps.", "11:40 AM"),
	OtherUserMessage("Alice", "Sounds good. Looking forward to it!", "11:45 AM"),
	OtherUserMessage("Bob", "Same here. Let's make this project a success!", "11:50 AM"),
	UserMessage("Hey, how's everyone doing with the project? It's been a few weeks since we last talked about it.", "10:00 AM"),
	OtherUserMessage("Alice", "I've been working on some user interface design concepts. We want to make sure it's really intuitive for people who aren't familiar with coding.", "10:05 AM"),
	OtherUserMessage("Bob", "I've been doing some research on visual scripting engines and how we can make ours stand out from the competition.", "10:10 AM"),
	UserMessage("That's great to hear. I've been thinking more about the technical side of things. I think we should focus on making it easy for developers to create custom nodes and integrations.", "10:15 AM"),
	OtherUserMessage("Alice", "Definitely. We don't want to limit what people can do with the platform.", "10:20 AM"),
	OtherUserMessage("Bob", "Agreed. We want to give people the flexibility to create whatever kind of application they want.", "10:25 AM"),
	UserMessage("One challenge I've been thinking about is how we can handle errors in the visual scripting environment. We need to make sure that it's clear where an error occurred and what caused it.", "10:30 AM"),
	OtherUserMessage("Alice", "That's a good point. We don't want people to get stuck when something goes wrong.", "10:35 AM"),
	OtherUserMessage("Bob", "Maybe we could highlight the nodes where an error occurred and provide more detailed error messages.", "10:40 AM"),
	UserMessage("Yeah, that's a good idea. We should also make it easy for people to debug their code by allowing them to step through it one node at a time.", "10:45 AM"),
	OtherUserMessage("Alice", "That would be really helpful. It's always easier to find a bug when you can see exactly what's happening at each step.", "10:50 AM"),
	OtherUserMessage("Bob", "We could also implement version control so that people can roll back to previous versions of their code if something goes wrong.", "10:55 AM"),
	UserMessage("That's a great idea. It will give people more confidence to experiment and try new things without worrying about breaking their code.", "11:00 AM"),
	OtherUserMessage("Alice", "Speaking of trying new things, what features are we planning to implement in the first release?", "11:05 AM"),
	UserMessage("Well, we definitely want to include support for popular programming languages like Java and Python. We should also have a library of pre-made nodes that people can use to get started.", "11:10 AM"),
	OtherUserMessage("Bob", "I think we should also include some kind of auto-complete functionality to help people write code faster.", "11:15 AM"),
	OtherUserMessage("Alice", "And we should make sure that the platform is extensible, so that people can create their own custom nodes and share them with the community.", "11:20 AM"),
	UserMessage("Those are all great ideas. I think if we can implement them successfully, we'll have a really powerful tool for developers.", "11:25 AM"),
	OtherUserMessage("Bob", "Agreed. But there's still a lot of work to do. We need to make sure that the platform is stable, fast, and scalable.", "11:30 AM"),
	OtherUserMessage("Alice", "And we need to make sure that it's accessible to people with different levels of programming experience.", "11:35 AM"),
	UserMessage("Definitely. It's a big challenge, but I'm excited to see what we can accomplish together.", "11:40 AM"),
)