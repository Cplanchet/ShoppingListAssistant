package me.cplanchet.shoppinglistassistant.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.AisleDto
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.createstore.CreateStoreViewModel
import me.cplanchet.shoppinglistassistant.ui.state.StoreUIState
import me.cplanchet.shoppinglistassistant.ui.state.toStoreDto
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CreateStoreViewModelTest {
    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository

    private val items = DaoMockData.allItemDtos
    private val aisles = listOf(AisleDto(1, "Name", listOf(), listOf()))

    @Test
    fun updateUIState_givenAStoreUIState_UpdatesTheState() {
        val underTest = CreateStoreViewModel(shoppingListRepository)
        val newStoreUIState = StoreUIState(1, "name", items, aisles)

        underTest.updateUIState(newStoreUIState.copy())

        assertEquals(newStoreUIState, underTest.storeUIState)
    }

    @Test
    fun saveStore_givenValidUIState_callsRepository() = runTest {
        val underTest = CreateStoreViewModel(shoppingListRepository)
        val validUIState = StoreUIState(1, "name", items, aisles)

        underTest.updateUIState(validUIState)
        underTest.saveStore()

        verify(shoppingListRepository).insertStore(validUIState.toStoreDto())
    }

    @Test
    fun saveStore_givenInvalidUIState_neverCallsRepository() = runTest {
        val underTest = CreateStoreViewModel(shoppingListRepository)
        val invalidUIState = StoreUIState(0, "", listOf(), listOf())

        underTest.updateUIState(invalidUIState)
        underTest.saveStore()

        verify(shoppingListRepository, never()).insertList(any())
    }
}