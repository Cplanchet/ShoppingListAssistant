package me.cplanchet.shoppinglistassistant.fakes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.cplanchet.shoppinglistassistant.data.daos.CategoryDao
import me.cplanchet.shoppinglistassistant.data.entities.Category

class FakeCategoryDao: CategoryDao {
    override suspend fun insert(category: Category) {
    }

    override suspend fun update(category: Category) {
    }

    override suspend fun delete(category: Category) {
    }

    override fun getCategoryById(id: Int): Flow<Category> {
        return flow {
            when(id){
                1 -> emit(DaoMockData.category1)
                2 -> emit(DaoMockData.category2)
                else -> {}
            }
        }
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return flow{
            emit(DaoMockData.allCategories)
        }
    }
}