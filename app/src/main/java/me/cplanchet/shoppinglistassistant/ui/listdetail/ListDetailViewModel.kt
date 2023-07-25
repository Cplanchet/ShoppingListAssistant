package me.cplanchet.shoppinglistassistant.ui.listdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.navigation.destinations.ListDetailDestination
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState

class ListDetailViewModel(
    listRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val listId: Int = checkNotNull(savedStateHandle[ListDetailDestination.listIdArg])

    val uiState: StateFlow<ListUIState> = listRepository.getListById(listId)
        .filterNotNull()
        .map {
            it.toListUIState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ListUIState()
        )
}