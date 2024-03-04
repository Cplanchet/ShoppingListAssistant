package me.cplanchet.shoppinglistassistant.fakes

import me.cplanchet.shoppinglistassistant.data.dtos.*
import me.cplanchet.shoppinglistassistant.data.entities.*

object DaoMockData {
    val store1 = Store(1, "store1")
    val store2 = Store(2, "store2")
    val allStores = listOf(store1, store2)

    val store1Dto = StoreDto(1, "store1")
    val store2Dto = StoreDto(2, "store2")
    val allStoreDtos = listOf(store1Dto, store2Dto)

    val category1 = Category(1, "category1")
    val category2 = Category(2, "category2")
    val allCategories = listOf(category1, category2)

    val category1Dto = CategoryDto(1, "category1")
    val category2Dto = CategoryDto(2, "category2")
    val allCategoryDtos = listOf(category1Dto, category2Dto)

    val item1 = Item(1, "item no category", null)
    val item2 = Item(2, "item with category", 1)
    val item3 = Item(3, "item3", null)
    val item4 = Item(4, "item4", 1)
    val allItems = listOf(item1, item2, item3, item4)

    val item1Dto = ItemDto(1, "item no category", null)
    val item2Dto = ItemDto(2, "item with category", category1Dto)
    val item3Dto = ItemDto(3, "item3", null)
    val item4Dto = ItemDto(4, "item4", category1Dto)
    val allItemDtos = listOf(item1Dto, item2Dto, item3Dto, item4Dto)

    val listItem1 = ListItem(1, 1, 1f, "count", false, 1)
    val listItem2 = ListItem(1, 2, 1f, "count", false, 2)
    val listItem3 = ListItem(2, 3, 3.5f, "pounds", false, 2)
    val listItem4 = ListItem(2, 4, 1f, "count", true, 1)
    val allListItems = listOf(listItem1, listItem2, listItem3, listItem4)
    val allItemsList1 = listOf(listItem1, listItem2)
    val allItemsList2 = listOf(listItem4, listItem3)

    val listItem1Dto = ListItemDto(item1Dto, 1f, "count", false, 1)
    val listItem2Dto = ListItemDto(item2Dto, 1f, "count", false, 2)
    val listItem3Dto = ListItemDto(item3Dto, 3.5f, "pounds", false, 2)
    val listItem4Dto = ListItemDto(item4Dto, 1f, "count", true, 1)
    val allListItemDtos = listOf(listItem1Dto, listItem2Dto, listItem3Dto, listItem4Dto)
    val allListItemDtosList1 = listOf(listItem1Dto, listItem2Dto)
    val allListItemDtosList2 = listOf(listItem4Dto, listItem3Dto)


    val shoppingList1 = ShoppingList(1, "list1", null)
    val shoppingList2 = ShoppingList(2, "list2", 1)
    val allLists = listOf(shoppingList1, shoppingList2)

    val shoppingList1Dto = ShoppingListDto(1, "list1", allListItemDtosList1, null)
    val shoppingList2Dto = ShoppingListDto(2, "list2", allListItemDtosList2, store1Dto)
}