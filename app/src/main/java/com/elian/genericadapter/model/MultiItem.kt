package com.elian.genericadapter.model

import java.util.*

sealed class MultiItem
{
	val uuid: String = UUID.randomUUID().toString()
}

data class Person(
	val name: String,
	val lastname: String,
) : MultiItem()

data class OperationInfo(
	val firstNumber: Int,
	val secondNumber: Int,
	val operationSymbol: String,
	val result: Int,
) : MultiItem()