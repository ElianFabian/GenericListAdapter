package com.elian.genericmultiitemadapter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elian.genericmultiitemadapter.adapter.OperationAndPersonAdapter
import com.elian.genericmultiitemadapter.databinding.ActivityMainBinding
import com.elian.genericmultiitemadapter.model.OperationInfo
import com.elian.genericmultiitemadapter.model.Person

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
		val multiItemAdapter = OperationAndPersonAdapter(
			items = listOfMultiItem,
			onOperationClick =
			{
				Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
			},
			onPersonClick =
			{
				Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
			},
		)

		binding.rvMultiItems.adapter = multiItemAdapter

		binding.fabShuffle.setOnClickListener()
		{
			multiItemAdapter.submitList(listOfMultiItem.shuffled())
		}

		//binding.rvMultiItems.adapter = OperationAdapter(listOfOperation)
		//binding.rvMultiItems.adapter = PersonAdapter(listOfPerson)
	}
}


val listOfMultiItem = listOf(
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

val listOfOperation = listOf(
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

val listOfPerson = listOf(
	Person("Lisa", "Brown"),
	Person("John", "Smith"),
	Person("Drake", "Wolf"),
	Person("Josh", "Knight"),
	Person("Scarlet", "Cloud"),
	Person("Nicolas", "Cage"),
)