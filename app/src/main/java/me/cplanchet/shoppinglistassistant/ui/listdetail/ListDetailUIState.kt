package me.cplanchet.shoppinglistassistant.ui.listdetail

import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto

data class ListDetailUIState(val items: MutableList<ItemDto> = mutableListOf())