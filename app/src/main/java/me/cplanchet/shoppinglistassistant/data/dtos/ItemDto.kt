package me.cplanchet.shoppinglistassistant.data.dtos



data class ItemDto (
    val id: Int = 0,
    val name: String = "",
    val category: CategoryDto? = null
)


