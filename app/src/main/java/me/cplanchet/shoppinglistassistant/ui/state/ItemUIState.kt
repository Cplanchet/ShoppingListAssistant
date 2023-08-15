package me.cplanchet.shoppinglistassistant.ui.state

import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto

data class ItemUIState(
    val id: Int = 0,
    val name: String = "",
    val category: CategoryDto? = null
)

fun ItemUIState.toItem(): ItemDto = ItemDto(id, name, category)

fun ItemDto.toItemUiState(): ItemUIState = ItemUIState(id, name, category)

fun ItemUIState.isValid(): Boolean{
    return name.isNotBlank()
}
