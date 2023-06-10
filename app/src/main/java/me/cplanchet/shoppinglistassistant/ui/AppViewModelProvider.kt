package me.cplanchet.shoppinglistassistant.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import me.cplanchet.shoppinglistassistant.ListApplication
import me.cplanchet.shoppinglistassistant.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            HomeViewModel(
                listApplication().container.shoppingListService
            )
        }
    }
}

fun CreationExtras.listApplication(): ListApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ListApplication)
