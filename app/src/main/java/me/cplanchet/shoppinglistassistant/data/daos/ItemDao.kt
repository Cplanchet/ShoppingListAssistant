package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)
    @Update
    suspend fun update(item:Item)
    @Delete
    suspend fun delete(item:Item)
    @Query("SELECT * FROM Item WHERE id = :id")
    fun getItemById(id: Int): Flow<Item>
    @Query("SELECT * FROM Item")
    fun getAllItems(): Flow<List<Item>>
}