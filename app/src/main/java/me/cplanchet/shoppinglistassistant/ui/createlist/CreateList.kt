package me.cplanchet.shoppinglistassistant.ui.createlist

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.LinkDropDownBox
import me.cplanchet.shoppinglistassistant.ui.state.ListUIState
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CreateListPage(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    navigateToCreateStorePage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateListViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val createListUIState by viewModel.createListUIState.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(hasBackButton = true, navigateUp = {onNavigateUp()},)
        }
    ){ paddingValues ->
        Column(
           modifier = Modifier.padding(paddingValues).then(Modifier.padding(top = 32.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.create_list_title),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            FormBody(
                listUIState = viewModel.listUIState,
                createListUIState = createListUIState,
                onValueChange = viewModel::updateUIState,
                modifier = Modifier.padding(top = 32.dp),
                onCreateStore = { navigateToCreateStorePage() }
            )
            Row(
                modifier = modifier.fillMaxWidth().padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                OutlinedButton(
                    onClick = { navigateBack() },
                    modifier.width(100.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ){
                    Text(text = stringResource(R.string.back))
                }
                Button(
                    onClick = {
                              coroutineScope.launch{
                                  viewModel.saveList()
                                  navigateBack()
                              }
                    },
                    modifier.width(100.dp),
                    enabled = viewModel.listUIState.isValid()
                ){
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}

@Composable
fun FormBody(
    listUIState: ListUIState,
    createListUIState: CreateListUIState,
    modifier: Modifier = Modifier,
    onValueChange: (ListUIState) -> Unit,
    onCreateStore: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        OutlinedTextField(
            value = listUIState.name,
            onValueChange = {onValueChange(listUIState.copy(name = it))},
            label = {Text(stringResource(R.string.list_name_label))},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        val options = createListUIState.stores.map { it.name }
        var selectedText by remember { mutableStateOf("Select Store...") }

        LinkDropDownBox(
            modifier = Modifier.fillMaxWidth(),
            selected = selectedText,
            onSelectionChanged = {
                selectedText = it
                onValueChange(listUIState.copy(store = createListUIState.stores.find { store -> store.name == it }))
            },
            onLinkSelected = { onCreateStore() },
            options = options,
            label = { Text(text = stringResource(R.string.choose_store)) },
            linkText = stringResource(R.string.store_dropdown_create)
        )
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
fun CreateListPreview(){
    ShoppingListAssistantTheme {
        CreateListPage(
            navigateBack = {},
            onNavigateUp = {},
            navigateToCreateStorePage = {},
            viewModel = CreateListViewModel(MockShoppingListRepository())
        )
    }
}