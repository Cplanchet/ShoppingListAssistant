package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

interface ShoppingListRepository{
    fun GetAllLists(): Flow<List<ShoppingList>>
}