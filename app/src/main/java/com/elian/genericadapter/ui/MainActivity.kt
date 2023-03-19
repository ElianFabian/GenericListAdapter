package com.elian.genericadapter.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elian.genericadapter.databinding.ActivityMainBinding
import com.elian.genericadapter.ui.multi_item.MultiItemActivity
import com.elian.genericadapter.ui.nested_items.NestedItemsActivity
import com.elian.genericadapter.ui.single_item.SingleItemActivity

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
		binding.apply()
		{
			btnGoToSingleItem.setOnClickListener()
			{
				startActivity(Intent(this@MainActivity, SingleItemActivity::class.java))
			}
			btnGoToNestedItems.setOnClickListener()
			{
				startActivity(Intent(this@MainActivity, NestedItemsActivity::class.java))
			}
			btnGoToMultiItem.setOnClickListener()
			{
				startActivity(Intent(this@MainActivity, MultiItemActivity::class.java))
			}
		}
	}
}