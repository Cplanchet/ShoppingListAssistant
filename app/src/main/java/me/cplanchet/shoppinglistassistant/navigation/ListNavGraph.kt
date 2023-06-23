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
        startDestination = CreateListDestination.route,
        modifier = modifier
    ){
        composable(route = HomeDesitination.route){
            HomeScreen()
        }
        composable(route = CreateListDestination.route){
            CreateListPage(
                navigateBack = {},
                onNavigateUp =  {}
            )
        }
    }
}