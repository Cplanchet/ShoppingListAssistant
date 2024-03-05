package me.cplanchet.shoppinglistassistant.viewmodel

import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListViewModel
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.toListDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class CreateListViewModelTest {
    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository

    @Test
    fun updateUIState_givenListUIState_updatesState(){
        val underTest = CreateListViewModel(shoppingListRepository)
        val toUpdate = ListUIState(1, "name", listOf(), null)
        assertNotEquals(toUpdate, underTest.listUIState)

        underTest.updateUIState(toUpdate)

        assertEquals(toUpdate, underTest.listUIState)
    }

    @Test
    fun saveList_validUIState_callsRepo() = runTest {
        val underTest = CreateListViewModel(shoppingListRepository)
        val validState = ListUIState(1, "Name", listOf(), DaoMockData.store1Dto)

        underTest.updateUIState(validState)
        underTest.saveList()

        verify(shoppingListRepository).insertList(validState.toListDto())
    }

    @Test
    fun saveList_invalidUIState_neverCallsRepo() = runTest {
        val underTest = CreateListViewModel(shoppingListRepository)
        val invalidState = ListUIState(1, "", listOf(), null)

        underTest.updateUIState(invalidState)
        underTest.saveList()

        verify(shoppingListRepository, never()).insertList(invalidState.toListDto())
    }
}