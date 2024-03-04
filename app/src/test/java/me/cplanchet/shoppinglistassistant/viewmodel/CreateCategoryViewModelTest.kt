package me.cplanchet.shoppinglistassistant.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.createcategory.CreateCategoryViewModel
import me.cplanchet.shoppinglistassistant.ui.state.CategoryUIState
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryDto
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CreateCategoryViewModelTest() {
    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository
    @Test
    fun updateUIState_givenUIState_UpdatesUIState(){
        val underTest = CreateCategoryViewModel(shoppingListRepository)
        val toUpdate = CategoryUIState(4, "Name")

        underTest.updateUIState(toUpdate)

        assertEquals(toUpdate, underTest.categoryUIState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveCategory_validUIState_callsRepositoryMethod() = runTest {
        val toSave = CategoryUIState(1, "name")
        val underTest = CreateCategoryViewModel(shoppingListRepository)

        underTest.updateUIState(toSave)
        underTest.saveCategory()

        verify(shoppingListRepository).insertCategory(toSave.toCategoryDto())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveCategory_invalidUIState_doesNotCallRepository() = runTest {
        val toSave = CategoryUIState(0, "")
        val underTest = CreateCategoryViewModel(shoppingListRepository)

        underTest.updateUIState(toSave)
        underTest.saveCategory()

        verify(shoppingListRepository, never()).insertCategory(toSave.toCategoryDto())
    }
}