package me.cplanchet.shoppinglistassistant.ui.updatecategory

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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun UpdateCategoryPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit,
    updateCategoryViewModel: UpdateCategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var dialogOpen by remember { mutableStateOf(false) }

    Scaffold(topBar = { AppBar(hasBackButton = true, navigateUp = onNavigateUp) }) { paddingValues ->
        if (dialogOpen) {
            DeleteDialog(
                onDismiss = { dialogOpen = false },
                onConfirm = {
                    coroutineScope.launch {
                        updateCategoryViewModel.deleteCategory()
                    }
                    dialogOpen = false
                    navigateBack()
                }
            )
        }
        Column(
            modifier = modifier.padding(paddingValues).then(Modifier.padding(all = 32.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.update_category_title),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = updateCategoryViewModel.categoryUIState.name,
                onValueChange = {
                    updateCategoryViewModel.updateCategoryUIState(
                        updateCategoryViewModel.categoryUIState.copy(name = it)
                    )
                },
                label = { Text(text = stringResource(R.string.create_category_label_name)) },
            )
            Button(
                onClick = {
                    dialogOpen = true
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onError,
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.widthIn(min = 100.dp)
            ) {
                Text(text = stringResource(R.string.delete))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        navigateBack()
                    },
                    modifier = Modifier.widthIn(min = 100.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            updateCategoryViewModel.saveCategory()
                        }
                        navigateBack()
                    },
                    modifier = Modifier.widthIn(min = 100.dp)
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.wrapContentWidth().wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.update_category_delete_dialog_content))
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onDismiss()
                    },
                ) {
                    Text(stringResource(R.string.dismiss))
                }
                TextButton(
                    onClick = {
                        onConfirm()
                    },
                ) {
                    Text(stringResource(R.string.confirm))
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
fun UpdateCategoryPreview() {
    ShoppingListAssistantTheme {
        UpdateCategoryPage(onNavigateUp = {}, navigateBack = {})
    }
}