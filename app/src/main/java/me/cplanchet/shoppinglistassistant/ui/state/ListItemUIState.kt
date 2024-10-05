package me.cplanchet.shoppinglistassistant.ui.state

import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto

data class ListItemUIState(
    val item: ItemDto = ItemDto(),
    val amount: Float = 0f,
    val amountUnit: String = "",
    val checked: Boolean = false,
    val order: Int = 0
)

fun ListItemDto.toListItemUIState(): ListItemUIState = ListItemUIState(item, amount, amountUnit, checked, order)
fun ListItemUIState.toListItem(): ListItemDto = ListItemDto(item, amount, amountUnit, checked, order)

fun ListItemUIState.isValid(): Boolean {
    return amountUnit.isNotBlank() && amount > 0 && order > 0
}