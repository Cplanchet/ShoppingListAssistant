package me.cplanchet.shoppinglistassistant.uistate

import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.state.ItemUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toItem
import me.cplanchet.shoppinglistassistant.ui.state.toItemUiState
import org.junit.Assert.*
import org.junit.Test

class ItemUIStateTest {

    private val category = DaoMockData.category1Dto
    @Test
    fun ItemUIState_defaultConstructor_createsADefaultItemUIState(){
        val expected = ItemUIState(0, "", null)

        val actual = ItemUIState()

        assertEquals(expected, actual)
    }
    @Test
    fun ItemUIState_constructor_createsItemUIStateCorrectly(){
        val actual = ItemUIState(1, "name", category)

        assertEquals(1, actual.id)
        assertEquals("name", actual.name)
        assertEquals(category, actual.category)
    }

    @Test
    fun toItem_convertsUIStateToDto(){
        val expected = ItemDto(1, "name", category)
        val UIState = ItemUIState(1, "name", category)

        val actual = UIState.toItem()

        assertEquals(expected, actual)
    }

    @Test
    fun toItemUIState_convertsDtoToUIState(){
        val expected = ItemUIState(1, "name", category)
        val dto = ItemDto(1, "name", category)

        val actual = dto.toItemUiState()

        assertEquals(expected, actual)
    }

    @Test
    fun isValid_validUIState_returnsTrue(){
        val uiState = ItemUIState(1, "name", null)

        assertTrue(uiState.isValid())
    }

    @Test
    fun isValid_invalidUIState_returnsFalse(){
        val uiState = ItemUIState(1, "", null)

        assertFalse(uiState.isValid())
    }
}