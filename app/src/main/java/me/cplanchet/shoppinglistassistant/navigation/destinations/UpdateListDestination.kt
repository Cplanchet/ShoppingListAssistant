package me.cplanchet.shoppinglistassistant.navigation.destinations

import me.cplanchet.shoppinglistassistant.R

object UpdateListDestination: NavigationDestination {
    override val route = "listUpdate"
    override val titleRes = R.string.update_list_title
    const val listIdArg = "listId"
    val routeWithArgs = "$route/{$listIdArg}"
}