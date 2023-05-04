package me.cplanchet.shoppinglistassistant.data

data class ShoppingList(
    val id: Int,
    val name: String,
    val items: List<ListItem>,
    val store: Store?
)
