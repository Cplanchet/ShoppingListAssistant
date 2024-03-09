package me.cplanchet.shoppinglistassistant.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.MainDispatcherRule
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.home.HomeUIState
import me.cplanchet.shoppinglistassistant.ui.home.HomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository

    @Test
    fun homeUIState_initializedFromRepo() = runTest {
        val expectedLists = DaoMockData.allListDtos
        val expected = HomeUIState(expectedLists)
        val shoppingListRepository = mock<ShoppingListRepository>{
            on{ getAllLists() } doReturn flowOf(expectedLists)
        }

        val underTest = HomeViewModel(shoppingListRepository)

        val collect = launch(UnconfinedTestDispatcher(testScheduler)){
            underTest.homeUIState.collect{
            }
        }
        assertEquals(expected, underTest.homeUIState.value)
        collect.cancel()
    }

    @Test
    fun deleteList_givenADto_callsRepoWithDto() = runTest{
        val toDelete = DaoMockData.shoppingList1Dto
        val underTest = HomeViewModel(shoppingListRepository)

        underTest.deleteList(toDelete)
        verify(shoppingListRepository).deleteList(toDelete)
    }
}