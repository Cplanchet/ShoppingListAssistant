package me.cplanchet.shoppinglistassistant.data.dtos

data class ListItemDto(
    val item: ItemDto,
    val amount: Float,
    val amountUnit: String,
    val checked: Boolean
)
