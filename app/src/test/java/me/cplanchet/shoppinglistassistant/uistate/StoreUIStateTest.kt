package me.cplanchet.shoppinglistassistant.uistate

import me.cplanchet.shoppinglistassistant.data.dtos.AisleDto
import me.cplanchet.shoppinglistassistant.data.dtos.StoreDto
import me.cplanchet.shoppinglistassistant.fakes.DaoMockData
import me.cplanchet.shoppinglistassistant.ui.state.StoreUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.state.toStoreDto
import me.cplanchet.shoppinglistassistant.ui.state.toUIState
import org.junit.Assert
import org.junit.Test

class StoreUIStateTest {
    private val items = DaoMockData.allItemDtos
    private val aisle = AisleDto(1, "name", DaoMockData.allCategoryDtos, DaoMockData.allItemDtos)

    @Test
    fun storeUIState_defaultConstructor_createsUIState() {
        val expected = StoreUIState(0, "", listOf(), listOf())

        val actual = StoreUIState()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun storeUIState_constructor_creates_UIState() {
        val actual = StoreUIState(1, "name", items, listOf(aisle))

        Assert.assertEquals(1, actual.id)
        Assert.assertEquals("name", actual.name)
        Assert.assertEquals(items, actual.items)
        Assert.assertEquals(listOf(aisle), actual.aisles)
    }

    @Test
    fun toStoreDto_givenUIState_createsDto() {
        val expected = StoreDto(1, "name", items, listOf(aisle))
        val uiState = StoreUIState(1, "name", items, listOf(aisle))

        val actual = uiState.toStoreDto()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun toStoreUIState_givenDto_createsUIState() {
        val expected = StoreUIState(1, "name", items, listOf(aisle))
        val dto = StoreDto(1, "name", items, listOf(aisle))

        val actual = dto.toUIState()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun isValid_validUIState_returnsTrue() {
        val validUIState = StoreUIState(1, "name", listOf(), listOf())

        Assert.assertTrue(validUIState.isValid())
    }

    @Test
    fun isValid_missingName_returnsFalse() {
        val invalidUIState = StoreUIState(1, "", listOf(), listOf())

        Assert.assertFalse(invalidUIState.isValid())
    }

    @Test
    fun isValid_longName_returnsFalse() {
        val invalidUIState = StoreUIState(1, "loooooooooooooooooooooooongname", listOf(), listOf())

        Assert.assertFalse(invalidUIState.isValid())
    }
}