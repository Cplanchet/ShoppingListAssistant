package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

class ManualShoppingListRepository : ShoppingListRepository{
    override fun GetAllLists(): Flow<List<ShoppingList>> {
        TODO("Not yet implemented")
    }

}