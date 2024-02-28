package me.cplanchet.shoppinglistassistant.ui.createcategory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.state.CategoryUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryDto

class CreateCategoryViewModel(private val shoppingListRepository: ShoppingListRepository): ViewModel() {
    var categoryUIState by mutableStateOf(CategoryUIState())
        private set

    fun updateUIState(newCategoryUIState: CategoryUIState){
        categoryUIState = newCategoryUIState.copy()
    }

    suspend fun saveCategory(){
        if(categoryUIState.isValid()){
            shoppingListRepository.insertCategory(categoryUIState.toCategoryDto())
        }
    }
}