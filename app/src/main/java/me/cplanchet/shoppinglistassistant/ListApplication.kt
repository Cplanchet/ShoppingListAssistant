package me.cplanchet.shoppinglistassistant

import android.app.Application
import me.cplanchet.shoppinglistassistant.data.AppContainer
import me.cplanchet.shoppinglistassistant.data.AppDataContainer

class ListApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}