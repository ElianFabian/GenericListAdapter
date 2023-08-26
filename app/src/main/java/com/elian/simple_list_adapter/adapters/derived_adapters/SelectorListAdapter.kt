package com.elian.simple_list_adapter.adapters.derived_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.elian.simple_list_adapter.adapters.SingleItemListAdapter

abstract class SelectorListAdapter<VB : ViewBinding, ItemT : Any>(
	inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
	diffCallback: DiffUtil.ItemCallback<ItemT>,
	private var isSelectionRequired: Boolean = true,
	private var isSingleSelection: Boolean = true,
	private val onItemSelectedChanged: (ItemT, isSelected: Boolean) -> Unit = { _, _ -> },
) : SingleItemListAdapter<VB, ItemT>(
	inflate = inflate,
	diffCallback = diffCallback,
) {
	var areItemsSelectable = true

	private val _selectedItems = mutableListOf<ItemT>()
	protected val selectedItems: List<ItemT> = _selectedItems


	fun selectItemAt(position: Int) {
		val item = getItem(position)
		selectItem(item)
	}

	fun unselectItemAt(position: Int) {
		val item = getItem(position)
		unselectItem(item)
	}

	fun selectItem(item: ItemT) {
		if (item in _selectedItems) {
			return
		}

		if (isSingleSelection) {
			val previousSelectedItem = _selectedItems.firstOrNull()

			if (previousSelectedItem != null) {
				unselectItem(previousSelectedItem)
			}
		}

		_selectedItems.add(item)

		val indexOfSelectedItem = currentList.indexOf(item)
		onItemSelectedChanged(item, true)
		notifyItemChanged(indexOfSelectedItem)
	}

	fun unselectItem(item: ItemT) {
		if (item !in _selectedItems) {
			return
		}

		val indexOfUnselectedItem = currentList.indexOf(item)
		onItemSelectedChanged(item, false)
		_selectedItems.remove(item)

		notifyItemChanged(indexOfUnselectedItem)
	}

	fun clearSelection() {
		val selectedItemsIndices = _selectedItems.map { currentList.indexOf(it) }

		_selectedItems.forEachIndexed { index, item ->
			onItemSelectedChanged(item, false)
			notifyItemChanged(selectedItemsIndices[index])
		}

		_selectedItems.clear()
	}

	protected fun isItemSelected(item: ItemT): Boolean {
		return item in _selectedItems
	}

	protected fun isItemSelectedAt(position: Int): Boolean {
		return isItemSelected(getItem(position))
	}

	/**
	 * When the view is clicked or somehow interacted with to select it,
	 * this functions must be called in order to apply the selection logic.
	 */
	protected fun applySelectionLogicToItem(item: ItemT) {
		if (!areItemsSelectable) {
			return
		}

		val isSelected = isItemSelected(item)
		val areThereAnyOtherSelectedItem = _selectedItems.any { selectedItem ->
			selectedItem != item
		}

		if (isSelected) {
			if (!isSelectionRequired || (!isSingleSelection && areThereAnyOtherSelectedItem)) {
				unselectItem(item)
			}
		}
		else {
			selectItem(item)
		}
	}


	override fun onCurrentListChanged(previousList: MutableList<ItemT>, currentList: MutableList<ItemT>) {
		super.onCurrentListChanged(previousList, currentList)

		if (previousList.isEmpty() && currentList.isNotEmpty() && isSelectionRequired && _selectedItems.isEmpty()) {
			val firstItem = currentList.firstOrNull() ?: return
			selectItem(firstItem)
		}
		if (currentList.isEmpty()) {
			clearSelection()
		}
	}
}


fun <T : Any> SelectorListAdapter<*, T>.selectItemIf(condition: (T) -> Boolean) {
	val item = currentList.firstOrNull(condition) ?: return
	selectItem(item)
}

fun <T : Any> SelectorListAdapter<*, T>.unselectItemIf(condition: (T) -> Boolean) {
	val item = currentList.firstOrNull(condition) ?: return
	unselectItem(item)
}