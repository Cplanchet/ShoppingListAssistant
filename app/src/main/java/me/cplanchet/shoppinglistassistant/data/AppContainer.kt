package me.cplanchet.shoppinglistassistant.data

import android.content.Context

interface AppContainer {
    val shoppingListRepository: ShoppingListRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val shoppingListRepository: ShoppingListRepository by lazy {
        ManualShoppingListRepository()
    }
}