package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingList: ShoppingList)
    @Update
    suspend fun update(shoppingList:ShoppingList)
    @Delete
    suspend fun delete(shoppingList:ShoppingList)
    @Query("SELECT * FROM ShoppingList WHERE id = :id")
    fun getShoppingListById(id: Int): Flow<ShoppingList>
    @Query("SELECT * FROM ShoppingList")
    fun getAllShoppingLists(): Flow<List<ShoppingList>>
}