package me.cplanchet.shoppinglistassistant.ui.listdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun ListDetailPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    listDetailViewModel: ListDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState = listDetailViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {AppBar(hasBackButton = true, navigateUp = { onNavigateUp() })}
    ){paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues).then(Modifier.padding(top = 32.dp, start = 16.dp))
        ) {
            Text(text = uiState.value.name, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            if(uiState.value.store != null){
                Text(text = uiState.value.store!!.name, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            }
            LazyColumn(
                modifier = Modifier.padding(end = 16.dp, top = 16.dp)
            ){
                items(uiState.value.items) {item ->
                    ListItem(listItem = item)
                }
            }
            TextButton(onClick = {}){
                Text(text = stringResource(R.string.list_detail_button_label))
            }
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    listItem: ListItemDto
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ){
            Checkbox(
                checked = listItem.checked,
                onCheckedChange = {},
            )
            Text(
                text = listItem.item.name + "  " + "(" + listItem.amount + " " + listItem.amountUnit + ")",
                textDecoration = if(listItem.checked) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit icon")
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
fun ListDetailPreview(){
    val listDetailViewModel = ListDetailViewModel(listRepository =  MockShoppingListRepository(), savedStateHandle = SavedStateHandle())
    ShoppingListAssistantTheme {
        ListDetailPage(onNavigateUp = {}, listDetailViewModel = listDetailViewModel)
    }
}