package me.cplanchet.shoppinglistassistant.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.MainDispatcherRule
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailUIState
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailViewModel
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ListDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var underTest: ListDetailViewModel


    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle(mapOf("listId" to 1))
        shoppingListRepository = mock<ShoppingListRepository> {
            on { getAllItems() } doReturn flowOf(DaoMockData.allItemDtos)
            on { getListById(1) } doReturn flowOf(DaoMockData.shoppingList1Dto)
            on { getAllListItems(1) } doReturn flowOf(DaoMockData.allListItemDtosList1)
        }
        underTest = ListDetailViewModel(shoppingListRepository, savedStateHandle)
    }

    @Test
    fun itemsUIState_initializedByRepo() = runTest {
        val expectedItemList = DaoMockData.allItemDtos
        val expected = ListDetailUIState(expectedItemList.toMutableStateList())

        val collect = launch(UnconfinedTestDispatcher(testScheduler)) {
            underTest.itemsUIState.collect {
            }
        }

        assertEquals(expected.items.size, underTest.itemsUIState.value.items.size)
        collect.cancel()
    }

    @Test
    fun listUiState_initializedByRepo() = runTest {
        val expectedList = DaoMockData.shoppingList1Dto
        val expected = expectedList.toListUIState()

        val collect = launch(UnconfinedTestDispatcher(testScheduler)) {
            underTest.listUIState.collect()
        }

        assertEquals(expected, underTest.listUIState.value)
        collect.cancel()
    }

    @Test
    fun listId_isSetBySavedStateHandle() {
        val expected = 1

        assertEquals(expected, underTest.listId)
    }

    @Test
    fun addItemToList_givenAnItemDto_callsRepoAndSetsOrderAndDefaultValues() = runTest {
        val toAdd = ItemDto(10, "New Item")
        val expected = ListItemDto(toAdd, 1f, "count", false, 3)

        underTest.addItemToList(toAdd)

        verify(shoppingListRepository).addListItem(expected, 1)
    }

    @Test
    fun addItemToList_givenAnItemDtoForEmptyList_callsRepoAndSetsOrderToOne() = runTest {
        shoppingListRepository = mock<ShoppingListRepository> {
            on { getAllItems() } doReturn flowOf(DaoMockData.allItemDtos)
            on { getListById(1) } doReturn flowOf(ShoppingListDto(1, "emptyList", listOf(), null))
            on { getAllListItems(1) } doReturn flowOf(listOf())
        }
        val alternate = ListDetailViewModel(shoppingListRepository, savedStateHandle)
        val toAdd = ItemDto(10, "New Item")
        val expected = ListItemDto(toAdd, 1f, "count", false, 1)

        alternate.addItemToList(toAdd)

        verify(shoppingListRepository).addListItem(expected, 1)
    }

    @Test
    fun createItem_givenAName_createsAnItemDtoWithName() = runTest {
        val expected = ItemDto(0, "testName", null)
        val toCreateName = "testName"

        underTest.createItem(toCreateName)

        verify(shoppingListRepository).insertItem(expected)
    }

    @Test
    fun updateListItem_givenNewListItemDto_callsRepoToUpdateItem() = runTest {
        val toUpdate = ListItemDto(ItemDto(1, "newName"), 12f, "pounds", true, 3)

        underTest.updateListItem(toUpdate)

        verify(shoppingListRepository).updateListItem(toUpdate, 1)
    }

    @Test
    fun deleteListItem_givenListItemDto_callsRepo() = runTest {
        val toDelete = DaoMockData.listItem1Dto

        underTest.deleteListItem(toDelete)

        verify(shoppingListRepository).deleteListItem(toDelete, 1)
    }

    @Test
    fun swap_givenTwoIndexes_changesOrderOfMutableList() = runTest {
        val from = 1
        val to = 0
        val expected = mutableListOf(DaoMockData.listItem2Dto, DaoMockData.listItem1Dto)

        underTest.swap(from, to)

        assertEquals(underTest.listItems.value.toList(), expected)
    }

    @Test
    fun saveItemOrder_callsRepoToSaveNewOrder() = runTest {
        underTest.saveItemOrder()
        verify(shoppingListRepository, times(underTest.listItems.value.size)).updateListItem(any(), any())
    }

    @Test
    fun categorizedList_initializedByRepo() = runTest {
        val expected = DaoMockData.categorizedList

        val collect = launch(UnconfinedTestDispatcher(testScheduler)) {
            underTest.listItems.collect {
            }
        }

        assertEquals(expected, underTest.categorizedItems.value)
        collect.cancel()
    }
}