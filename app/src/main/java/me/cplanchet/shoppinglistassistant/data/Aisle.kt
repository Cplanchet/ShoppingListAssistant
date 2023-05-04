package me.cplanchet.shoppinglistassistant.data

data class Aisle(
    val id: Int,
    val name: String,
    val categories: List<Category>?,
    val items: List<Item>?
)
