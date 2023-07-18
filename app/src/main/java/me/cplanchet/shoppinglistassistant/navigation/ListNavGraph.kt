package me.cplanchet.shoppinglistassistant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.cplanchet.shoppinglistassistant.navigation.destinations.CreateListDestination
import me.cplanchet.shoppinglistassistant.navigation.destinations.CreateStoreDestination
import me.cplanchet.shoppinglistassistant.navigation.destinations.HomeDestination
import me.cplanchet.shoppinglistassistant.navigation.destinations.ListDetailDestination
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListPage
import me.cplanchet.shoppinglistassistant.ui.createstore.CreateStorePage
import me.cplanchet.shoppinglistassistant.ui.home.HomeScreen
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailPage

@Composable
fun ListNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = ListDetailDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDestination.route){
            HomeScreen(navigateToCreateList = {navController.navigate(CreateListDestination.route)})
        }
        composable(route = CreateListDestination.route){
            CreateListPage(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                navigateToCreateStorePage = { navController.navigate(CreateStoreDestination.route) }
            )
        }
        composable(route = CreateStoreDestination.route){
            CreateStorePage(
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()}
            )
        }
        composable(route = ListDetailDestination.route){
            ListDetailPage()
        }
    }
}