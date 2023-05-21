package me.cplanchet.shoppinglistassistant.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun HomeScreen(modifier: Modifier = Modifier)
{
    Text("Hello World")
}

@Composable
fun ListCard(
    modifier: Modifier = Modifier,
    list: ShoppingList
 ) {
    OutlinedCard(
        //TODO: Make clickable
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier.padding(top = 0.dp, start = 16.dp, bottom = 0.dp, end = 16.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = list.name,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(4.dp)
                )
                IconButton(
                    onClick = { /*TODO: add delete function*/ }
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "delete button", tint = Color.Black)
                }
            }
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(list.items.size >= 3){
                    for(i in 1..3){
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

@Preview
@Composable
fun listCardPreview(){
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
    val shoppingList1 = ShoppingList(1, "name", listItems, null)
    ListCard(Modifier, shoppingList1);
}