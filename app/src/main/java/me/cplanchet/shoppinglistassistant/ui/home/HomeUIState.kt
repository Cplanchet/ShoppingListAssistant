package me.cplanchet.shoppinglistassistant.ui.home

import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto

data class HomeUIState(val shoppingLists: List<ShoppingListDto> = listOf())