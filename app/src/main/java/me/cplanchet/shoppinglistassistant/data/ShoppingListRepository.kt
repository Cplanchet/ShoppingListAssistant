package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.data.dtos.StoreDto

interface ShoppingListRepository{
    fun getAllLists(): Flow<List<ShoppingListDto>>
    suspend fun insertList(list: ShoppingListDto)
    suspend fun deleteList(list: ShoppingListDto)

    fun getAllStores(): Flow<List<StoreDto>>
    suspend fun insertStore(store:StoreDto)
}