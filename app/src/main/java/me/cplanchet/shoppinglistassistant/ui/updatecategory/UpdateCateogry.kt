package me.cplanchet.shoppinglistassistant.ui.updatecategory

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun UpdateCategoryPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateBack: () -> Unit
){
    var temp by remember{mutableStateOf("")}
    Scaffold(topBar = { AppBar(hasBackButton = true, navigateUp = onNavigateUp) }){ paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.update_category_title),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = temp,
                onValueChange = { temp = it },
                label = { Text( text = stringResource(R.string.create_category_label_name)) }
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onError,
                    containerColor = MaterialTheme.colorScheme.error),
                ){
                Text(text = stringResource(R.string.delete))
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                ){
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    onClick = {},
                    modifier = Modifier
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
fun UpdateCategoryPreview(){
    ShoppingListAssistantTheme {
        UpdateCategoryPage(onNavigateUp = {}, navigateBack = {})
    }
}