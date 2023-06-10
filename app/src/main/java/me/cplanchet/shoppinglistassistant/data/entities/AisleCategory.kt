package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity

@Entity(tableName = "Aisle_Category", primaryKeys = ["aisleId", "categoryId"])
data class AisleCategory (
    val aisleId: Int,
    val categoryId: Int
)