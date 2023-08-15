package me.cplanchet.shoppinglistassistant.ui.updateitem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.navigation.destinations.UpdateItemDestination
import me.cplanchet.shoppinglistassistant.ui.state.*

class UpdateItemViewModel(
    private val repository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val listId: Int = checkNotNull(savedStateHandle[UpdateItemDestination.listIdArg])
    private val itemId: Int = checkNotNull(savedStateHandle[UpdateItemDestination.itemIdArg])

    var itemUIState by mutableStateOf(ItemUIState())
        private set
    var listItemUIState by mutableStateOf(ListItemUIState())
        private set
    val categoryUIState = repository.getAllCategories().map{ UpdateItemUIState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UpdateItemUIState()
    )

    fun updateItemUIState(newItemUIState: ItemUIState){
        itemUIState = newItemUIState.copy()
    }
    fun updateListItemUIState(newListItemUIState: ListItemUIState){
        listItemUIState = newListItemUIState.copy()
    }

    suspend fun saveItem(){
        if(itemUIState.isValid()){
            repository.updateItem(itemUIState.toItem())
        }
    }
    suspend fun saveListItem(){
        if(listItemUIState.isValid()){
            repository.updateListItem(listItemUIState.toListItem(), listId)
        }
    }
    suspend fun removeListItem(){
        repository.deleteListItem(listItemUIState.toListItem(), listId)
    }
    suspend fun deleteItem(){
        repository.deleteItem(itemUIState.toItem())
    }


    init{
        viewModelScope.launch {
            repository.getItemById(itemId).collect{
                itemUIState = it.toItemUiState()
            }
        }

        viewModelScope.launch {
            repository.getListItemById(listId, itemId).collect{
                listItemUIState = it.toListItemUIState()
            }
        }
    }
}