package me.cplanchet.shoppinglistassistant.ui.updatelist

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.LinkDropDownBox
import me.cplanchet.shoppinglistassistant.ui.state.isValid
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun UpdateListPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    navigateToCreateStorePage: () -> Unit,
    viewModel: UpdateListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val updateListViewModel by viewModel.updateListUIState.collectAsState()

    Scaffold(
        topBar = { AppBar(hasBackButton = true, navigateUp = { onNavigateUp() }) }
    ) { padding ->
        Column(
            modifier = modifier.fillMaxWidth().padding(padding).then(Modifier.padding(top = 32.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.update_list_title),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                modifier = Modifier.fillMaxWidth(.8f).padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.listUIState.name,
                    onValueChange = { viewModel.updateListState(viewModel.listUIState.copy(name = it)) },
                    label = { Text(text = stringResource(R.string.list_name_label)) }
                )
                LinkDropDownBox(
                    modifier = Modifier.padding(top = 8.dp),
                    options = updateListViewModel.stores.map { it.name }.plus("None"),
                    onLinkSelected = { navigateToCreateStorePage() },
                    onSelectionChanged = {
                        viewModel.updateListState(viewModel.listUIState.copy(
                            store = updateListViewModel.stores.find { store -> store.name == it }
                        ))
                    },
                    label = { Text(text = stringResource(R.string.choose_store)) },
                    selected = viewModel.listUIState.store?.name ?: "None",
                    linkText = stringResource(R.string.create_store_title)
                )
            }
            Row(
                modifier.fillMaxWidth(.8f).padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = Modifier.widthIn(min = 130.dp),
                    onClick = { navigateBack() },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    modifier = Modifier.widthIn(min = 130.dp),
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveList()
                            navigateBack()
                        }
                    },
                    enabled = viewModel.listUIState.isValid()
                ) {
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
fun UpdateListPreview() {
    ShoppingListAssistantTheme {
        UpdateListPage(onNavigateUp = {}, navigateBack = {}, navigateToCreateStorePage = {})
    }
}

