package me.cplanchet.shoppinglistassistant.navigation.destinations

import me.cplanchet.shoppinglistassistant.R

object UpdateCategoryDestination : NavigationDestination {
    override val route: String = "updatecategory"
    override val titleRes: Int = R.string.update_category_title
    val categoryIdArg: String = "categoryId"
    val routeWithArgs = "$route/{$categoryIdArg}"
}