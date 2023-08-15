package me.cplanchet.shoppinglistassistant.ui.listdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.navigation.destinations.ListDetailDestination
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState

class ListDetailViewModel(
    private val listRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _listUIState = MutableStateFlow(ListUIState())
    val listId: Int = checkNotNull(savedStateHandle[ListDetailDestination.listIdArg])
    var itemToAdd by mutableStateOf("")
    var listUIState:StateFlow<ListUIState> = _listUIState
    val itemsUIState = listRepository.getAllItems().map{
        ListDetailUIState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ListDetailUIState()
    )
    init {
        viewModelScope.launch{
            listRepository.getListById(listId).collect{
                _listUIState.value = it.toListUIState()
            }
        }
        viewModelScope.launch{
            listRepository.getAllListItems(listId).collect{
                _listUIState.value = _listUIState.value.copy(items = it)    //Keep track of updates in list items
            }
        }
        viewModelScope.launch {
            itemsUIState.collect{
                val toAdd = it.items.find { item -> item.name.equals(itemToAdd, true) }
                if(toAdd != null){
                    addItemToList(toAdd)
                    itemToAdd = ""
                }
                refreshItemNames()
            }
        }
    }
    suspend fun addItemToList(item: ItemDto){
        viewModelScope.launch{
            listRepository.addListItem(ListItemDto(item, 1f, "count", false), listId)
        }
    }

    suspend fun createItem(itemName: String){
        viewModelScope.launch {
            listRepository.insertItem(ItemDto(0, itemName, null))
        }
    }

    suspend fun updateListItem(item: ListItemDto){
        viewModelScope.launch {
            listRepository.updateListItem(item, listId)
        }
    }

    suspend fun deleteListItem(item: ListItemDto){
        viewModelScope.launch {
            listRepository.deleteListItem(item, listId)
        }
    }

    private fun refreshItemNames(){
        listUIState.value.items.forEach { item ->
            val refreshedItem = itemsUIState.value.items.find { itemDto -> itemDto.id == item.item.id }

            if (refreshedItem != null) {
                listUIState.value.items.single{listItem -> listItem.item.id == refreshedItem.id}.item = refreshedItem
            }
        }
    }
}