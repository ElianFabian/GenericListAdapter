package com.elian.genericadapter.model

import java.util.*

data class SkillInfo(
	val name: String,
	val percentage: Int,
	val uuid: String = UUID.randomUUID().toString(),
)