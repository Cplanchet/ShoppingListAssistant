package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cplanchet.shoppinglistassistant.data.entities.Category
import me.cplanchet.shoppinglistassistant.data.entities.Item
import me.cplanchet.shoppinglistassistant.data.entities.ListItem
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

class MockShoppingListRepository : ShoppingListRepository{
    override fun GetAllLists(): Flow<List<ShoppingList>> {
        val category = Category(1, "cat 1")
        val item1 = Item(1, "item1", category)
        val item2 = Item(2, "item2", category)
        val item3 = Item(3, "item3", category)
        val item4 = Item(4, "item4", category)

        val listItems = listOf(
            ListItem(item1, 1f, "count", false),
            ListItem(item2, 3f, "lb", false),
            ListItem(item3, 3f, "lb", false),
            ListItem(item4, 3f, "lb", false),
        )
        val listItems2 = listOf(
            ListItem(item1, 1f, "count", false),
            ListItem(item2, 3f, "count", false),
            ListItem(item3, 1f, "count", true)
        )
        val listItems3 = listOf(
            ListItem(item1, 1f, "count", false),
            ListItem(item2, 3f, "count", false),
        )
        val shoppingList1 = ShoppingList(1, "name", listItems, null)
        val shoppingList2 = ShoppingList(2, "name2", listItems2, null)
        val shoppingList3 = ShoppingList(3, "name3", listItems3, null)
        val shoppingList4 = ShoppingList(4, "name4", listOf<ListItem>(), null)
        val shoppingLists = listOf(
            shoppingList1,
            shoppingList2,
            shoppingList3,
            shoppingList4
        )
        return flowOf(shoppingLists)
    }
}