package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import me.cplanchet.shoppinglistassistant.data.daos.*
import me.cplanchet.shoppinglistassistant.data.dtos.*
import me.cplanchet.shoppinglistassistant.data.entities.*

class DefaultShoppingListRepository(private val shoppingListDao: ShoppingListDao, private val listItemDao: ListItemDao, private val itemDao: ItemDao, private val categoryDao: CategoryDao, private val storeDao: StoreDao): ShoppingListRepository {
    override fun getAllLists(): Flow<List<ShoppingListDto>> {
        return shoppingListDao.getAllShoppingLists().map {convertListsToDto(it)}
    }
    override fun getListById(listId: Int): Flow<ShoppingListDto> {
        return shoppingListDao.getShoppingListById(listId).map { convertListToDto(it) }
    }
    override suspend fun insertList(list: ShoppingListDto) {
        shoppingListDao.insert(list.mapToEntity());
    }
    override suspend fun deleteList(list: ShoppingListDto) {
        shoppingListDao.delete(list.mapToEntity())
    }
    override suspend fun updateList(store:ShoppingListDto){
        shoppingListDao.update(store.mapToEntity())
    }

    override fun getAllStores(): Flow<List<StoreDto>> {
        return storeDao.getAllStores().map{ convertToStoreDtos(it) }
    }
    override fun getStoreById(id: Int): Flow<StoreDto>{
        return storeDao.getStoreById(id).map { convertToStoreDto(it) }
    }
    override suspend fun insertStore(store: StoreDto) {
        storeDao.insert(store.mapToEntity())
    }

    override suspend fun addListItem(listItem: ListItemDto, listId: Int){
        listItemDao.insert(listItem.mapToEntity(listId))
    }
    override suspend fun deleteListItem(listItem: ListItemDto, listId: Int){
        listItemDao.delete(listItem.mapToEntity(listId))
    }

    override fun getAllItems(): Flow<List<ItemDto>>{
        return itemDao.getAllItems().map { convertToItemDtos(it) }
    }
    override fun getItemById(itemId: Int): Flow<ItemDto>{
        return itemDao.getItemById(itemId).map { convertToItemDto(it) }
    }
    override suspend fun insertItem(item: ItemDto){
        itemDao.insert(item.mapToEntity())
    }
    override suspend fun updateItem(item: ItemDto){
        itemDao.update(item.mapToEntity())
    }
    override suspend fun deleteItem(item: ItemDto){
        itemDao.delete(item.mapToEntity())
    }

    override fun getAllListItems(listId: Int): Flow<List<ListItemDto>>{
        return listItemDao.getListItemsByListId(listId).map { convertListItemsToDto(it) }
    }
    override fun getListItemById(listId: Int, itemId: Int): Flow<ListItemDto>{
        return listItemDao.getListItemByItemId(listId, itemId).map { convertListItemToDto(it) }
    }
    override suspend fun updateListItem(item: ListItemDto, listId: Int){
        listItemDao.update(ListItem(listId, item.item.id, item.amount, item.amountUnit, item.checked, item.order))
    }

    override suspend fun swapListItems(from: ListItemDto, to: ListItemDto, listId: Int) {
        listItemDao.update(to.copy(order = from.order).mapToEntity(listId))
        listItemDao.update(from.copy(order = to.order).mapToEntity(listId))
    }

    override fun getAllCategories(): Flow<List<CategoryDto>>{
        return categoryDao.getAllCategories().map { convertToCategoryDtos(it) }
    }

    override suspend fun insertCategory(category: CategoryDto) {
        categoryDao.insert(category.mapToEntity())
    }

    private suspend fun convertListsToDto(lists: List<ShoppingList>): List<ShoppingListDto>{
        val listDtos = ArrayList<ShoppingListDto>()

        for(item in lists){
            listDtos.add(convertListToDto(item))
        }
        return listDtos
    }

    private suspend fun convertListToDto(list: ShoppingList): ShoppingListDto{
        var storeDto: StoreDto? = null
        if(list.storeId != null){
            storeDto = storeDao.getStoreById(list.storeId).map { convertToStoreDto(it) }.first()
        }
        val itemDtos = listItemDao.getListItemsByListId(list.id).map { convertListItemsToDto(it) }
        return list.mapToDto(itemDtos.first(), storeDto)
    }

    private suspend fun convertListItemToDto(item: ListItem?): ListItemDto{
        if(item != null){
            return (item.mapToDto(itemDao.getItemById(item.itemId).map { convertToItemDto(it)}.first()))
        }
        return ListItemDto(ItemDto(), 0f, "", true, 0)
    }
    private suspend fun convertListItemsToDto(items: List<ListItem>): List<ListItemDto>{
        var itemDtos = ArrayList<ListItemDto>()

        for(item in items){
            itemDtos.add(convertListItemToDto(item))
        }
            return itemDtos
    }

    private suspend fun convertToItemDto(item: Item?): ItemDto{
        var categoryDto: CategoryDto?
        if(item != null){
            categoryDto = categoryDao.getCategoryById(item.categoryId ?: 0).map { convertToCategoryDto(it) }.firstOrNull()
            return item.mapToDto(categoryDto)
        }
        return ItemDto()
    }

    private suspend fun convertToItemDtos(items: List<Item>): List<ItemDto>{
        var itemDtos = ArrayList<ItemDto>()
        for(item in items){
            itemDtos.add(convertToItemDto(item))
        }
        return itemDtos
    }

    private fun convertToStoreDto(store: Store): StoreDto{
        return store.mapToDto(listOf(), listOf())
    }

    private fun convertToStoreDtos(stores: List<Store>): List<StoreDto> {
        var storeDtos = ArrayList<StoreDto>()
        for (store in stores) {
            storeDtos.add(convertToStoreDto(store))
        }
        return storeDtos
    }

    private fun convertToCategoryDto(category: Category?): CategoryDto?{
        if(category != null && category.id !=0){
            return category.mapToDto()
        }
        return null;
    }
    private fun convertToCategoryDtos(categories: List<Category>): List<CategoryDto>{
        var dtos: ArrayList<CategoryDto> = ArrayList()

        categories.forEach {
            convertToCategoryDto(it)?.let { it1 -> dtos.add(it1) }
        }

        return dtos
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
fun CategoryDto.mapToEntity(): Category {
    return Category(this.id, this.name)
}
fun Item.mapToDto(category: CategoryDto?): ItemDto{
    return ItemDto(this.id, this.name, category)
}
fun ItemDto.mapToEntity(): Item{
    return Item(this.id, this.name, this.category?.id)
}
fun ListItem.mapToDto(item: ItemDto): ListItemDto{
    return ListItemDto(item, this.amount, this.amountUnit, this.checked, this.order)
}
fun ListItemDto.mapToEntity(listId: Int): ListItem{
    return ListItem(listId, this.item.id, this.amount, this.amountUnit, this.checked, this.order)
}
fun ShoppingListDto.mapToEntity(): ShoppingList{
    return ShoppingList(this.id, this.name, this.store?.id)
}
fun StoreDto.mapToEntity(): Store{
    return Store(this.id, this.name)
}

