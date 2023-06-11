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

    private fun convertListToDto(list: ShoppingList): ShoppingListDto{
        var itemDtos: ArrayList<ListItemDto> = arrayListOf()
        var storeDto: StoreDto = StoreDto(1, "store", listOf()) //TODO wire up store
        listItemDao.getListItemsByListId(list.id).onEach {x -> x.forEach { itemDtos.add(convertListItemToDto(it)) }}

        return list.mapToDto(itemDtos, storeDto)
    }

    private fun convertListItemToDto(item: ListItem): ListItemDto{
        var itemDto: ItemDto = ItemDto(1, "name", CategoryDto(1, "name"))   //TODO fix this to not need to be initialized
        itemDao.getItemById(item.itemId).onEach {x -> itemDto = convertToItemDto(x)}

        return item.mapToDto(itemDto)
    }

    private fun convertToItemDto(item: Item): ItemDto{
        var categoryDto: CategoryDto = CategoryDto(1, "1")  //TODO fix this
        categoryDao.getCategoryById(item.categoryId).onEach {x -> categoryDto = x.mapToDto() }

        return item.mapToDto(categoryDto)
    }
}

fun Store.mapToDto(aisles: List<AisleDto>): StoreDto {
    return StoreDto(this.id, this.name, aisles)
}
fun Aisle.mapToDto(categories: List<CategoryDto>, items: List<ItemDto>): AisleDto {
    return AisleDto(this.id, this.name, categories, items)
}
fun ShoppingList.mapToDto(items: List<ListItemDto>, store: StoreDto): ShoppingListDto {
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


