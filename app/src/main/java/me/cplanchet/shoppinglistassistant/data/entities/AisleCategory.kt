package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity

@Entity(tableName = "Aisle_Category")
data class AisleCategory (
    val aisleId: Int,
    val categoryId: Int
)