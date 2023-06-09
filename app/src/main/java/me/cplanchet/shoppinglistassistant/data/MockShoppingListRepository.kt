package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cplanchet.shoppinglistassistant.data.dtos.*

class MockShoppingListRepository : ShoppingListRepository{
    override fun getAllLists(): Flow<List<ShoppingListDto>> {
        val category = CategoryDto(1, "cat 1")
        val item1 = ItemDto(1, "item1", category)
        val item2 = ItemDto(2, "item2", category)
        val item3 = ItemDto(3, "item3", category)
        val item4 = ItemDto(4, "item4", category)

        val listItems = listOf(
            ListItemDto(item1, 1f, "count", false),
            ListItemDto(item2, 3f, "lb", false),
            ListItemDto(item3, 3f, "lb", false),
            ListItemDto(item4, 3f, "lb", false),
        )
        val listItems2 = listOf(
            ListItemDto(item1, 1f, "count", false),
            ListItemDto(item2, 3f, "count", false),
            ListItemDto(item3, 1f, "count", true)
        )
        val listItems3 = listOf(
            ListItemDto(item1, 1f, "count", false),
            ListItemDto(item2, 3f, "count", false),
        )
        val shoppingList1 = ShoppingListDto(1, "name", listItems, null)
        val shoppingList2 = ShoppingListDto(2, "name2", listItems2, null)
        val shoppingList3 = ShoppingListDto(3, "name3", listItems3, null)
        val shoppingList4 = ShoppingListDto(4, "name4", listOf(), null)
        val shoppingLists = listOf(
            shoppingList1,
            shoppingList2,
            shoppingList3,
            shoppingList4
        )
        return flowOf(shoppingLists)
    }
    override suspend fun insertList(list: ShoppingListDto){
        return
    }

    override suspend fun deleteList(list: ShoppingListDto) {
        return
    }

    override fun getAllStores(): Flow<List<StoreDto>> {
        val store1 = StoreDto(1, "Store 1", listOf(), listOf())
        val store2 = StoreDto(2, "Store 2", listOf(), listOf())

        return flowOf(listOf(store1, store2))
    }
    override suspend fun insertStore(store: StoreDto) {
    }
}