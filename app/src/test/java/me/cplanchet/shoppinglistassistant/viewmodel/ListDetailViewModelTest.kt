package me.cplanchet.shoppinglistassistant.viewmodel

import androidx.compose.runtime.toMutableStateList
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
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailUIState
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailViewModel
import me.cplanchet.shoppinglistassistant.ui.state.ItemUIState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ListDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository
    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Test
    fun itemsUIState_initializedByRepo() = runTest {
        val expectedItemList = DaoMockData.allItemDtos
        val expected = ListDetailUIState(expectedItemList.toMutableStateList())
        val shoppingListRepository = mock<ShoppingListRepository>{
            on{ getAllItems() } doReturn flowOf(expectedItemList)
        }
        val underTest = ListDetailViewModel(shoppingListRepository, savedStateHandle)

        val collect = launch(UnconfinedTestDispatcher(testScheduler)){
            underTest.itemsUIState.collect{
            }
        }

        assertEquals(expected, underTest.itemsUIState.value)
        collect.cancel()
    }
}