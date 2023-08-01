package me.cplanchet.shoppinglistassistant.ui.updatelist

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.StoreDropdownBox
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun UpdateListPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
){
    Scaffold(
        topBar = { AppBar(hasBackButton = true, navigateUp = { onNavigateUp() }) }
    ) {
        Column(
            modifier = modifier.fillMaxWidth().padding(it).then(Modifier.padding(top = 32.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(R.string.update_list_title),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                modifier = Modifier.fillMaxWidth(.8f).padding(top= 16.dp, ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = {},
                    label = {Text(text = stringResource(R.string.list_name_label))}
                )
                StoreDropdownBox(
                    modifier = Modifier.padding(top = 8.dp),
                    options = emptyList(),
                    onSelectCreate = {},
                    onSelectionChanged = {},
                    label = {Text(text = stringResource(R.string.choose_store))},
                    selected = ""
                )
            }
            Row(
                modifier.fillMaxWidth(.8f).padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                OutlinedButton(
                    modifier = Modifier.widthIn(min= 130.dp),
                    onClick = {}
                ){
                    Text(text = "Cancel")
                }
                Button(
                    modifier = Modifier.widthIn(min = 130.dp),
                    onClick = {}
                ){
                    Text(text = "Save")
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "light mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark mode"
)
@Composable
fun UpdateListPreview(){
    ShoppingListAssistantTheme {
        UpdateListPage(onNavigateUp = {})
    }
}

