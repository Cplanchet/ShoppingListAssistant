package me.cplanchet.shoppinglistassistant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.cplanchet.shoppinglistassistant.navigation.destinations.*
import me.cplanchet.shoppinglistassistant.ui.createcategory.CreateCategoryPage
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListPage
import me.cplanchet.shoppinglistassistant.ui.createstore.CreateStorePage
import me.cplanchet.shoppinglistassistant.ui.home.HomeScreen
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailPage
import me.cplanchet.shoppinglistassistant.ui.updatecategory.UpdateCategoryPage
import me.cplanchet.shoppinglistassistant.ui.updateitem.UpdateItemPage
import me.cplanchet.shoppinglistassistant.ui.updatelist.UpdateListPage

@Composable
fun ListNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToCreateList = { navController.navigate(CreateListDestination.route) },
                navigateToListDetail = { navController.navigate("${ListDetailDestination.route}/${it}") }
            )
        }
        composable(route = CreateListDestination.route) {
            CreateListPage(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToCreateStorePage = { navController.navigate(CreateStoreDestination.route) }
            )
        }
        composable(route = CreateStoreDestination.route) {
            CreateStorePage(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(route = ListDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(ListDetailDestination.listIdArg) {
                type = NavType.IntType
            })
        ) {
            ListDetailPage(
                onNavigateUp = { navController.navigateUp() },
                navigateToUpdateListPage = { navController.navigate("${UpdateListDestination.route}/${it}") },
                navigateToUpdateItemPage = { listId, itemId -> navController.navigate("${UpdateItemDestination.route}/${listId}/item/${itemId}") }
            )
        }
        composable(route = UpdateListDestination.routeWithArgs,
            arguments = listOf(navArgument(UpdateListDestination.listIdArg) {
                type = NavType.IntType
            })
        ) {
            UpdateListPage(
                onNavigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() },
                navigateToCreateStorePage = { navController.navigate(CreateStoreDestination.route) }
            )
        }
        composable(
            route = UpdateItemDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UpdateItemDestination.listIdArg) { type = NavType.IntType },
                navArgument(UpdateItemDestination.itemIdArg) { type = NavType.IntType })
        ) {
            UpdateItemPage(
                onNavigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() },
                navigateToCategoryCreatePage = { navController.navigate(CreateCategoryDestination.route) },
                navigateToUpdateCategoryPage = { navController.navigate("${UpdateCategoryDestination.route}/${it}") }
            )
        }
        composable(route = CreateCategoryDestination.route) {
            CreateCategoryPage(
                onNavigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = UpdateCategoryDestination.routeWithArgs,
            arguments = listOf(navArgument(UpdateCategoryDestination.categoryIdArg) { type = NavType.IntType })
        ) {
            UpdateCategoryPage(
                onNavigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}