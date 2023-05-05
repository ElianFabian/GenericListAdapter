package com.elian.simple_list_adapter.model

import java.util.UUID

data class SkillInfo(
	val name: String,
	val percentage: Int,
	val uuid: String = UUID.randomUUID().toString(),
)