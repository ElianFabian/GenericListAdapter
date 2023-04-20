package com.elian.generic_list_adapter.model

import java.util.UUID

data class OperationInfo(
	val firstNumber: Int,
	val secondNumber: Int,
	val operationSymbol: String,
	val result: Int,
	val uuid: String = UUID.randomUUID().toString(),
)