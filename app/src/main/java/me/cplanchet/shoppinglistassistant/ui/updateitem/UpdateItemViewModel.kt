package me.cplanchet.shoppinglistassistant.ui.updateitem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.navigation.destinations.UpdateItemDestination
import me.cplanchet.shoppinglistassistant.ui.state.*

class UpdateItemViewModel(
    private val repository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val listId: Int = checkNotNull(savedStateHandle[UpdateItemDestination.listIdArg])
    private val itemId: Int = checkNotNull(savedStateHandle[UpdateItemDestination.itemIdArg])

    var itemUIState by mutableStateOf(ItemUIState())
        private set
    var listItemUIState by mutableStateOf(ListItemUIState())
        private set
    var categoryUIState: MutableStateFlow<UpdateItemUIState> = MutableStateFlow(UpdateItemUIState())
        private set

    fun updateItemUIState(newItemUIState: ItemUIState) {
        itemUIState = newItemUIState.copy()
    }

    fun updateListItemUIState(newListItemUIState: ListItemUIState) {
        listItemUIState = newListItemUIState.copy()
    }

    suspend fun saveItem() {
        if (itemUIState.isValid()) {
            repository.updateItem(itemUIState.toItem())
        }
    }

    suspend fun saveListItem() {
        if (listItemUIState.isValid()) {
            repository.updateListItem(listItemUIState.toListItem(), listId)
        }
    }

    suspend fun removeListItem() {
        repository.deleteListItem(listItemUIState.toListItem(), listId)
    }

    suspend fun deleteItem() {
        repository.deleteItem(itemUIState.toItem())
    }

    private fun updateItemCategoryName() {
        val category = categoryUIState.value.categories.find { category -> category.id == itemUIState.category?.id }
        updateItemUIState(itemUIState.copy(category = category))
    }

    init {
        viewModelScope.launch {
            repository.getItemById(itemId).collect {
                itemUIState = it.toItemUiState()
            }
        }
        viewModelScope.launch {
            repository.getListItemById(listId, itemId).collect {
                listItemUIState = it.toListItemUIState()
            }
        }
        viewModelScope.launch {
            repository.getAllCategories().collect {
                categoryUIState.value = UpdateItemUIState(it)

                if (itemUIState.category != null) {
                    updateItemCategoryName()
                }
            }
        }
    }
}