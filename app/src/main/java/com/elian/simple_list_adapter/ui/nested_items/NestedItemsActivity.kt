package com.elian.simple_list_adapter.ui.nested_items

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elian.simple_list_adapter.databinding.ActivityGenericListBinding
import com.elian.simple_list_adapter.model.Person
import com.elian.simple_list_adapter.model.SkillInfo

class NestedItemsActivity : AppCompatActivity() {
	private lateinit var binding: ActivityGenericListBinding


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityGenericListBinding.inflate(layoutInflater)

		setContentView(binding.root)

		initUi()
	}


	private fun initUi() {
		val skillsByPersonAdapter = SkillsByPersonAdapter_New(skillsByPerson)
		//val skillsByPersonAdapter = SkillsByPersonAdapter_Old(skillsByPerson)

		binding.recyclerView.adapter = skillsByPersonAdapter

		binding.fabShuffle.setOnClickListener {
			skillsByPersonAdapter.submitList(skillsByPerson.map { it.key to it.value.shuffled() }.shuffled())
		}
	}
}


private val skillsByPerson = mapOf(
	Person("Alice", "Anderson") to listOf(
		SkillInfo("Programming", 80),
		SkillInfo("Design", 60),
		SkillInfo("Communication", 90),
	),
	Person("Bob", "Becker") to listOf(
		SkillInfo("Programming", 90),
		SkillInfo("Testing", 80),
		SkillInfo("Documentation", 70),
	),
	Person("Charlie", "Chaplin") to listOf(
		SkillInfo("Programming", 70),
		SkillInfo("Design", 80),
		SkillInfo("Communication", 90),
		SkillInfo("Testing", 60),
	),
	Person("Dave", "Doe") to listOf(
		SkillInfo("Programming", 90),
		SkillInfo("Testing", 90),
		SkillInfo("Documentation", 80),
		SkillInfo("Communication", 70),
	),
)