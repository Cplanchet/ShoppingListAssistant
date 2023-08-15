package me.cplanchet.shoppinglistassistant.ui.state

import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto

data class CategoryUIState(
    val id: Int = 0,
    val name: String = ""
)

fun CategoryUIState.toCategoryDto(): CategoryDto = CategoryDto(
    id = id,
    name = name
)

fun CategoryDto.toCategoryUIState(): CategoryUIState = CategoryUIState(
    id = id,
    name = name
)

fun CategoryUIState.isValid(): Boolean {
    return name.isNotBlank()
}