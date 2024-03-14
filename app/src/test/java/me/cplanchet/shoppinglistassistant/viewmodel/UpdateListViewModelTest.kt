package me.cplanchet.shoppinglistassistant.viewmodel

import androidx.lifecycle.SavedStateHandle
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.MainDispatcherRule
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState
import me.cplanchet.shoppinglistassistant.ui.updatelist.UpdateListUIState
import me.cplanchet.shoppinglistassistant.ui.updatelist.UpdateListViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UpdateListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var underTest: UpdateListViewModel

    @Before
    fun setup(){
        savedStateHandle = SavedStateHandle(mapOf("listId" to 1))
        shoppingListRepository = mock<ShoppingListRepository>{
            on { getListById(1) } doReturn flowOf(DaoMockData.shoppingList1Dto)
            on { getAllStores() } doReturn flowOf(DaoMockData.allStoreDtos)
        }
        underTest = UpdateListViewModel(shoppingListRepository, savedStateHandle)
    }

    @Test
    fun listUIState_initializedByRepo(){
        val expectedList = DaoMockData.shoppingList1Dto
        val expected = expectedList.toListUIState()

        assertEquals(expected, underTest.listUIState)
    }

    @Test
    fun updateListUIState_initializedByRepo() = runTest {
        val expectedStoreList = DaoMockData.allStoreDtos
        val expected = UpdateListUIState(expectedStoreList)

        val collect = launch(UnconfinedTestDispatcher(testScheduler)) {
            underTest.updateListUIState.collect{}
        }

        assertEquals(expected, underTest.updateListUIState.value)
        collect.cancel()
    }

    @Test
    fun updateListState_givenListUIState_updatesViewModelListUIState(){
        val expected = ListUIState(1, "newName", DaoMockData.allListItemDtosList2, DaoMockData.store2Dto)

        underTest.updateListState(expected)

        assertEquals(expected, underTest.listUIState)
    }

    @Test
    fun saveList_givenValidUIState_callsRepoWithDto() = runTest {
        val expectedDto = DaoMockData.shoppingList1Dto

        underTest.saveList()

        verify(shoppingListRepository).updateList(expectedDto)
    }

    @Test
    fun saveList_givenInvalidListUIState_neverCallsRepo() = runTest{
        val invalidState = ListUIState(1, "", listOf(), null)

        underTest.updateListState(invalidState)
        underTest.saveList()

        verify(shoppingListRepository, never()).updateList(any())
    }
}