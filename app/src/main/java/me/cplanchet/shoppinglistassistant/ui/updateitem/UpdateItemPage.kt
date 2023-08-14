package me.cplanchet.shoppinglistassistant.ui.updateitem

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.StandardDropdownBox
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun UpdateItemPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
){
    Scaffold(
        modifier = modifier,
        topBar = { AppBar(hasBackButton = true, navigateUp = { onNavigateUp() }) }
    ) {
        var view by remember { mutableStateOf(0) }
        val tabNames = listOf("List", "Item")

        Column(
            modifier = Modifier.padding(it)
        ) {
            TabRow(selectedTabIndex = view){
                tabNames.forEachIndexed { index, title ->
                    Tab(
                        selected = view == index,
                        onClick = {view = index},
                        text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis)}
                    )
                }
            }
            Column(
                modifier.fillMaxWidth().padding(top= 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(view == 0){
                    ListItemView()
                }
                else{
                    ItemView()
                }
            }
        }
    }
}

@Composable
fun ItemView(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(.8f)
    ){
        Text(
            text = stringResource(R.string.edit_item_view_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = {Text(stringResource(R.string.label_name))}
        )
        StandardDropdownBox(
            modifier = Modifier.padding(top = 8.dp),
            options = listOf("test", "test"),
            onSelectionChanged = {},
            selected = "test",
            label = {Text(stringResource(R.string.label_category))}
        )

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onError,
                containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(top = 16.dp)
        ){
            Text(stringResource(R.string.label_delete_item))
        }
        Row(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            OutlinedButton(
                onClick = {},
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.cancel))
            }

            Button(
                onClick = {},
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun ListItemView(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(.9f)
    ) {
        Text(
            text = stringResource(R.string.edit_list_item_view_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ){
            OutlinedTextField(
                onValueChange = {},
                label = {Text(stringResource(R.string.label_list_item_amount))},
                value = "",
                modifier = Modifier.width(100.dp),

            )
            OutlinedTextField(
                onValueChange = {},
                label = {Text(stringResource(R.string.label_list_item_unit))},
                value = ""
            )
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onError,
                containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(top = 16.dp)
        ){
            Text(stringResource(R.string.label_remove_item))
        }
        Row(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            OutlinedButton(
                onClick = {},
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.cancel))
            }

            Button(
                onClick = {},
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.save))
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
fun UpdateItemPagePreview(){
    ShoppingListAssistantTheme {
        UpdateItemPage(onNavigateUp = {})
    }
}