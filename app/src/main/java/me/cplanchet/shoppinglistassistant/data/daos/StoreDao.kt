package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.Store

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(store: Store)

    @Update
    suspend fun update(store: Store)

    @Delete
    suspend fun delete(store: Store)

    @Query("SELECT * FROM Store WHERE id = :id")
    fun getStoreById(id: Int): Flow<Store>

    @Query("SELECT * FROM Store")
    fun getAllStores(): Flow<List<Store>>
}