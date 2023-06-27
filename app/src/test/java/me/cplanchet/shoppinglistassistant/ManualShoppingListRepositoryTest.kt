package me.cplanchet.shoppinglistassistant

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import org.junit.Test

class ManualShoppingListRepositoryTest {
    val repository = MockShoppingListRepository()
    val category = CategoryDto(1, "cat 1")
    val item = ItemDto(1, "item1", category)
    val listItems = listOf(
        ListItemDto(item, 1f, "count", false),
        ListItemDto(item, 3f, "lb", false),
    )
    val shoppingList1 = ShoppingListDto(1, "name", listItems, null)

    @Test
    fun GetAllLists_called_returnsLists() = runBlocking{
        val items = repository.getAllLists();

        assertEquals(shoppingList1, items.first()[0])
        assertEquals(2, items.first().size);
    }
}