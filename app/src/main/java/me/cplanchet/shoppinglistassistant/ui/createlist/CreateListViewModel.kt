package me.cplanchet.shoppinglistassistant.ui.createlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toListDto

class CreateListViewModel(private val shoppingListRepository: ShoppingListRepository) : ViewModel() {
    var listUIState by mutableStateOf(ListUIState())
        private set

    val createListUIState: StateFlow<CreateListUIState> =
        shoppingListRepository.getAllStores().map { CreateListUIState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateListUIState()
        )

    fun updateUIState(newListUIState: ListUIState) {
        listUIState = newListUIState.copy()
    }

    suspend fun saveList() {
        if (listUIState.isValid()) {
            shoppingListRepository.insertList(listUIState.toListDto())
        }
    }
}