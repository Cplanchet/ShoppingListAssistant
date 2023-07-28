package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val categoryId: Int?
)