package me.cplanchet.shoppinglistassistant.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.cplanchet.shoppinglistassistant.data.daos.ShoppingListDao
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

class FakeShoppingListDao : ShoppingListDao {
    override suspend fun insert(shoppingList: ShoppingList) {
        println(shoppingList)
    }

    override suspend fun update(shoppingList: ShoppingList) {
    }

    override suspend fun delete(shoppingList: ShoppingList) {
    }

    override fun getShoppingListById(id: Int): Flow<ShoppingList> {
        return flow {
            when (id) {
                1 -> emit(DaoMockData.shoppingList1)
                2 -> emit(DaoMockData.shoppingList2)
                else -> {}
            }
        }
    }

    override fun getAllShoppingLists(): Flow<List<ShoppingList>> {
        return flow {
            emit(DaoMockData.allLists)
        }
    }
}