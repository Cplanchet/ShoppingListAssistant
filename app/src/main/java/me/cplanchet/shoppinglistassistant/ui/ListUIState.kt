package me.cplanchet.shoppinglistassistant.ui

import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.data.dtos.StoreDto

data class ListUIState(
    val id: Int = 0,
    val name: String = "",
    val items: List<ListItemDto> = ArrayList(),
    val store: StoreDto? = null)

fun ListUIState.toListDto(): ShoppingListDto = ShoppingListDto(
    id = id,
    name = name,
    items = items,
    store = store
)

fun ShoppingListDto.toListUIState(): ListUIState = ListUIState(
    id = id,
    name = name,
    items = items,
    store = store
)

fun ListUIState.isValid(): Boolean {
    return name.isNotBlank()
}