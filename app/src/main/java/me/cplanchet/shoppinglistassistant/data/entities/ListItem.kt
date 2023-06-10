package me.cplanchet.shoppinglistassistant.data.entities

import androidx.room.Entity

@Entity(tableName = "List_Item", primaryKeys = ["listId", "itemId"])
data class ListItem(
    val listId: Int,
    val itemId: Int,
    val amount: Float,
    val amountUnit: String,
    val checked: Boolean
)
