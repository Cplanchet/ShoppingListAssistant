package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Aisle_Item",
    primaryKeys = ["aisleId", "itemId"],
    foreignKeys = [
        ForeignKey(
            entity = Aisle::class,
            parentColumns = ["id"],
            childColumns = ["aisleId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Item::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AisleItem(
    val aisleId: Int,
    val itemId: Int
)
