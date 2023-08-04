package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.data.dtos.StoreDto

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
    suspend fun insertItem(item: ItemDto)

    fun getAllListItems(listId: Int): Flow<List<ListItemDto>>
    suspend fun updateListItem(item: ListItemDto, listId: Int)
}