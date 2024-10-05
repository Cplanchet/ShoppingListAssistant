package me.cplanchet.shoppinglistassistant.data.daos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.cplanchet.shoppinglistassistant.data.entities.ListItem

@Dao
interface ListItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(listItem: ListItem)

    @Update
    suspend fun update(listItem: ListItem)

    @Delete
    suspend fun delete(listItem: ListItem)

    @Query("SELECT * FROM List_Item WHERE listId = :id ORDER BY `order`")
    fun getListItemsByListId(id: Int): Flow<List<ListItem>>

    @Query("SELECT * FROM List_Item WHERE listId = :listId AND itemId = :itemId ORDER BY `order`")
    fun getListItemByItemId(listId: Int, itemId: Int): Flow<ListItem>

    @Query("SELECT * FROM List_Item ORDER BY `order`")
    fun getAllListItems(): Flow<List<ListItem>>
}