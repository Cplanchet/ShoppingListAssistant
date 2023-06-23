package me.cplanchet.shoppinglistassistant.ui.createlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.ListUIState
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.StandardDropdownBox
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun CreateListPage(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    viewModel: CreateListViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(hasBackButton = true)
        }
    ){ paddingValues ->
        Column(
           modifier = Modifier.padding(paddingValues).then(Modifier.padding(top = 32.dp))
        ) {
            Text(
                text = stringResource(R.string.create_list_title)
            )
            FormBody(viewModel.listUIState)
            Row(
                modifier = modifier.fillMaxWidth().padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                OutlinedButton(
                    onClick = {},
                ){
                    Text(text = stringResource(R.string.back))
                }
                Button(
                    onClick = {}
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
    modifier: Modifier = Modifier,
    onValueChange: (ListUIState) -> Unit = {}
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
        val options = listOf("option 1", "option2", "option3")
        var selectedText by remember { mutableStateOf(options[0])}
    StandardDropdownBox(
        Modifier.fillMaxWidth(),
        selected = selectedText,
        onSelectionChanged = { selectedText = it},
        options = options,
        label = {Text(text = stringResource(R.string.choose_store))}
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
        CreateListPage(navigateBack =  {}, onNavigateUp = {}, viewModel = CreateListViewModel(MockShoppingListRepository()))
    }
}