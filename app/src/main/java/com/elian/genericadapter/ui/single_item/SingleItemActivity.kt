package com.elian.genericadapter.ui.single_item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elian.genericadapter.databinding.ActivityGenericListBinding
import com.elian.genericadapter.model.OperationInfo

class SingleItemActivity : AppCompatActivity()
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
		val operationAdapter = OperationAdapter_New(listOfOperation)
		//val operationAdapter = OperationAdapter_Old(listOfOperation)

		binding.recyclerView.adapter = operationAdapter

		binding.fabShuffle.setOnClickListener()
		{
			operationAdapter.submitList(listOfOperation.shuffled())
		}
	}
}

private val listOfOperation: List<OperationInfo> = listOf(
	OperationInfo(1, 2, "+", 3),
	OperationInfo(1, 1, "+", 2),
	OperationInfo(21, 3, "-", 18),
	OperationInfo(1, 77, "+", 78),
	OperationInfo(12, 15, "+", 27),
	OperationInfo(52, 12, "+", 64),
	OperationInfo(6, 3, "*", 18),
	OperationInfo(100, 11, "+", 111),
	OperationInfo(50, 2, "+", 52),
	OperationInfo(123, 10, "-", 113),
	OperationInfo(72, 2, "*", 144),
)