package com.elian.genericadapter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elian.genericadapter.adapter.OperationAdapter
import com.elian.genericadapter.adapter.OperationAndPersonAdapter
import com.elian.genericadapter.databinding.ActivityMainBinding
import com.elian.genericadapter.model.MultiItem
import com.elian.genericadapter.model.OperationInfo
import com.elian.genericadapter.model.Person

class MainActivity : AppCompatActivity()
{
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
	
		initUi()
	}

	private fun initUi()
	{
		// In here you have 2 examples of the regular GenericAdapter and 1 of the GenericMultiItemAdapter
		// Comment out the code you want to try out

		val operationAndPersonAdapter = OperationAndPersonAdapter(
			items = listOfMultiItem,
			onOperationClick = {
				Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
			},
			onPersonClick = {
				Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
			},
		)
		binding.rvMultiItems.adapter = operationAndPersonAdapter
		binding.fabShuffle.setOnClickListener()
		{
			operationAndPersonAdapter.submitList(listOfMultiItem.shuffled())
		}

		// ------------------------------------------------------------------------------

//		val operationAdapter = OperationAdapter(listOfOperation)
//		binding.rvMultiItems.adapter = operationAdapter
//		binding.fabShuffle.setOnClickListener()
//		{
//			operationAdapter.submitList(listOfOperation.shuffled())
//		}

		// ------------------------------------------------------------------------------

//		val personAdapter = PersonAdapter(listOfPerson)
//		binding.rvMultiItems.adapter = personAdapter
//		binding.fabShuffle.setOnClickListener()
//		{
//			personAdapter.submitList(listOfPerson.shuffled())
//		}
	}
}

private val listOfMultiItem: List<MultiItem> = listOf(
	OperationInfo(1, 2, "+", 3),
	OperationInfo(1, 1, "+", 2),
	Person("Lisa", "Brown"),
	OperationInfo(21, 3, "-", 18),
	OperationInfo(1, 77, "+", 78),
	OperationInfo(12, 15, "+", 27),
	Person("John", "Smith"),
	OperationInfo(52, 12, "+", 64),
	Person("Drake", "Wolf"),
	Person("Josh", "Knight"),
	Person("Scarlet", "Cloud"),
	OperationInfo(6, 3, "*", 18),
	OperationInfo(100, 11, "+", 111),
	Person("Nicolas", "Cage"),
	OperationInfo(5, 2, "+", 7),
)

private val listOfOperation: List<OperationInfo> = listOf(
	OperationInfo(1, 2, "+", 3),
	OperationInfo(1, 1, "+", 2),
	OperationInfo(21, 3, "-", 18),
	OperationInfo(1, 77, "+", 78),
	OperationInfo(12, 15, "+", 27),
	OperationInfo(52, 12, "+", 64),
	OperationInfo(6, 3, "*", 18),
	OperationInfo(100, 11, "+", 111),
	OperationInfo(5, 2, "+", 7),
)

private val listOfPerson: List<Person> = listOf(
	Person("Lisa", "Brown"),
	Person("John", "Smith"),
	Person("Drake", "Wolf"),
	Person("Josh", "Knight"),
	Person("Scarlet", "Cloud"),
	Person("Nicolas", "Cage"),
)