package com.elian.genericadapter.model

import java.util.*

data class OperationInfo(
	val firstNumber: Int,
	val secondNumber: Int,
	val operationSymbol: String,
	val result: Int,
	val uuid: String = UUID.randomUUID().toString(),
)