package me.cplanchet.shoppinglistassistant.navigation.destinations

import me.cplanchet.shoppinglistassistant.R

object UpdateItemDestination: NavigationDestination {
    override val route = "update"
    override val titleRes= R.string.update_item_title
    const val listIdArg = "listId"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$listIdArg}/item/{$itemIdArg}"
}