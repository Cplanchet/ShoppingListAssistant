package me.cplanchet.shoppinglistassistant.uistate

import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toListDto
import me.cplanchet.shoppinglistassistant.ui.state.toListUIState
import org.junit.Assert.*
import org.junit.Test

class ListUIStateTest {
    private val listItems = DaoMockData.allListItemDtosList1
    private val store = DaoMockData.store1Dto

    @Test
    fun listUIState_defaultConstructor_createsUIState() {
        val expected = ListUIState(0, "", ArrayList(), null)

        val actual = ListUIState()

        assertEquals(expected, actual)
    }

    @Test
    fun listUIState_constructor_creates_UIState() {
        val actual = ListUIState(1, "name", listItems, store)

        assertEquals(1, actual.id)
        assertEquals("name", actual.name)
        assertEquals(listItems, actual.items)
        assertEquals(store, actual.store)
    }

    @Test
    fun toListDto_givenUIState_createsDto() {
        val expected = ShoppingListDto(1, "name", listItems, store)
        val uiState = ListUIState(1, "name", listItems, store)

        val actual = uiState.toListDto()

        assertEquals(expected, actual)
    }

    @Test
    fun toListUIState_givenDto_createsUIState() {
        val expected = ListUIState(1, "name", listItems, store)
        val dto = ShoppingListDto(1, "name", listItems, store)

        val actual = dto.toListUIState()

        assertEquals(expected, actual)
    }

    @Test
    fun isValid_validUIState_returnsTrue() {
        val validUIState = ListUIState(1, "name", ArrayList(), null)

        assertTrue(validUIState.isValid())
    }

    @Test
    fun isValid_missingName_returnsFalse() {
        val invalidUIState = ListUIState(1, "", ArrayList(), null)

        assertFalse(invalidUIState.isValid())
    }

    @Test
    fun isValid_longName_returnsFalse() {
        val invalidUIState = ListUIState(1, "loooooooooooooooooooooooongname", ArrayList(), null)

        assertFalse(invalidUIState.isValid())
    }
}