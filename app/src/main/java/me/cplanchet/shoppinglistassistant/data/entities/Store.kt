package me.cplanchet.shoppinglistassistant.data.entities

import me.cplanchet.shoppinglistassistant.data.entities.Aisle

data class Store(
    val id: Int,
    val name: String,
    val aisles: List<Aisle>?
)
