package me.cplanchet.shoppinglistassistant.data.dtos

data class ShoppingListDto(
    val id: Int,
    val name: String,
    val items: List<ListItemDto>,
    val store: StoreDto
)
