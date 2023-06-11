package me.cplanchet.shoppinglistassistant.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository

class HomeViewModel(listRepository: ShoppingListRepository): ViewModel() {
    val homeUIState: StateFlow<HomeUIState> = listRepository.getAllLists().map{ HomeUIState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = HomeUIState()
    )
}