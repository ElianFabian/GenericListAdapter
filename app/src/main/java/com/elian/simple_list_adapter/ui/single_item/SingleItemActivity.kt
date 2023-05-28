package com.elian.simple_list_adapter.ui.single_item

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elian.simple_list_adapter.databinding.ActivityGenericListBinding
import com.elian.simple_list_adapter.model.OperationInfo

class SingleItemActivity : AppCompatActivity() {

	private lateinit var binding: ActivityGenericListBinding


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityGenericListBinding.inflate(layoutInflater)

		setContentView(binding.root)

		initUi()
	}


	private fun initUi() {

		val operationAdapter = OperationAdapter_New(
			items = listOfOperation,
			onItemClick = { operation ->

				val message = operation.run { "$firstNumber $operationSymbol $secondNumber = $result" }

				Toast.makeText(
					applicationContext,
					message,
					Toast.LENGTH_SHORT,
				).show()
			},
		)
//		val operationAdapter = OperationAdapter_Old(
//			items = listOfOperation,
//			onItemClick = { operation ->
//
//				val message = operation.run { "$firstNumber $operationSymbol $secondNumber = $result" }
//
//				Toast.makeText(
//					applicationContext,
//					message,
//					Toast.LENGTH_SHORT,
//				).show()
//			},
//		)

		binding.recyclerView.adapter = operationAdapter

		binding.fabShuffle.setOnClickListener {
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
	OperationInfo(14, 7, "+", 21),
	OperationInfo(8, 4, "*", 32),
	OperationInfo(27, 9, "/", 3),
	OperationInfo(15, 5, "-", 10),
	OperationInfo(7, 3, "+", 10),
	OperationInfo(24, 6, "*", 144),
	OperationInfo(81, 3, "/", 27),
	OperationInfo(11, 11, "+", 22),
	OperationInfo(16, 8, "/", 2),
	OperationInfo(9, 3, "*", 27),
	OperationInfo(50, 25, "-", 25),
	OperationInfo(6, 2, "/", 3),
)