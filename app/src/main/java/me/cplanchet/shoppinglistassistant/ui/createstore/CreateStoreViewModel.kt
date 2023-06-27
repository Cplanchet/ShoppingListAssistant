package me.cplanchet.shoppinglistassistant.ui.createstore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.state.StoreUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid

class CreateStoreViewModel(private val shoppingListRepository: ShoppingListRepository): ViewModel() {
    var storeUIState by mutableStateOf(StoreUIState())
        private set

    fun updateUIState(newStoreUIState: StoreUIState){
        storeUIState = newStoreUIState.copy()
    }

    suspend fun saveStore(){
        if(storeUIState.isValid()){

        }
    }
}