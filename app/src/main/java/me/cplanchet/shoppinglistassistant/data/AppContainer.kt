package me.cplanchet.shoppinglistassistant.data

import android.content.Context
import me.cplanchet.shoppinglistassistant.data.databases.ListDatabase

interface AppContainer {
    val shoppingListRepository: ShoppingListRepository
}

class AppDataContainer(private val context: Context): AppContainer{
    override val shoppingListRepository: ShoppingListRepository by lazy {
        DefaultShoppingListRepository(
            ListDatabase.getDatabase(context).shoppingListDao(),
            ListDatabase.getDatabase(context).listItemDao(),
            ListDatabase.getDatabase(context).itemDao(),
            ListDatabase.getDatabase(context).categoryDao(),
            ListDatabase.getDatabase(context).storeDao()
        )
    }
}