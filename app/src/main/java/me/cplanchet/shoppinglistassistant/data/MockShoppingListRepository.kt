package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cplanchet.shoppinglistassistant.data.dtos.*

class MockShoppingListRepository : ShoppingListRepository{
    val category = CategoryDto(1, "cat 1")
    val item1 = ItemDto(1, "item1", category)
    val item2 = ItemDto(2, "item2", category)
    val item3 = ItemDto(3, "item3", category)
    val item4 = ItemDto(4, "item4", category)

    val listItems = listOf(
        ListItemDto(item1, 1f, "count", true, 1),
        ListItemDto(item2, 3f, "lb", true, 2),
        ListItemDto(item3, 3f, "lb", false, 3),
        ListItemDto(item4, 3f, "lb", false, 4),
    )
    val listItems2 = listOf(
        ListItemDto(item1, 1f, "count", false, 1),
        ListItemDto(item2, 3f, "count", false, 2),
        ListItemDto(item3, 1f, "count", true, 3)
    )
    val listItems3 = listOf(
        ListItemDto(item1, 1f, "count", false,1),
        ListItemDto(item2, 3f, "count", false, 2),
    )
    val shoppingList1 = ShoppingListDto(1, "Name1", listItems, null)
    val shoppingList2 = ShoppingListDto(2, "name2", listItems2, null)
    val shoppingList3 = ShoppingListDto(3, "name3", listItems3, null)
    val shoppingList4 = ShoppingListDto(4, "name4", listOf(), null)
    val shoppingLists = listOf(
        shoppingList1,
        shoppingList2,
        shoppingList3,
        shoppingList4
    )
    val store1 = StoreDto(1, "Store 1", listOf(), listOf())
    val store2 = StoreDto(2, "Store 2", listOf(), listOf())

    override fun getAllLists(): Flow<List<ShoppingListDto>> {
        return flowOf(shoppingLists)
    }

    override fun getListById(listId: Int): Flow<ShoppingListDto> {
        return flowOf(shoppingList1)
    }
    override suspend fun insertList(list: ShoppingListDto){
        return
    }
    override suspend fun deleteList(list: ShoppingListDto) {
        return
    }
    override suspend fun updateList(store:ShoppingListDto){}

    override fun getAllStores(): Flow<List<StoreDto>> {

        return flowOf(listOf(store1, store2))
    }
    override fun getStoreById(id: Int): Flow<StoreDto>{
        return flowOf(store1)
    }
    override suspend fun insertStore(store: StoreDto) {
    }

    override suspend fun addListItem(listItem: ListItemDto, listId: Int){}
    override suspend fun deleteListItem(listItem: ListItemDto, listId: Int){}

    override fun getAllItems(): Flow<List<ItemDto>>{
        return flowOf(listOf(item1, item2, item3, item4))
    }
    override fun getItemById(itemId: Int): Flow<ItemDto>{
        return flowOf(item1)
    }
    override suspend fun insertItem(item: ItemDto){}
    override suspend fun updateItem(item: ItemDto){}
    override suspend fun deleteItem(item: ItemDto){}

    override fun getAllListItems(listId: Int): Flow<List<ListItemDto>>{
        return flowOf(listItems)
    }
    override fun getListItemById(listId: Int, itemId: Int): Flow<ListItemDto>{
        return flowOf(listItems[0])
    }
    override suspend fun updateListItem(item: ListItemDto, listId: Int){
    }

    override suspend fun swapListItems(from: ListItemDto, to: ListItemDto, listId: Int) {
    }

    override fun getAllCategories(): Flow<List<CategoryDto>>{
        return flowOf(listOf(category))
    }

    override suspend fun insertCategory(category: CategoryDto) {
    }
}