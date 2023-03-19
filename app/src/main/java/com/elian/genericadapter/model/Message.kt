package com.elian.genericadapter.model

import java.util.*

sealed class Message
{
	val uuid: String = UUID.randomUUID().toString()
}

data class UserMessage(
	val content: String,
	val hour: String
) : Message()

data class OtherUserMessage(
	val senderName: String,
	val content: String,
	val hour: String
) : Message()