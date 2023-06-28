package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import me.cplanchet.shoppinglistassistant.data.daos.*
import me.cplanchet.shoppinglistassistant.data.dtos.*
import me.cplanchet.shoppinglistassistant.data.entities.*

class DefaultShoppingListRepository(private val shoppingListDao: ShoppingListDao, private val listItemDao: ListItemDao, private val itemDao: ItemDao, private val categoryDao: CategoryDao, private val storeDao: StoreDao): ShoppingListRepository {
    override fun getAllLists(): Flow<List<ShoppingListDto>> {
        return shoppingListDao.getAllShoppingLists().map {convertListsToDto(it)}
    }

    override suspend fun insertList(list: ShoppingListDto) {
        shoppingListDao.insert(list.mapToEntity());
    }

    override suspend fun deleteList(list: ShoppingListDto) {
        shoppingListDao.delete(list.mapToEntity())
    }

    override fun getAllStores(): Flow<List<StoreDto>> {
        return storeDao.getAllStores().map{ convertToStoreDto(it) }
    }

    override suspend fun insertStore(store: StoreDto) {
        storeDao.insert(store.mapToEntity())
    }

    private suspend fun convertListsToDto(lists: List<ShoppingList>): List<ShoppingListDto>{
        var listDtos = ArrayList<ShoppingListDto>()

        for(item in lists){
            var storeDto: StoreDto? = null  //TODO add Store
            val itemDtos = listItemDao.getListItemsByListId(item.id).map { convertListItemsToDto(it) }

            listDtos.add(item.mapToDto(itemDtos.first(), storeDto))
        }
        return listDtos
    }

    private fun convertListItemsToDto(items: List<ListItem>): List<ListItemDto>{
        var itemDtos = ArrayList<ListItemDto>()

        for(item in items){
            var itemDto: ItemDto? = null
            itemDao.getItemById(item.itemId).onEach {x -> itemDto = convertToItemDto(x)}

            if(itemDto == null){
                throw NullPointerException()
            }
            itemDtos.add(item.mapToDto(itemDto as ItemDto))
        }

        return itemDtos
    }

    private fun convertToItemDto(item: Item): ItemDto{
        var categoryDto: CategoryDto? = null
        categoryDao.getCategoryById(item.categoryId).onEach {x -> categoryDto = x.mapToDto() }

        if(categoryDto == null){
            throw NullPointerException()
        }
        return item.mapToDto(categoryDto as CategoryDto)
    }

    private fun convertToStoreDto(stores: List<Store>): List<StoreDto> {
        var storeDtos = ArrayList<StoreDto>()
        for (store in stores) {
            storeDtos.add(store.mapToDto(listOf(), listOf()))
        }
        return storeDtos
    }
}

fun Store.mapToDto(items: List<ItemDto>, aisles: List<AisleDto>): StoreDto {
    return StoreDto(this.id, this.name, items, aisles)
}
fun Aisle.mapToDto(categories: List<CategoryDto>, items: List<ItemDto>): AisleDto {
    return AisleDto(this.id, this.name, categories, items)
}
fun ShoppingList.mapToDto(items: List<ListItemDto>, store: StoreDto?): ShoppingListDto {
    return ShoppingListDto(this.id, this.name, items, store)
}
fun Category.mapToDto(): CategoryDto{
    return CategoryDto(this.id, this.name)
}
fun Item.mapToDto(category: CategoryDto): ItemDto{
    return ItemDto(this.id, this.name, category)
}
fun ListItem.mapToDto(item: ItemDto): ListItemDto{
    return ListItemDto(item, this.amount, this.amountUnit, this.checked)
}
fun ShoppingListDto.mapToEntity(): ShoppingList{
    return ShoppingList(this.id, this.name, this.store?.id)
}
fun StoreDto.mapToEntity(): Store{
    return Store(this.id, this.name)
}


