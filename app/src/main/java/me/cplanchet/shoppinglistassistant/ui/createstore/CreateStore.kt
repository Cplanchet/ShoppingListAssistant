package me.cplanchet.shoppinglistassistant.ui.createstore

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
import me.cplanchet.shoppinglistassistant.ui.state.StoreUIState
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun CreateStorePage(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateStoreViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(hasBackButton = true, navigateUp = { onNavigateUp() })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .then(Modifier.padding(top = 32.dp, end = 32.dp, bottom = 32.dp, start = 32.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_store_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            CreateStoreFormBody(
                storeUIState = viewModel.storeUIState,
                onValueChanged = { viewModel.updateUIState(it) },
                onSubmit = {
                    coroutineScope.launch {
                        viewModel.saveStore()
                        navigateBack()
                    }
                },
                onBackButtonPressed = { navigateBack() }
            )
        }
    }
}

@Composable
fun CreateStoreFormBody(
    modifier: Modifier = Modifier,
    storeUIState: StoreUIState,
    onValueChanged: (StoreUIState) -> Unit,
    onSubmit: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = storeUIState.name,
            onValueChange = { onValueChanged(storeUIState.copy(name = it)) },
            label = { Text(stringResource(R.string.store_name_label)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { onSubmit() })
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                modifier = Modifier.width(100.dp),
                onClick = { onBackButtonPressed() },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.back))
            }
            Button(
                modifier = Modifier.width(100.dp),
                onClick = { onSubmit() },
            ) {
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
fun CreateStorePreview() {
    ShoppingListAssistantTheme {
        CreateStorePage(
            viewModel = CreateStoreViewModel(MockShoppingListRepository()),
            navigateBack = {},
            onNavigateUp = {}
        )
    }
}