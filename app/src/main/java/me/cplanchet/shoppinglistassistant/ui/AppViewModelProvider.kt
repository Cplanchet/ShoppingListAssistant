package me.cplanchet.shoppinglistassistant.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import me.cplanchet.shoppinglistassistant.ListApplication
import me.cplanchet.shoppinglistassistant.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            HomeViewModel(
                ListApplication().containter.shoppingListRepository
            )
        }
    }
}