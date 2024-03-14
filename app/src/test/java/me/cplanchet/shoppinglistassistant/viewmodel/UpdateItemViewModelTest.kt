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
import me.cplanchet.shoppinglistassistant.ui.state.*
import me.cplanchet.shoppinglistassistant.ui.updateitem.UpdateItemUIState
import me.cplanchet.shoppinglistassistant.ui.updateitem.UpdateItemViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UpdateItemViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var underTest: UpdateItemViewModel

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle(mapOf("listId" to 1, "itemId" to 1))
        shoppingListRepository = mock<ShoppingListRepository> {
            on { getItemById(1) } doReturn flowOf(DaoMockData.item1Dto)
            on { getListItemById(1, 1) } doReturn flowOf(DaoMockData.listItem1Dto)
            on { getAllCategories() } doReturn flowOf(DaoMockData.allCategoryDtos)
        }
        underTest = UpdateItemViewModel(shoppingListRepository, savedStateHandle)
    }

    @Test
    fun itemUIState_givenListIdAndItemIdFromSaveState_isInitializedByRepo() {
        val expectedItem = DaoMockData.item1Dto
        val expected = expectedItem.toItemUiState()

        assertEquals(expected, underTest.itemUIState)
    }

    @Test
    fun listItemUIState_givenListIdAndItemIdFromSaveState_isInitializedByRepo() {
        val expectedList = DaoMockData.listItem1Dto
        val expected = expectedList.toListItemUIState()

        assertEquals(expected, underTest.listItemUIState)
    }

    @Test
    fun categoryUIState_isInitializedByRepo() = runTest {
        val expectedCategoryList = DaoMockData.allCategoryDtos
        val expected = UpdateItemUIState(expectedCategoryList)

        val collect = launch(UnconfinedTestDispatcher(testScheduler)) {
            underTest.categoryUIState.collect {
            }
        }

        assertEquals(expected, underTest.categoryUIState.value)
        collect.cancel()
    }

    @Test
    fun updateItemUIState_givenItemUIState_UpdatesViewModelItemUIState() {
        val toUpdate = ItemUIState(5, "newName", DaoMockData.category1Dto)

        underTest.updateItemUIState(toUpdate)

        assertEquals(toUpdate, underTest.itemUIState)
    }

    @Test
    fun updateListItemUIState_givenListItemUIState_UpdatesViewModelListItemUIState() {
        val toUpdate = ListItemUIState(DaoMockData.item1Dto, 3f, "newUnit", true, 12)

        underTest.updateListItemUIState(toUpdate)

        assertEquals(toUpdate, underTest.listItemUIState)
    }

    @Test
    fun saveItem_givenValidUIState_callsRepoWithDto() = runTest {
        val validUIState = ItemUIState(1, "name", DaoMockData.category1Dto)

        underTest.updateItemUIState(validUIState)
        underTest.saveItem()

        verify(shoppingListRepository).updateItem(validUIState.toItem())
    }

    @Test
    fun saveItem_givenInvalidUIState_neverCallsRepo() = runTest {
        val invalidUIState = ItemUIState(1, "", null)

        underTest.updateItemUIState(invalidUIState)
        underTest.saveItem()

        verify(shoppingListRepository, never()).updateItem(any())
    }

    @Test
    fun saveListItem_givenValidUIState_callsRepoWithDto() = runTest {
        val validUIState = ListItemUIState(DaoMockData.item1Dto, 1f, "count", false, 1)

        underTest.updateListItemUIState(validUIState)
        underTest.saveListItem()

        verify(shoppingListRepository).updateListItem(validUIState.toListItem(), 1)
    }

    @Test
    fun saveListItem_givenInvalidUIState_neverCallsRepo() = runTest {
        val invalidUIState = ListItemUIState(DaoMockData.item1Dto, 0f, "0", false, 0)

        underTest.updateListItemUIState(invalidUIState)
        underTest.saveListItem()

        verify(shoppingListRepository, never()).updateListItem(any(), any())
    }

    @Test
    fun removeListItem_callsRepoWithDto() = runTest {
        val toDelete = ListItemUIState(DaoMockData.item1Dto, 1f, "count", false, 1)

        underTest.updateListItemUIState(toDelete)
        underTest.removeListItem()

        verify(shoppingListRepository).deleteListItem(toDelete.toListItem(), 1)
    }

    @Test
    fun deleteItem_callsRepoWithDto() = runTest {
        val toDelete = ItemUIState(1, "name", DaoMockData.category1Dto)

        underTest.updateItemUIState(toDelete)
        underTest.deleteItem()

        verify(shoppingListRepository).deleteItem(toDelete.toItem())
    }
}