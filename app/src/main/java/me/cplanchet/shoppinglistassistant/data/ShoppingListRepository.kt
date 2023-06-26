package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto

interface ShoppingListRepository{
    fun getAllLists(): Flow<List<ShoppingListDto>>
    suspend fun insertList(list: ShoppingListDto)
    suspend fun deleteList(list: ShoppingListDto)
}