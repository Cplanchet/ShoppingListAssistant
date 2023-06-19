package me.cplanchet.shoppinglistassistant.ui.createlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import me.cplanchet.shoppinglistassistant.data.DefaultShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.ListUIState
import me.cplanchet.shoppinglistassistant.ui.isValid
import me.cplanchet.shoppinglistassistant.ui.toListDto

class CreateListViewModel(private val shoppingListRepository: DefaultShoppingListRepository): ViewModel() {
    var listUIState by mutableStateOf(ListUIState())
        private set

    fun updateUiState(newListUIState: ListUIState){
        listUIState = newListUIState.copy()
    }

    suspend fun saveList(){
        if(listUIState.isValid()){
            shoppingListRepository.insertList(listUIState.toListDto())
        }
    }
}