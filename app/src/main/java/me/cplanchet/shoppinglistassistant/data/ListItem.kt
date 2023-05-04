package me.cplanchet.shoppinglistassistant.data

data class ListItem(
    val item: Item,
    val amount: Float,
    val amountUnit: String,
    val checked: Boolean
)
