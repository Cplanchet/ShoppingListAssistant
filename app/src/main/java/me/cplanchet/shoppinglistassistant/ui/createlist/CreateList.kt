package me.cplanchet.shoppinglistassistant.ui.createlist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.ListUIState
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun ListEntryPage(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier,
    canNavigateBack: Boolean = true,
    viewModel: CreateListViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    Text("Create Page works")
}

@OptIn(ExperimentalMaterialApi::class)
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
//        ExposedDropdownMenuBox(
//            expanded = false,
//            onExpandedChange = {}
//        ){
//            OutlinedTextField(
//                value = listUIState.name,
//                onValueChange = {onValueChange(listUIState.copy(name = it))},
//                label = {Text(stringResource(R.string.list_name_label))},
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
//        }
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
        FormBody(ListUIState())
    }
}