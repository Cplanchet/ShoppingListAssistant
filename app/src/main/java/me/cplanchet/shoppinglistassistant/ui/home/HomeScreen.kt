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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    val homeUIState by homeViewModel.homeUIState.collectAsState()
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
                items(homeUIState.shoppingLists){list ->
                    ListCard(list = list)
                }
            }
        }
    )
}

@Composable
fun ListCard(
    modifier: Modifier = Modifier,
    list: ShoppingListDto
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
    val viewModel = HomeViewModel(listRepository = MockShoppingListRepository());
    val homeUIState by viewModel.homeUIState.collectAsState()
    ShoppingListAssistantTheme {
        HomeScreen(homeViewModel = viewModel)
    }
}