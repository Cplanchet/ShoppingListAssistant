package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

class MockShoppingListRepository : ShoppingListRepository{
    override fun GetAllLists(): Flow<List<ShoppingList>> {

        return flowOf<List<ShoppingList>>()
    }
}