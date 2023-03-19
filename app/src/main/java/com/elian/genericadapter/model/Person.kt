package com.elian.genericadapter.model

import java.util.*

data class Person(
	val name: String,
	val lastname: String,
	val uuid: String = UUID.randomUUID().toString(),
)