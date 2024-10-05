package me.cplanchet.shoppinglistassistant.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.cplanchet.shoppinglistassistant.data.daos.ItemDao
import me.cplanchet.shoppinglistassistant.data.entities.Item

class FakeItemDao : ItemDao {
    override suspend fun insert(item: Item) {
    }

    override suspend fun update(item: Item) {
    }

    override suspend fun delete(item: Item) {
    }

    override fun getItemById(id: Int): Flow<Item> {
        return flow {
            when (id) {
                1 -> emit(DaoMockData.item1)
                2 -> emit(DaoMockData.item2)
                3 -> emit(DaoMockData.item3)
                4 -> emit(DaoMockData.item4)
                else -> {}
            }
        }
    }

    override fun getAllItems(): Flow<List<Item>> {
        return flow {
            emit(DaoMockData.allItems)
        }
    }
}