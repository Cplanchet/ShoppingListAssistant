package me.cplanchet.shoppinglistassistant.ui.listdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository

class ListDetailViewModel(private val listRepository: ShoppingListRepository): ViewModel() {
    val listDetailUiState: StateFlow<ListDetailUiState> = listRepository.getAllLists().map{ ListDetailUiState(it)}.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ListDetailUiState()
    )
}