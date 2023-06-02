package me.cplanchet.shoppinglistassistant.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.entities.Category
import me.cplanchet.shoppinglistassistant.data.entities.Item
import me.cplanchet.shoppinglistassistant.data.entities.ListItem
import me.cplanchet.shoppinglistassistant.data.entities.ShoppingList
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    lists: List<ShoppingList>,
){
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(hasBackButton = false)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {/*TODO add logic*/ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary

            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add Button",
                )
            }
        },
        content = {
            LazyColumn(
                modifier = modifier.padding(it).then(Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 32.dp))
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                items(lists){list ->
                    ListCard(list = list)
                }
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ListCard(
    modifier: Modifier = Modifier,
    list: ShoppingList
 ) {
    ElevatedCard(
        //TODO: Make clickable
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = modifier.padding(top = 0.dp, start = 16.dp, bottom = 0.dp, end = 16.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = list.name,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                IconButton(
                    onClick = { /*TODO: add delete function*/ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete button",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(list.items.size >= 3){
                    for(i in 0..2){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check_box_outline_blank_48px),
                                contentDescription = "check box"
                            )
                            Text(list.items[i].item.name)
                        }
                    }
                    if(list.items.size > 3){
                        Text("...")
                    }
                } else{
                    for(i in list.items){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check_box_outline_blank_48px),
                                contentDescription = "check box"
                            )
                            Text(i.item.name)
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "light mode"
)
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    name = "dark mode"
)
@Composable
fun HomeScreenPreview(){
//----------------------------------------MOCK DATA--------------------------------------------------
    val category = Category(1, "cat 1")
    val item1 = Item(1, "item1", category)
    val item2 = Item(2, "item2", category)
    val item3 = Item(3, "item3", category)
    val item4 = Item(4, "item4", category)

    val listItems = listOf(
        ListItem(item1, 1f, "count", false),
        ListItem(item2, 3f, "lb", false),
        ListItem(item3, 3f, "lb", false),
        ListItem(item4, 3f, "lb", false),
    )
    val listItems2 = listOf(
        ListItem(item1, 1f, "count", false),
        ListItem(item2, 3f, "count", false),
        ListItem(item3, 1f, "count", true)
    )
    val listItems3 = listOf(
        ListItem(item1, 1f, "count", false),
        ListItem(item2, 3f, "count", false),
    )
    val shoppingList1 = ShoppingList(1, "name", listItems, null)
    val shoppingList2 = ShoppingList(2, "name2", listItems2, null)
    val shoppingList3 = ShoppingList(3, "name3", listItems3, null)
    val shoppingList4 = ShoppingList(4, "name4", listOf<ListItem>(), null)
    val shoppingLists = listOf(
        shoppingList1,
        shoppingList2,
        shoppingList3,
        shoppingList4
    )
//-------------------------------------------------------------------------------------------------------
    ShoppingListAssistantTheme {
        HomeScreen(lists = shoppingLists)
    }
}