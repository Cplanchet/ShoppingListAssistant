package me.cplanchet.shoppinglistassistant.data.dtos

data class StoreDto(
    val id: Int,
    val name: String,
    val aisle: List<AisleDto>
)
