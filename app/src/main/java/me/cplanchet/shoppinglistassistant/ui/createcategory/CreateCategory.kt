package me.cplanchet.shoppinglistassistant.ui.createcategory

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun CreateCategoryPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: CreateCategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(hasBackButton = true, navigateUp = { onNavigateUp() })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .then(Modifier.padding(all = 32.dp).fillMaxWidth()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            val coroutineScope = rememberCoroutineScope()
            Text(
                text = stringResource(R.string.create_category_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            OutlinedTextField (
                value = viewModel.categoryUIState.name,
                onValueChange = { viewModel.updateUIState(viewModel.categoryUIState.copy(name = it))},
                label = {Text(stringResource(R.string.create_category_label_name))},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        coroutineScope.launch {
                            viewModel.saveCategory()
                        }
                        navigateBack()
                    }
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                OutlinedButton(
                    onClick = {
                      navigateBack();
                    },
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(100.dp)
                ){
                    Text(stringResource(R.string.back))
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveCategory()
                        }
                        navigateBack()
                    },
                    modifier = Modifier.width(100.dp)
                ){
                    Text(stringResource(R.string.save))
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
fun CreateStorePreview(){
    ShoppingListAssistantTheme {
        CreateCategoryPage(
            onNavigateUp = {},
            navigateBack = {},
            viewModel = CreateCategoryViewModel(MockShoppingListRepository())
        )
    }
}