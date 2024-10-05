package me.cplanchet.shoppinglistassistant.ui.updatecategory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.navigation.destinations.UpdateCategoryDestination
import me.cplanchet.shoppinglistassistant.ui.state.CategoryUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryDto
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryUIState

class UpdateCategoryViewModel(
    private val shoppingListRepository: ShoppingListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val categoryId: Int = checkNotNull(savedStateHandle[UpdateCategoryDestination.categoryIdArg])
    var categoryUIState by mutableStateOf(CategoryUIState())
        private set

    init {
        viewModelScope.launch {
            shoppingListRepository.getCategoryById(categoryId).collect {
                categoryUIState = it.toCategoryUIState()
            }
        }
    }

    fun updateCategoryUIState(newState: CategoryUIState) {
        categoryUIState = newState.copy()
    }

    suspend fun saveCategory() {
        viewModelScope.launch {
            if (categoryUIState.isValid()) {
                shoppingListRepository.updateCategory(categoryUIState.toCategoryDto())
            }
        }
    }

    suspend fun deleteCategory() {
        viewModelScope.launch {
            shoppingListRepository.deleteCategory(categoryUIState.toCategoryDto())
        }
    }
}