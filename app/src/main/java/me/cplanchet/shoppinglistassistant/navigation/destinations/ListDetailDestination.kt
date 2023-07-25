package me.cplanchet.shoppinglistassistant.navigation.destinations

import me.cplanchet.shoppinglistassistant.R

object ListDetailDestination: NavigationDestination {
    override val route = "detail"
    override val titleRes = R.string.list_detail_title
    const val listIdArg = "listId"
    val routeWithArgs = "$route/{$listIdArg}"
}