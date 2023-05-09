package me.cplanchet.shoppinglistassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListAssistantTheme {
                ListApp()
            }
        }
    }
}