package me.cplanchet.shoppinglistassistant.uistate

import junit.framework.TestCase.*
import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto
import me.cplanchet.shoppinglistassistant.ui.state.CategoryUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryDto
import me.cplanchet.shoppinglistassistant.ui.state.toCategoryUIState
import org.junit.Test

class CategoryUIStateTest {
    @Test
    fun categoryUIState_defaultConstructor_createsDefaultUIState() {
        val expected = CategoryUIState(0, "")
        val actual = CategoryUIState()

        assertEquals(expected, actual)
    }

    @Test
    fun categoryUIState_constructorWithArguments_createsUIState() {
        val actual = CategoryUIState(1, "4")

        assertEquals(1, actual.id)
        assertEquals("4", actual.name)
    }

    @Test
    fun toCategoryDto_convertsToDto() {
        val expected = CategoryDto(1, "name")
        val uiState = CategoryUIState(1, "name")

        val actual = uiState.toCategoryDto()

        assertEquals(expected, actual)
    }

    @Test
    fun toCategoryUIState_convertsDtoToUIState() {
        val expected = CategoryUIState(1, "name")
        val dto = CategoryDto(1, "name")

        val actual = dto.toCategoryUIState()

        assertEquals(expected, actual)
    }

    @Test
    fun isValid_validUIState_returnsTrue() {
        val validUiState = CategoryUIState(1, "name")

        assertTrue(validUiState.isValid())
    }

    @Test
    fun isValid_invalidUIState_returnsFalse() {
        val validUiState = CategoryUIState(1, "")

        assertFalse(validUiState.isValid())
    }
}