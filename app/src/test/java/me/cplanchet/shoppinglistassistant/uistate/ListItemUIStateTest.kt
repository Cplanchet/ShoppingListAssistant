package me.cplanchet.shoppinglistassistant.uistate

import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.state.ListItemUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toListItem
import me.cplanchet.shoppinglistassistant.ui.state.toListItemUIState
import org.junit.Assert.*
import org.junit.Test

class ListItemUIStateTest {
    private val itemDto = DaoMockData.item1Dto

    @Test
    fun listItemUIState_defaultConstructor_createsDefaultUIState(){
        val expected = ListItemUIState(ItemDto(), 0f, "", false, 0)

        val actual = ListItemUIState()

        assertEquals(expected, actual)
    }

    @Test
    fun listItemUIState_constructor_createsUIState(){
        val actual = ListItemUIState(itemDto, 1f, "count", true, 4)

        assertEquals(itemDto, actual.item)
        assertEquals(1f, actual.amount)
        assertEquals("count", actual.amountUnit)
        assertEquals(true, actual.checked)
        assertEquals(4, actual.order)
    }

    @Test
    fun toListItemUIState_givenDto_convertsToUIState() {
        val expected = ListItemUIState(itemDto, 1f, "count", true, 4)
        val dto = ListItemDto(itemDto, 1f, "count", true, 4)

        val actual = dto.toListItemUIState()

        assertEquals(expected, actual)
    }

    @Test
    fun toListItem_givenUIState_convertsToDto() {
        val expected = ListItemDto(itemDto, 1f, "count", true, 4)
        val uiState = ListItemUIState(itemDto, 1f, "count", true, 4)

        val actual = uiState.toListItem()

        assertEquals(expected, actual)
    }

    @Test
    fun isValid_validUIState_returnsTrue(){
        val validUIState = ListItemUIState(itemDto, 1f, "count", true, 4)

        assertTrue(validUIState.isValid())
    }

    @Test
    fun isValid_amountZero_returnsFalse(){
        val validUIState = ListItemUIState(itemDto, 0f, "count", true, 4)

        assertFalse(validUIState.isValid())
    }

    @Test
    fun isValid_amountUnitEmpty_returnsFalse(){
        val validUIState = ListItemUIState(itemDto, 1f, "", true, 4)

        assertFalse(validUIState.isValid())
    }

    @Test
    fun isValid_orderZero_returnsFalse(){
        val validUIState = ListItemUIState(itemDto, 1f, "count", true, 0)

        assertFalse(validUIState.isValid())
    }
}