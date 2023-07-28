package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Store::class,
            parentColumns = ["id"],
            childColumns = ["storeId"],
            onDelete = SET_NULL,
            deferred = false
        )
    ]
)
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val storeId: Int?
)
