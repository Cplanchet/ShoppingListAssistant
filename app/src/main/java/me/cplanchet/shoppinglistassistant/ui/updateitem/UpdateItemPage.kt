package me.cplanchet.shoppinglistassistant.ui.updateitem

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.LinkDropDownBox
import me.cplanchet.shoppinglistassistant.ui.state.ItemUIState
import me.cplanchet.shoppinglistassistant.ui.state.ListItemUIState
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun UpdateItemPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    navigateToCategoryCreatePage: () -> Unit,
    viewModel: UpdateItemViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val vm by viewModel.categoryUIState.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = { AppBar(hasBackButton = true, navigateUp = { onNavigateUp() }) }
    ) {
        var view by remember { mutableStateOf(0) }
        val tabNames = listOf("List", "Item")
        val coroutineScope = rememberCoroutineScope()

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
                    ListItemView(
                        onValueChange = {listItem -> viewModel.updateListItemUIState(listItem) },
                        onCancel = {navigateBack()},
                        onSave = {
                             coroutineScope.launch {
                                 viewModel.saveListItem()
                                 navigateBack()
                             }
                        },
                        onDelete = {
                           coroutineScope.launch{
                               navigateBack()
                               viewModel.removeListItem()
                           }
                        },
                        listItem = viewModel.listItemUIState
                    )
                }
                else{
                    ItemView(
                        item = viewModel.itemUIState,
                        options = vm.categories.map { category -> category.name },
                        onValueChange = { newItem -> viewModel.updateItemUIState(newItem)},
                        onSelectionChange = {
                            viewModel.updateItemUIState(viewModel.itemUIState.copy(category = vm.categories.firstOrNull{category -> category.name == it}))
                        },
                        onDelete = {
                           coroutineScope.launch{
                               navigateBack()
                               viewModel.deleteItem()
                           }
                        },
                        onCancel = { navigateBack() },
                        onSave = {
                            coroutineScope.launch{
                                viewModel.saveItem()
                                navigateBack()
                            }
                        },
                        onCreateCategory = {
                            navigateToCategoryCreatePage()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemView(
    modifier: Modifier = Modifier,
    item: ItemUIState,
    options: List<String>,
    onValueChange: (item:ItemUIState) -> Unit,
    onSelectionChange: (selection: String) -> Unit,
    onCreateCategory: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(.8f)
    ){
        Text(
            text = stringResource(R.string.edit_item_view_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            value = item.name,
            onValueChange = {onValueChange(item.copy(name = it))},
            label = {Text(stringResource(R.string.label_name))}
        )
        LinkDropDownBox(
            modifier = Modifier.padding(top = 8.dp),
            options = options,
            onSelectionChanged = { onSelectionChange(it) },
            selected = item.category?.name ?: "Select Category",
            label = {Text(stringResource(R.string.label_category))},
            linkText = stringResource(R.string.create_category_link),
            onLinkSelected = { onCreateCategory() }
        )

        Button(
            onClick = { onDelete() },
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
                onClick = { onCancel() },
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.cancel))
            }

            Button(
                onClick = { onSave() },
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun ListItemView(
    modifier: Modifier = Modifier,
    listItem: ListItemUIState,
    onValueChange: (item: ListItemUIState) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(.9f)
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
                onValueChange = { onValueChange(listItem.copy(amount = it.toFloat())) },
                label = {Text(stringResource(R.string.label_list_item_amount))},
                value = listItem.amount.toString(),
                modifier = Modifier.width(100.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                onValueChange = { onValueChange(listItem.copy(amountUnit = it)) },
                label = {Text(stringResource(R.string.label_list_item_unit))},
                value = listItem.amountUnit
            )
        }
        Button(
            onClick = { onDelete() },
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
                onClick = { onCancel() },
                modifier = Modifier.width(100.dp)
            ){
                Text(stringResource(R.string.cancel))
            }

            Button(
                onClick = { onSave() },
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
        UpdateItemPage(onNavigateUp = {}, viewModel = UpdateItemViewModel(MockShoppingListRepository(), SavedStateHandle()), navigateBack = {}, navigateToCategoryCreatePage = {})
    }
}