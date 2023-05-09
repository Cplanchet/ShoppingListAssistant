package me.cplanchet.shoppinglistassistant.data.entities

import me.cplanchet.shoppinglistassistant.data.entities.Item

data class ListItem(
    val item: Item,
    val amount: Float,
    val amountUnit: String,
    val checked: Boolean
)
