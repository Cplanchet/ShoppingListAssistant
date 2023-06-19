package me.cplanchet.shoppinglistassistant.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    hasBackButton: Boolean,
    navigateUp: () -> Unit = {}
){
    if(hasBackButton){
        TopAppBar(
            title = { Text(stringResource(R.string.app_title))},
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back button"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,

            )
        )
    }
    else{
        TopAppBar(
            title = {Text(stringResource(R.string.app_title))},
            modifier = modifier,
            colors = TopAppBarDefaults.smallTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,

            ))
    }
}

@Preview(
    uiMode= UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
fun Preview(){
    ShoppingListAssistantTheme {
        AppBar(hasBackButton = false)
    }
}