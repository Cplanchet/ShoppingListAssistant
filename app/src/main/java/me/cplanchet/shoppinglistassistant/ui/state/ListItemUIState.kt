package me.cplanchet.shoppinglistassistant.ui.state

import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto

data class ListItemUIState(
    val item: ItemDto = ItemDto(),
    val amount: Float = 0f,
    val amountUnit: String = "",
    val checked: Boolean = false
    )

fun ListItemDto.toListItemUIState(): ListItemUIState = ListItemUIState(item, amount, amountUnit, checked)
fun ListItemUIState.toListItem(): ListItemDto = ListItemDto(item, amount, amountUnit, checked)

fun ListItemUIState.isValid(): Boolean{
    return amountUnit.isNotBlank()
}