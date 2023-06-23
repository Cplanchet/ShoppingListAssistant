package me.cplanchet.shoppinglistassistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import me.cplanchet.shoppinglistassistant.data.daos.CategoryDao
import me.cplanchet.shoppinglistassistant.data.daos.ItemDao
import me.cplanchet.shoppinglistassistant.data.daos.ListItemDao
import me.cplanchet.shoppinglistassistant.data.daos.ShoppingListDao
import me.cplanchet.shoppinglistassistant.data.dtos.*
import me.cplanchet.shoppinglistassistant.data.entities.*

class DefaultShoppingListRepository(private val shoppingListDao: ShoppingListDao, private val listItemDao: ListItemDao, private val itemDao: ItemDao, private val categoryDao: CategoryDao): ShoppingListRepository {
    override fun getAllLists(): Flow<List<ShoppingListDto>> {
        var listsDtos: ArrayList<ShoppingListDto> = arrayListOf()
        shoppingListDao.getAllShoppingLists().onEach {x -> x.forEach{listsDtos.add(convertListToDto(it))}}

        return flowOf(listsDtos)
    }

    override suspend fun insertList(list: ShoppingListDto) {
        val newList = ShoppingList(list.id, list.name, list.store?.id)
        shoppingListDao.insert(newList);
    }

    private fun convertListToDto(list: ShoppingList): ShoppingListDto{
        var itemDtos: ArrayList<ListItemDto> = arrayListOf()
        var storeDto: StoreDto? = null  //TODO add Store
        listItemDao.getListItemsByListId(list.id).onEach {x -> x.forEach { itemDtos.add(convertListItemToDto(it)) }}

        return list.mapToDto(itemDtos, storeDto)
    }

    private fun convertListItemToDto(item: ListItem): ListItemDto{
        var itemDto: ItemDto? = null
        itemDao.getItemById(item.itemId).onEach {x -> itemDto = convertToItemDto(x)}

        if(itemDto == null){
            throw NullPointerException()
        }
        return item.mapToDto(itemDto as ItemDto)
    }

    private fun convertToItemDto(item: Item): ItemDto{
        var categoryDto: CategoryDto? = null
        categoryDao.getCategoryById(item.categoryId).onEach {x -> categoryDto = x.mapToDto() }

        if(categoryDto == null){
            throw NullPointerException()
        }
        return item.mapToDto(categoryDto as CategoryDto)
    }
}

fun Store.mapToDto(aisles: List<AisleDto>): StoreDto {
    return StoreDto(this.id, this.name, aisles)
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


