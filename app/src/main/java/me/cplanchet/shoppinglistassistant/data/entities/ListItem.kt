package me.cplanchet.shoppinglistassistant.data.entities

data class ListItem(
    val listId: Int,
    val itemId: Int,
    val amount: Float,
    val amountUnit: String,
    val checked: Boolean
)
