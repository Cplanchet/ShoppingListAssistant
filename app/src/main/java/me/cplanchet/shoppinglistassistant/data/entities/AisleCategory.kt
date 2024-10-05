package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "Aisle_Category",
    primaryKeys = ["aisleId", "categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = Aisle::class,
            parentColumns = ["id"],
            childColumns = ["aisleId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = CASCADE
        )
    ]
)
data class AisleCategory(
    val aisleId: Int,
    val categoryId: Int
)