package me.cplanchet.shoppinglistassistant.data

import android.content.Context

interface AppContainer {
    val shoppingListRepository: ShoppingListRepository
    val shoppingListService: ShoppingListService
}

class AppDataContainer(private val context: Context): AppContainer{
    override val shoppingListRepository: ShoppingListRepository by lazy {
        MockShoppingListRepository()
    }
    override val shoppingListService: ShoppingListService by lazy {
        MockShoppingListService()
    }

}