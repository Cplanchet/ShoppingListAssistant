package me.cplanchet.shoppinglistassistant.data.entities

data class Aisle(
    val id: Int,
    val name: String,
    val categories: List<Category>?,
    val items: List<Item>?
)
