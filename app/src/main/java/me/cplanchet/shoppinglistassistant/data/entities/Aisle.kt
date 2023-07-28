package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            Store::class,
            parentColumns = ["id"],
            childColumns = ["storeId"],
            onDelete = SET_NULL
        )
    ]
)
data class Aisle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val storeId: Int,
    val name: String
)
