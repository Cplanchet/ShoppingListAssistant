package me.cplanchet.shoppinglistassistant.ui.createlist

import me.cplanchet.shoppinglistassistant.data.dtos.StoreDto

data class CreateListUIState(val stores: List<StoreDto> = listOf())
