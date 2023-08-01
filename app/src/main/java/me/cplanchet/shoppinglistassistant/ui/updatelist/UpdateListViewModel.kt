package me.cplanchet.shoppinglistassistant.ui.updatelist

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
import me.cplanchet.shoppinglistassistant.navigation.destinations.UpdateListDestination
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toListDto
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState

class UpdateListViewModel(
    private val shoppingListRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val listId: Int = checkNotNull(savedStateHandle[UpdateListDestination.listIdArg])

    var listUIState by mutableStateOf(ListUIState())
        private set
    val updateListUIState = shoppingListRepository.getAllStores().map{ UpdateListUIState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UpdateListUIState()
        )

    fun updateListState(newListUIState: ListUIState){
        listUIState = newListUIState.copy()
    }
    suspend fun saveList(){
        if(listUIState.isValid()){
            shoppingListRepository.updateList(listUIState.toListDto())
        }
    }

    init{
        viewModelScope.launch {
            shoppingListRepository.getListById(listId).collect{
                updateListState(it.toListUIState())
            }
        }
    }
}