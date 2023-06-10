package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto

interface ShoppingListService {
    fun GetAllLists(): Flow<List<ShoppingListDto>>
}