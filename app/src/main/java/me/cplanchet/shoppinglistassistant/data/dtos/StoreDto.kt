package me.cplanchet.shoppinglistassistant.data.dtos

data class StoreDto(
    val id: Int = 0,
    val name: String = "",
    val items: List<ItemDto> = listOf(),
    val aisle: List<AisleDto> = listOf()
)
