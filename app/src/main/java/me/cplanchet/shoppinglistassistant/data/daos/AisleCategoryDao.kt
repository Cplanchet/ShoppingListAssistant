package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.AisleCategory

@Dao
interface AisleCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(aisleCategory: AisleCategory)

    @Update
    suspend fun update(aisleCategory:AisleCategory)

    @Delete
    suspend fun delete(aisleCategory:AisleCategory)

    @Query("SELECT * FROM AisleCategory WHERE aisleId = :id")
    fun getAisleCategoryByAisleId(id: Int): Flow<List<AisleCategory>>

    @Query("SELECT * FROM AisleCategory WHERE categoryId = :id")
    fun getAisleCategoryByCategoryId(id: Int): Flow<List<AisleCategory>>

    @Query("SELECT * FROM AisleCategory")
    fun getAllAisleCategories(): Flow<List<AisleCategory>>
}