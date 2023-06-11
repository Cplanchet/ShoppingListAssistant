package me.cplanchet.shoppinglistassistant.mock

import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.data.entities.Category
import me.cplanchet.shoppinglistassistant.data.entities.Item
import me.cplanchet.shoppinglistassistant.data.entities.ListItem
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

object MockData {
    val category: CategoryDto = CategoryDto(1, "fakeCategory")

    val item1 = ItemDto(1, "fake item 1", category)
    val item2 = ItemDto(2, "fake item 2", category)
    val item3 = ItemDto(3, "fake item 3", category)
    val item4 = ItemDto(4, "fake item 4", category)

    val listItem1 = ListItemDto(item1, 1f, "count", false)
    val listItem2 = ListItemDto(item2, 1f, "count", false)
    val listItem3 = ListItemDto(item3, 1f, "count", false)
    val listItem4 = ListItemDto(item4, 1f, "count", false)   //TODO: convert to mock repo data

    val shoppingListNoItems = ShoppingListDto(1, "No Items", listOf(), null)
    val shoppingListOneItem = ShoppingListDto(2, "One Item", listOf(listItem1), null)
    val shoppingListThreeItems = ShoppingListDto(2, "Three Items", listOf(listItem1, listItem2, listItem3), null)
    val shoppingListFourItems = ShoppingListDto(2, "Four Items", listOf(listItem1, listItem2, listItem3, listItem4), null)
}