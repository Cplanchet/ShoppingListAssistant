package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cplanchet.shoppinglistassistant.data.entities.Category
import me.cplanchet.shoppinglistassistant.data.entities.Item
import me.cplanchet.shoppinglistassistant.data.entities.ListItem
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

class ManualShoppingListRepository : ShoppingListRepository{
    override fun GetAllLists(): Flow<List<ShoppingList>> {
        val category = Category(1, "cat 1")
        val item = Item(1, "item1", category)
        val listItems = listOf(
            ListItem(item, 1f, "count", false),
            ListItem(item, 3f, "lb", false),
        )
        val shoppingList = listOf(ShoppingList(1, "name", listItems, null), ShoppingList(2, "name2", listItems, null))
        return flowOf(shoppingList)
    }
}