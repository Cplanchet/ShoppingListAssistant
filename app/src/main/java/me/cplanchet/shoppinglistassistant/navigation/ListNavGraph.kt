package me.cplanchet.shoppinglistassistant.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.cplanchet.shoppinglistassistant.navigation.destinations.CreateListDestination
import me.cplanchet.shoppinglistassistant.navigation.destinations.HomeDesitination
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListPage
import me.cplanchet.shoppinglistassistant.ui.home.HomeScreen

@Composable
fun ListNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = HomeDesitination.route,
        modifier = modifier
    ){
        composable(route = HomeDesitination.route){
            HomeScreen(navigateToCreateList = {navController.navigate(CreateListDestination.route)})
        }
        composable(route = CreateListDestination.route){
            CreateListPage(
                navigateBack = {navController.popBackStack()},
                onNavigateUp =  {navController.navigateUp()}
            )
        }
    }
}