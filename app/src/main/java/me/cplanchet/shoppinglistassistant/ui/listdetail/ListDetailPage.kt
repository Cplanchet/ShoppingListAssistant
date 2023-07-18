package me.cplanchet.shoppinglistassistant.ui.listdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.CategoryDto
import me.cplanchet.shoppinglistassistant.data.dtos.ItemDto
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun ListDetailPage(){
    val repo = MockShoppingListRepository()
    val category = CategoryDto(1, "Cat")
    val item = ItemDto(1, "Name", category)
    val listItem = ListItemDto(item, 1f, "lb", false)
    ListItem(listItem = listItem)
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
            verticalAlignment = Alignment.CenterVertically
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
            verticalAlignment = Alignment.CenterVertically
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
        ListDetailPage()
    }
}