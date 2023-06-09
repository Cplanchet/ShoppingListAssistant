package me.cplanchet.shoppinglistassistant.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.MockShoppingListRepository
import me.cplanchet.shoppinglistassistant.data.dtos.ShoppingListDto
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToCreateList: () -> Unit
){
    val coroutineScope = rememberCoroutineScope()
    val homeUIState by homeViewModel.homeUIState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    var listToDelete = remember { mutableStateOf(ShoppingListDto(1, "", listOf(), null)) }
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(hasBackButton = false)
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToCreateList() },
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
            if(openDialog.value){
                DeleteDialog(
                    onDismiss = {openDialog.value = false},
                    onConfirm = {
                        coroutineScope.launch {
                            homeViewModel.deleteList(listToDelete.value)
                            openDialog.value = false
                        }
                    },
                    listDto = listToDelete.value
                )
            }
            LazyColumn(
                modifier = modifier.padding(it).then(Modifier.padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 32.dp))
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                items(homeUIState.shoppingLists){list ->
                    ListCard(list = list, onListDelete = {
                        listToDelete.value = it
                        openDialog.value = true
                    })
                }
            }
        }
    )
}

@Composable
fun ListCard(
    modifier: Modifier = Modifier,
    list: ShoppingListDto,
    onListDelete: (ShoppingListDto) -> Unit
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
                    text = if (list.name.length > 15) list.name.take(15) + "..." else list.name,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                IconButton(
                    onClick = { onListDelete(list) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete button",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    listDto: ShoppingListDto,
    modifier: Modifier = Modifier,
){
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Are you sure you want to delete " + listDto.name + "?"
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    TextButton(
                        onClick = {
                            onDismiss()
                        },
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = {
                            onConfirm()
                        },
                    ) {
                        Text("Confirm")
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
    val test = HomeViewModel(listRepository = MockShoppingListRepository());
    ShoppingListAssistantTheme {
        HomeScreen(
            homeViewModel = test,
            navigateToCreateList = {}
            )
    }
}