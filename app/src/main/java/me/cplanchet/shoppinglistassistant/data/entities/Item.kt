package me.cplanchet.shoppinglistassistant.data.entities

import me.cplanchet.shoppinglistassistant.data.entities.Category

data class Item(
    val id: Int,
    val name: String,
    val category: Category
)