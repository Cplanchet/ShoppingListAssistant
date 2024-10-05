package me.cplanchet.shoppinglistassistant.viewmodel

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.MainDispatcherRule
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.createcategory.CreateCategoryViewModel
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListUIState
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListViewModel
import me.cplanchet.shoppinglistassistant.ui.state.CategoryUIState
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryDto
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CreateCategoryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository

    @Test
    fun createListUIState_generatesFromRepository() = runTest {
        val expectedStoreList = DaoMockData.allStoreDtos
        val expected = CreateListUIState(expectedStoreList)
        val shoppingListRepository = mock<ShoppingListRepository> {
            on { getAllStores() } doReturn flow { emit(expectedStoreList) }
        }

        val underTest = CreateListViewModel(shoppingListRepository)

        val collect = launch(UnconfinedTestDispatcher(testScheduler)) {
            underTest.createListUIState.collect {
            }
        }
        assertEquals(expected, underTest.createListUIState.value)
        collect.cancel()
    }

    @Test
    fun updateUIState_givenUIState_UpdatesUIState() {
        val underTest = CreateCategoryViewModel(shoppingListRepository)
        val toUpdate = CategoryUIState(4, "Name")

        underTest.updateUIState(toUpdate)

        assertEquals(toUpdate, underTest.categoryUIState)
    }

    @Test
    fun saveCategory_validUIState_callsRepositoryMethod() = runTest {
        val toSave = CategoryUIState(1, "name")
        val underTest = CreateCategoryViewModel(shoppingListRepository)

        underTest.updateUIState(toSave)
        underTest.saveCategory()

        verify(shoppingListRepository).insertCategory(toSave.toCategoryDto())
    }

    @Test
    fun saveCategory_invalidUIState_doesNotCallRepository() = runTest {
        val toSave = CategoryUIState(0, "")
        val underTest = CreateCategoryViewModel(shoppingListRepository)

        underTest.updateUIState(toSave)
        underTest.saveCategory()

        verify(shoppingListRepository, never()).insertCategory(toSave.toCategoryDto())
    }
}