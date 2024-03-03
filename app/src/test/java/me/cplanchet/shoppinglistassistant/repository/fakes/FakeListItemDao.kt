package me.cplanchet.shoppinglistassistant.repository.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import me.cplanchet.shoppinglistassistant.data.daos.ListItemDao
import me.cplanchet.shoppinglistassistant.data.entities.ListItem

class FakeListItemDao: ListItemDao {
    override suspend fun insert(listItem: ListItem) {
    }

    override suspend fun update(listItem: ListItem) {
    }

    override suspend fun delete(listItem: ListItem) {
    }

    override fun getListItemsByListId(id: Int): Flow<List<ListItem>> {
        return when(id){
            1 -> flowOf(DaoMockData.allItemsList1)
            2 -> flowOf(DaoMockData.allItemsList2)
            else -> { flowOf() }
        }
    }

    override fun getListItemByItemId(listId: Int, itemId: Int): Flow<ListItem> {
        if(listId == 1){
            return when(itemId) {
                1 -> flowOf(DaoMockData.listItem1)
                2 -> flowOf(DaoMockData.listItem2)
                else -> flowOf()
            }
        }
        return when(itemId){
            3 -> flowOf(DaoMockData.listItem3)
            4 -> flowOf(DaoMockData.listItem4)
            else -> flowOf()
        }
    }

    override fun getAllListItems(): Flow<List<ListItem>> {
        return flowOf(DaoMockData.allListItems)
    }

}