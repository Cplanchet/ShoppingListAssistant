package me.cplanchet.shoppinglistassistant

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.cplanchet.shoppinglistassistant.data.ManualShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.entities.Category
import me.cplanchet.shoppinglistassistant.data.entities.Item
import me.cplanchet.shoppinglistassistant.data.entities.ListItem
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList
import org.junit.Test

class ManualShoppingListRepositoryTest {
    val repository = ManualShoppingListRepository()
    val category = Category(1, "cat 1")
    val item = Item(1, "item1", category)
    val listItems = listOf(
        ListItem(item, 1f, "count", false),
        ListItem(item, 3f, "lb", false),
    )
    val shoppingList1 = ShoppingList(1, "name", listItems, null)

    @Test
    fun GetAllLists_called_returnsLists() = runBlocking{
        val items = repository.GetAllLists();

        assertEquals(shoppingList1, items.first()[0])
        assertEquals(2, items.first().size);
    }
}