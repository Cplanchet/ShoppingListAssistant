package me.cplanchet.shoppinglistassistant.ui.listdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun ListDetailPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    list: ShoppingListDto?
){
    Scaffold(
        topBar = {AppBar(hasBackButton = true, navigateUp = {onNavigateUp()})}
    ){paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues).then(Modifier.padding(top = 32.dp, start = 16.dp))
        ) {
            Text(text = list?.name ?: "", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            if(list?.store != null){
                Text(text = list.store.name, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            }
            LazyColumn(
                modifier = Modifier.padding(end = 16.dp, top = 16.dp)
            ){
                items(list?.items ?: listOf()) {item ->
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
    ShoppingListAssistantTheme {
        val category = CategoryDto(1, "fakeCategory")

        val item1 = ItemDto(1, "fake item 1", category)
        val item2 = ItemDto(2, "fake item 2", category)
        val item3 = ItemDto(3, "fake item 3", category)
        val item4 = ItemDto(4, "fake item 4", category)

        val listItem1 = ListItemDto(item1, 1f, "count", false)
        val listItem2 = ListItemDto(item2, 1f, "count", false)
        val listItem3 = ListItemDto(item3, 1f, "count", false)
        val listItem4 = ListItemDto(item4, 1f, "count", false)

        val shoppingListFourItems = ShoppingListDto(2, "Four Items", listOf(listItem1, listItem2, listItem3, listItem4), null)

        ListDetailPage(onNavigateUp = {}, list = shoppingListFourItems)
    }
}