package me.cplanchet.shoppinglistassistant.repository

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.cplanchet.shoppinglistassistant.data.DefaultShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.ShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.daos.*
import me.cplanchet.shoppinglistassistant.fakes.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ShoppingListRepositoryTest {
    @Spy
    private var shoppingListDao: ShoppingListDao = FakeShoppingListDao()
    @Spy
    private val listItemDao: ListItemDao = FakeListItemDao()
    @Spy
    private val itemDao: ItemDao = FakeItemDao()
    @Spy
    private val categoryDao: CategoryDao = FakeCategoryDao()
    @Spy
    private val storeDao: StoreDao = FakeStoreDao()

    private lateinit var repository: ShoppingListRepository

    @Before
    fun createRepo(){
        repository = DefaultShoppingListRepository(shoppingListDao, listItemDao, itemDao, categoryDao, storeDao)
    }
    @Test
    fun getAllTests_retrievesTests() = runTest {
        val expected = DaoMockData.shoppingList1Dto

        val actual = repository.getAllLists().first()

        assertEquals(DaoMockData.allLists.size, actual.size)
        assertEquals(expected, actual[0])
    }
    @Test
    fun getListById_ListId2_returnsCorrectList() = runTest{
        val expected = DaoMockData.shoppingList2Dto

        val actual = repository.getListById(2).first()

        assertEquals(expected, actual)
    }
    @Test
    fun insertList_givenDto_CallsDaoWithEntityEquivalent() = runTest{
        val toInsert = DaoMockData.shoppingList2Dto
        val expectedDaoInsert = DaoMockData.shoppingList2

        repository.insertList(toInsert)

        verify(shoppingListDao).insert(expectedDaoInsert)
    }
    @Test
    fun deleteList_givenDto_callsDaoWithEntity() = runTest {
        val toDelete = DaoMockData.shoppingList1Dto
        val expectedEntity = DaoMockData.shoppingList1

        repository.deleteList(toDelete)

        verify(shoppingListDao).delete(expectedEntity)
    }
    @Test
    fun updateList_givenDto_callsDaoWithEntity() = runTest {
        val toUpdate = DaoMockData.shoppingList1Dto
        val expectedEntity = DaoMockData.shoppingList1

        repository.updateList(toUpdate)

        verify(shoppingListDao).update(expectedEntity)
    }

    @Test
    fun getAllStores_returnsListOfStores() = runTest {
        val expected = DaoMockData.allStoreDtos

        val actual = repository.getAllStores().first()

        assertEquals(expected, actual)
    }
    @Test
    fun getStoreById_id1_returnsCorrectStore() = runTest {
        val expected = DaoMockData.store1Dto

        val actual = repository.getStoreById(1).first()

        assertEquals(expected, actual)
    }
    @Test
    fun insertStore_passedDto_callsDaoWithEntity() = runTest {
        val toInsert = DaoMockData.store2Dto
        val expectedEntity = DaoMockData.store2

        repository.insertStore(toInsert)

        verify(storeDao).insert(expectedEntity)
    }

    @Test
    fun addListitem_givenADtoAndListId_callsDaoWithEntity() = runTest {
        val toInsert = DaoMockData.listItem1Dto
        val expectedEntity = DaoMockData.listItem1

        repository.addListItem(toInsert, 1)

        verify(listItemDao).insert(expectedEntity)
    }
    @Test
    fun deleteListItem_givenDtoAndListId_callsDaoWithEntity() = runTest {
        val toDelete = DaoMockData.listItem3Dto
        val expectedEntity = DaoMockData.listItem3

        repository.deleteListItem(toDelete, 2)

        verify(listItemDao).delete(expectedEntity)
    }
    @Test
    fun getAllItems_returnsList() = runTest {
        val expected = DaoMockData.allItemDtos

        val actual = repository.getAllItems().first()

        assertEquals(expected, actual)
    }
    @Test
    fun getItemById_id2_returnsCorrectItem() = runTest {
        val expected = DaoMockData.item2Dto

        val actual = repository.getItemById(2).first()

        assertEquals(expected, actual)
    }
    @Test
    fun insertItem_givenDto_callsDaoWithEntity() = runTest {
        val toInsert = DaoMockData.item3Dto
        val expectedEntity = DaoMockData.item3

        repository.insertItem(toInsert)

        verify(itemDao).insert(expectedEntity)
    }
    @Test
    fun updateItem_givenDto_callsDaoWithEntity() = runTest {
        val toUpdate = DaoMockData.item4Dto
        val expectedEntity = DaoMockData.item4

        repository.updateItem(toUpdate)

        verify(itemDao).update(expectedEntity)
    }
    @Test
    fun deleteItem_givenDto_callsDaoWithEntity() = runTest {
        val toDelete = DaoMockData.item1Dto
        val expectedEntity = DaoMockData.item1

        repository.deleteItem(toDelete)

        verify(itemDao).delete(expectedEntity)
    }
    @Test
    fun getAllListItems_listId1_returnsList() = runTest {
        val expected = DaoMockData.allListItemDtosList1

        val actual = repository.getAllListItems(1).first()

        assertEquals(expected, actual)
    }
    @Test
    fun getListItemById_listId2AndItemId3_returnsCorrectItem() = runTest {
        val expected = DaoMockData.listItem3Dto

        val actual = repository.getListItemById(2, 3).first()

        assertEquals(expected, actual)
    }
    @Test
    fun updateListItem_givenDto_CallsDaoWithEntity() = runTest {
        val toUpdate = DaoMockData.listItem2Dto
        val expectedEntity = DaoMockData.listItem2

        repository.updateListItem(toUpdate, 1)

         verify(listItemDao).update(expectedEntity)
    }
    @Test
    fun swapListItems_givenTwoDtos_SwapsOrderAndPassesEntityToDao() = runTest {
        val from = DaoMockData.listItem3Dto
        val to = DaoMockData.listItem4Dto
        val expectedEntity1 = DaoMockData.listItem4.copy(order = 2)
        val expectedEntity2 = DaoMockData.listItem3.copy(order = 1)

        repository.swapListItems(from, to, 2)

        verify(listItemDao).update(expectedEntity1)
        verify(listItemDao).update(expectedEntity2)
    }

    @Test
    fun getAllCategories_returnsList() = runTest {
        val expected = DaoMockData.allCategoryDtos

        val actual = repository.getAllCategories().first()

        assertEquals(expected, actual)
    }
    @Test
    fun insertCategory_givenDto_CallsDaoWithEntity() = runTest {
        val toInsert = DaoMockData.category1Dto
        val expectedEntity = DaoMockData.category1

        repository.insertCategory(toInsert)

        verify(categoryDao).insert(expectedEntity)
    }
    @Test
    fun updateCategory_givenDto_CallsDaoWithEntity() = runTest {
        val toUpdate = DaoMockData.category2Dto
        val expectedEntity = DaoMockData.category2

        repository.updateCategory(toUpdate)

        verify(categoryDao).update(expectedEntity)
    }
}