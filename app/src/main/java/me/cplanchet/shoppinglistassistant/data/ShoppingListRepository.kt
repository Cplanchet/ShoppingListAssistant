package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.dtos.*

interface ShoppingListRepository{
    fun getAllLists(): Flow<List<ShoppingListDto>>
    fun getListById(listId: Int): Flow<ShoppingListDto>
    suspend fun insertList(list: ShoppingListDto)
    suspend fun deleteList(list: ShoppingListDto)
    suspend fun updateList(store:ShoppingListDto)

    fun getAllStores(): Flow<List<StoreDto>>
    fun getStoreById(id: Int): Flow<StoreDto>
    suspend fun insertStore(store:StoreDto)

    suspend fun addListItem(listItem: ListItemDto, listId: Int)
    suspend fun deleteListItem(listItem: ListItemDto, listId: Int)

    fun getAllItems(): Flow<List<ItemDto>>
    fun getItemById(itemId: Int): Flow<ItemDto>
    suspend fun insertItem(item: ItemDto)
    suspend fun updateItem(item: ItemDto)
    suspend fun deleteItem(item: ItemDto)

    fun getAllListItems(listId: Int): Flow<List<ListItemDto>>
    fun getListItemById(listId: Int, itemId: Int): Flow<ListItemDto>
    suspend fun updateListItem(item: ListItemDto, listId: Int)
    suspend fun swapListItems(from: ListItemDto, to: ListItemDto, listId: Int)

    fun getAllCategories(): Flow<List<CategoryDto>>
}