package me.cplanchet.shoppinglistassistant.ui.listdetail

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.navigation.destinations.ListDetailDestination
import me.cplanchet.shoppinglistassistant.ui.components.move
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState

@SuppressLint("MutableCollectionMutableState")
class ListDetailViewModel(
    private val listRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _listUIState = MutableStateFlow(ListUIState())

    val listId: Int = checkNotNull(savedStateHandle[ListDetailDestination.listIdArg])
    var itemToAdd by mutableStateOf("")
    var listUIState: StateFlow<ListUIState> = _listUIState
    var sortStyle = mutableStateOf(FilterType.CUSTOM)
    val itemsUIState = listRepository.getAllItems().map {
        ListDetailUIState(it.toMutableStateList())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ListDetailUIState()
    )
    var categorizedItems: MutableState<Map<String, MutableList<ListItemDto>>> = mutableStateOf(mapOf())


    var listItems = MutableStateFlow(_listUIState.value.items.sortedBy { x -> x.order }
        .toMutableStateList())   //TODO: Get List Item UI State and pass to listItems

    init {
        viewModelScope.launch {
            listRepository.getListById(listId).collect {
                _listUIState.value = it.toListUIState()
                generateCategorizedList(it.items)
            }
        }
        viewModelScope.launch {
            listRepository.getAllListItems(listId).collect {
                _listUIState.value = _listUIState.value.copy(items = it)
                listItems.value = it.toMutableStateList()
                generateCategorizedList(it)
            }
        }
        viewModelScope.launch {
            itemsUIState.collect {
                val toAdd = it.items.find { item -> item.name.equals(itemToAdd, true) }
                if (toAdd != null) {
                    addItemToList(toAdd)
                    itemToAdd = ""
                }
                refreshItemNames()
                generateCategorizedList(listUIState.value.items)
            }
        }
    }

    suspend fun addItemToList(item: ItemDto) {
        val order =
            if (_listUIState.value.items.isNotEmpty()) _listUIState.value.items.maxOf { t -> t.order } + 1 else 1
        viewModelScope.launch {
            listRepository.addListItem(ListItemDto(item, 1f, "count", false, order), listId)
        }
    }

    suspend fun createItem(itemName: String) {
        viewModelScope.launch {
            listRepository.insertItem(ItemDto(0, itemName, null))
        }
    }

    suspend fun updateListItem(item: ListItemDto) {
        viewModelScope.launch {
            listRepository.updateListItem(item, listId)
        }
    }

    suspend fun deleteListItem(item: ListItemDto) {
        viewModelScope.launch {
            listRepository.deleteListItem(item, listId)
        }
    }

    fun swap(from: Int, to: Int) {
        listItems.value.move(from, to);
    }

    suspend fun saveItemOrder() {
        viewModelScope.launch {
            listItems.value.forEach { item ->
                updateListItem(item.copy(order = listItems.value.indexOf(item) + 1))
            }
        }
    }

    private fun refreshItemNames() {
        listUIState.value.items.forEach { item ->
            val refreshedItem = itemsUIState.value.items.find { itemDto -> itemDto.id == item.item.id }

            if (refreshedItem != null) {
                listUIState.value.items.single { listItem -> listItem.item.id == refreshedItem.id }.item = refreshedItem
            }
        }
    }

    private fun generateCategorizedList(list: List<ListItemDto>) {
        val temp: MutableMap<String, MutableList<ListItemDto>> = mutableMapOf();
        list.sortedBy { x -> x.order }.forEach { item ->
            val itemKey = item.item.category?.name ?: "";
            val categoryList = temp.get(itemKey)
            temp.set(
                itemKey,
                categoryList?.plusElement(item)?.sortedBy { x -> x.order }?.toMutableList() ?: mutableListOf(item)
            )
        }
        categorizedItems.value = temp
    }
}