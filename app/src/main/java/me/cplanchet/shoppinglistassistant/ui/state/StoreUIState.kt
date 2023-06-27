package me.cplanchet.shoppinglistassistant.ui.state

import me.cplanchet.shoppinglistassistant.data.dtos.AisleDto
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.StoreDto

data class StoreUIState(
    val id: Int = 0,
    val name: String = "",
    val items: List<ItemDto> = listOf(),
    val aisles: List<AisleDto> = listOf()
)

fun StoreDto.toUIState(): StoreUIState{
    return StoreUIState(this.id, this.name, this.items, this.aisle)
}