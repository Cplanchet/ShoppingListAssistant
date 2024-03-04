package me.cplanchet.shoppinglistassistant.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListUIState
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class CreateListViewModelTest {
    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createListUIState_givenFlowFromRepo_setsCreateListUIState() = runTest {
        val expectedList = DaoMockData.allStoreDtos
        val expected = CreateListUIState(expectedList)
        shoppingListRepository = mock<ShoppingListRepository> {
            on { getAllStores() } doReturn flowOf(expectedList)
        }
        val underTest = CreateListViewModel(shoppingListRepository)
        val actual = underTest.createListUIState.first()

        assertEquals(expected, actual)
    }
}