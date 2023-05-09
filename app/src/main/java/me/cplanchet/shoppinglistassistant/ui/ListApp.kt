package me.cplanchet.shoppinglistassistant.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.cplanchet.shoppinglistassistant.navigation.ListNavHost

@Composable
fun ListApp(navController: NavHostController = rememberNavController()){
    ListNavHost(navController = navController)
}