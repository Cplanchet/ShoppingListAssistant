package me.cplanchet.shoppinglistassistant.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.cplanchet.shoppinglistassistant.data.daos.StoreDao
import me.cplanchet.shoppinglistassistant.data.entities.Store

class FakeStoreDao: StoreDao {
    override suspend fun insert(store: Store) {
    }

    override suspend fun update(store: Store) {
    }

    override suspend fun delete(store: Store) {
    }

    override fun getStoreById(id: Int): Flow<Store> {
        return flow {
           when(id){
               1 -> emit(DaoMockData.store1)
               2 -> emit(DaoMockData.store2)
               else -> {}
           }
        }
    }

    override fun getAllStores(): Flow<List<Store>> {
        return flow {
            emit(DaoMockData.allStores)
        }
    }

}