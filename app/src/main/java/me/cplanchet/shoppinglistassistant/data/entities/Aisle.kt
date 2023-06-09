package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Aisle(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val storeId: Int,
    val name: String
)
