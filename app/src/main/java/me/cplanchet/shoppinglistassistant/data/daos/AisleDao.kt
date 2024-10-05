package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.Aisle

@Dao
interface AisleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(aisle: Aisle)

    @Update
    suspend fun update(aisle: Aisle)

    @Delete
    suspend fun delete(aisle: Aisle)

    @Query("SELECT * FROM Aisle WHERE id = :id")
    fun getAisleById(id: Int): Flow<Aisle>

    @Query("SELECT * FROM Aisle")
    fun getAllAisles(): Flow<List<Aisle>>
}