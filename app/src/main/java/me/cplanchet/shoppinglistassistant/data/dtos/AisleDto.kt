package me.cplanchet.shoppinglistassistant.data.dtos

data class AisleDto(
    val id: Int,
    val name: String,
    val categories: List<CategoryDto>,
    val items: List<ItemDto>
)
