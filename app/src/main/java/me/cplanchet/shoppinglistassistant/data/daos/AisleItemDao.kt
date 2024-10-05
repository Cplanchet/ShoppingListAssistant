package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.AisleItem

@Dao
interface AisleItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(aisleItem: AisleItem)

    @Update
    suspend fun update(aisleItem: AisleItem)

    @Delete
    suspend fun delete(aisleItem: AisleItem)

    @Query("SELECT * FROM Aisle_Item WHERE aisleId = :id")
    fun getAisleItemByAisleId(id: Int): Flow<List<AisleItem>>

    @Query("SELECT * FROM Aisle_Item")
    fun getAllAisleItems(): Flow<List<AisleItem>>
}