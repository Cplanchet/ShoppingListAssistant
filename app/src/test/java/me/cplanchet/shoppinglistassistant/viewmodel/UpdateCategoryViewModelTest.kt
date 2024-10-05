package me.cplanchet.shoppinglistassistant.viewmodel

import androidx.lifecycle.SavedStateHandle
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.MainDispatcherRule
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.navigation.destinations.UpdateCategoryDestination
import me.cplanchet.shoppinglistassistant.ui.state.CategoryUIState
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryDto
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryUIState
import me.cplanchet.shoppinglistassistant.ui.updatecategory.UpdateCategoryViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UpdateCategoryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var shoppingListRepository: ShoppingListRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var underTest: UpdateCategoryViewModel

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle(mapOf(UpdateCategoryDestination.categoryIdArg to 1))
        shoppingListRepository = mock<ShoppingListRepository> {
            on { getCategoryById(1) } doReturn flowOf(DaoMockData.category1Dto)
        }
        underTest = UpdateCategoryViewModel(shoppingListRepository, savedStateHandle)
    }

    @Test
    fun categoryUIState_initialized() {
        val expectedDto = DaoMockData.category1Dto
        val expected = expectedDto.toCategoryUIState()

        assertEquals(expected, underTest.categoryUIState)
    }

    @Test
    fun updateCategoryUIState_givenUIState_UpdatesViewModelState() {
        val expected = CategoryUIState(5, "testName")

        underTest.updateCategoryUIState(expected)

        assertEquals(expected, underTest.categoryUIState)
    }

    @Test
    fun saveCategory_givenValidUIState_callsRepositoryWithDto() = runTest {
        val toSave = CategoryUIState(2, "testName")
        val expected = toSave.toCategoryDto()

        underTest.updateCategoryUIState(toSave)
        underTest.saveCategory()

        verify(shoppingListRepository).updateCategory(expected)
    }

    @Test
    fun saveCategory_givenInvalidUIState_neverCallsRepository() = runTest {
        val toSave = CategoryUIState(0, "")

        underTest.updateCategoryUIState(toSave)
        underTest.saveCategory()

        verify(shoppingListRepository, never()).updateCategory(any())
    }

    @Test
    fun deleteCategory_callsRepoWithDto() = runTest {
        val toDelete = CategoryUIState(1, "name")
        val expected = toDelete.toCategoryDto()

        underTest.updateCategoryUIState(toDelete)
        underTest.deleteCategory()

        verify(shoppingListRepository).deleteCategory(expected)
    }
}