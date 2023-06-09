package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity

@Entity(tableName = "Aisle_Item")
data class AisleItem(
    val aisleId: Int,
    val itemId: Int
)
