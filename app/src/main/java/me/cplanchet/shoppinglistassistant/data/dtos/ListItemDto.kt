package me.cplanchet.shoppinglistassistant.data.dtos

data class ListItemDto(
    var item: ItemDto,
    val amount: Float,
    val amountUnit: String,
    val checked: Boolean,
    val order: Int
)
