package com.elian.generic_list_adapter.model

import java.util.UUID

data class Person(
	val name: String,
	val lastname: String,
	val uuid: String = UUID.randomUUID().toString(),
)