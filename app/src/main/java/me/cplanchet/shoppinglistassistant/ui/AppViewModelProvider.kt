package me.cplanchet.shoppinglistassistant.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import me.cplanchet.shoppinglistassistant.ListApplication
import me.cplanchet.shoppinglistassistant.ui.createlist.CreateListViewModel
import me.cplanchet.shoppinglistassistant.ui.createstore.CreateStoreViewModel
import me.cplanchet.shoppinglistassistant.ui.home.HomeViewModel
import me.cplanchet.shoppinglistassistant.ui.listdetail.ListDetailViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory{
        initializer {
            HomeViewModel(
                listApplication().container.shoppingListRepository
            )
        }
        initializer {
            CreateListViewModel(
                listApplication().container.shoppingListRepository
            )
        }
        initializer {
            CreateStoreViewModel(
                listApplication().container.shoppingListRepository
            )
        }
        initializer {
            ListDetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                listRepository = listApplication().container.shoppingListRepository
            )
        }
    }
}

fun CreationExtras.listApplication(): ListApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ListApplication)
