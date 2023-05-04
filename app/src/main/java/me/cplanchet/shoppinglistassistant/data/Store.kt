package me.cplanchet.shoppinglistassistant.data

data class Store(
    val id: Int,
    val name: String,
    val aisles: List<Aisle>?
)
