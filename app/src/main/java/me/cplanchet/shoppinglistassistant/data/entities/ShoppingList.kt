package me.cplanchet.shoppinglistassistant.data.entities

data class ShoppingList(
    val id: Int,
    val name: String,
    val items: List<ListItem>,
    val store: Store?
)
