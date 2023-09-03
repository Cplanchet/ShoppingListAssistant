package me.cplanchet.shoppinglistassistant.ui.listdetail

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.cplanchet.shoppinglistassistant.R
import me.cplanchet.shoppinglistassistant.data.dtos.ListItemDto
import me.cplanchet.shoppinglistassistant.ui.AppViewModelProvider
import me.cplanchet.shoppinglistassistant.ui.components.AppBar
import me.cplanchet.shoppinglistassistant.ui.components.AutocompleteTextbox
import me.cplanchet.shoppinglistassistant.ui.theme.ShoppingListAssistantTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDetailPage(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    navigateToUpdateListPage: (listId: Int) -> Unit,
    navigateToUpdateItemPage: (listId: Int, itemId: Int) -> Unit,
    listDetailViewModel: ListDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState = listDetailViewModel.listUIState.collectAsState()
    val itemsState = listDetailViewModel.itemsUIState.collectAsState()
    var addItem by remember { mutableStateOf(false)}
    val coroutineScope = rememberCoroutineScope()
    var itemToDelete by remember { mutableStateOf<ListItemDto?>(null)}
    val lazyListState = rememberLazyListState();

    Scaffold(
        topBar = {AppBar(hasBackButton = true, navigateUp = { onNavigateUp() })}
    ){paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues).then(Modifier.padding(top = 32.dp, start = 16.dp))
        ) {
            Text(
                modifier = Modifier.clickable(onClick = {navigateToUpdateListPage(listDetailViewModel.listId)}),
                text = uiState.value.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary)
            if(uiState.value.store != null){
                Text(
                    modifier = Modifier.clickable(onClick = {navigateToUpdateListPage(listDetailViewModel.listId)}),
                    text = uiState.value.store!!.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary)
            }
            LazyColumn(
                modifier = Modifier.padding(end = 16.dp, top = 16.dp),
                state = lazyListState,
            ){
                items(
                    items = uiState.value.items.sortedBy { it.order },
                    key = {item -> item.order}
                    ) { item ->
                    ListItem(
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                coroutineScope.launch{
                                listDetailViewModel.updateListItem(item.copy(checked = !item.checked))
                            } },
                            onLongClick = { itemToDelete = item },
                            onLongClickLabel = stringResource(R.string.click_item_context)
                        ),
                        listItem = item,
                        onCheckedChanged ={
                            coroutineScope.launch{
                                listDetailViewModel.updateListItem(item.copy(checked = it))
                            }
                        },
                        onEditButtonPressed = {
                            navigateToUpdateItemPage(listDetailViewModel.listId, it)
                        },
                        state = lazyListState
                    )
                }
            }
            if(addItem){
                AddItemSection(
                    onSubmit = {
                        val item = itemsState.value.items.find { item -> item.name.equals(it, ignoreCase = true) }
                        if(item != null){
                            coroutineScope.launch {
                                listDetailViewModel.addItemToList(item)
                            }
                        }
                        else{
                            coroutineScope.launch {
                                listDetailViewModel.itemToAdd = it
                                listDetailViewModel.createItem(it)
                            }
                        }
                        addItem = false
                   },
                    onCancel = {addItem = false},
                    options = itemsState.value.items.map { it.name }.filter { item -> listDetailViewModel.listUIState.value.items.find{added -> added.item.name == item} == null}
                )
            } else{
                TextButton(onClick = {addItem = true}){
                    Text(text = stringResource(R.string.list_detail_button_label))
                }
            }

            if(itemToDelete != null){
                DeleteDialog(
                    onConfirm = {
                        coroutineScope.launch {
                            listDetailViewModel.deleteListItem(itemToDelete!!)
                            itemToDelete = null
                        }
                    },
                    onDismiss = { itemToDelete = null }
                )
            }
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    onCheckedChanged: (checked: Boolean) -> Unit,
    onEditButtonPressed: (itemId: Int) -> Unit,
    listItem: ListItemDto,
    state: LazyListState
){
    var offset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }
    val coroutineScope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().offset{ offset.value.toIntOffset() }
//            .draggable(
//                orientation = Orientation.Vertical,
//                state = rememberDraggableState { delta ->
//                    offsetY += delta
//                }
//            )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ){
            var currentIndexOfDraggedItem by remember { mutableStateOf(0) }
            Icon(
                painter = painterResource(R.drawable.baseline_drag_indicator_24),
                contentDescription = "Drag Icon",
                modifier = Modifier.pointerInput(Unit){
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            //Use Lazy List State to get information about current and Hovered item
                            /*
                            Setting Current:
                            
                             lazyListState.layoutInfo.visibleItemsInfo
                                .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
                                ?.also {
                                    currentIndexOfDraggedItem = it.index
                                    initiallyDraggedElement = it
                                }

                            Checking Hover:

                             currentElement?.let { hovered ->
                                lazyListState.layoutInfo.visibleItemsInfo
                                    .filterNot { item -> item.offsetEnd < startOffset || item.offset > endOffset || hovered.index == item.index }
                                    .firstOrNull { item ->
                                        val delta = startOffset - hovered.offset
                                        when {
                                            delta > 0 -> (endOffset > item.offsetEnd)
                                            else -> (startOffset < item.offset)
                                        }
                                    }
                                    ?.also { item ->
                                        currentIndexOfDraggedItem?.let { current -> onMove.invoke(current, item.index) }
                                        currentIndexOfDraggedItem = item.index
                                    }
                            * */
                            change.consume()
                            val position = Offset(offset.value.x, offset.value.y + dragAmount.y)
                            coroutineScope.launch{
                                offset.snapTo(position)
                            }
                        },
                        onDragStart = {
                            state.layoutInfo.visibleItemsInfo.firstOrNull{
                                    item -> item.key == listItem.order
                            }?.also {
                                currentIndexOfDraggedItem = it.index
                            }
                        },
                        onDragEnd = {
                            coroutineScope.launch{
                                offset.animateTo(Offset(0f, 0f))
                            }
                        }
                    )
                }
            )
            Checkbox(
                checked = listItem.checked,
                onCheckedChange = { onCheckedChanged(it) }
            )
            Text(
                text = listItem.item.name + "  " + "(" + listItem.amount + " " + listItem.amountUnit + ")",
                textDecoration = if(listItem.checked) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            IconButton(onClick = { onEditButtonPressed(listItem.item.id) }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit icon")
            }
        }

    }
}

@Composable
fun AddItemSection(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onSubmit: (itemName: String) -> Unit,
    options: List<String>
){
    var empty by remember { mutableStateOf(true)}
    var resultText by remember {mutableStateOf("")}
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        AutocompleteTextbox(
            options = options,
            onSelectionChanged = { onSubmit(it) },
            text = resultText,
            onTextChange = {
                resultText = it
                empty = resultText == ""
            }
        ){
            Text(text = "Add Item")
        }
        if(empty){
            IconButton(
                onClick = { onCancel() },
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Add Item",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        else{
            IconButton(
                onClick = {onSubmit(resultText)}
            ){
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Add Item",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
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
                    text = "Are you sure you want to delete this item?"
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
        AddItemSection(onSubmit =  {}, onCancel = {}, options = listOf("Apple", "Orange", "Banana"))
    }
}
private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())