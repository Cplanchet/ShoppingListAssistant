package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity

@Entity(tableName = "Aisle_Item", primaryKeys = ["aisleId", "itemId"])
data class AisleItem(
    val aisleId: Int,
    val itemId: Int
)
