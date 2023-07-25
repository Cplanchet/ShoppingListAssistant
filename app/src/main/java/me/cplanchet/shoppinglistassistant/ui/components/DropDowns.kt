package me.cplanchet.shoppinglistassistant.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.cplanchet.shoppinglistassistant.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardDropdownBox(
    modifier: Modifier = Modifier,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    selected: String,
    label: @Composable (() -> Unit)?
){
    var expanded by remember {mutableStateOf(false)}

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            readOnly = true,
            value = selected,
            onValueChange = {},
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = !expanded
            },
            modifier.fillMaxWidth()
        ) {
            options.forEach { selectedOption ->
                DropdownMenuItem(
                    text = { Text(text = selectedOption) },
                    onClick = {
                        onSelectionChanged(selectedOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDropdownBox(
    modifier: Modifier = Modifier,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    onSelectCreate: () -> Unit,
    selected: String,
    label: @Composable (() -> Unit)?
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            readOnly = true,
            value = selected,
            onValueChange = {},
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = !expanded
            },
            modifier.fillMaxWidth()
        ) {
            options.forEach { selectedOption ->
                DropdownMenuItem(
                    text = { Text(text = selectedOption) },
                    onClick = {
                        onSelectionChanged(selectedOption)
                        expanded = false
                    }
                )
            }
            DropdownMenuItem(
                text = { Text(stringResource(R.string.store_dropdown_create)) },
                onClick = {
                    onSelectCreate()
                    expanded = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutocompleteTextbox(
    modifier: Modifier = Modifier,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    label: @Composable (() -> Unit)
){
    var expanded by remember {mutableStateOf(false)}
    //var selectedText by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChange(it)},
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        val filteringOptions = options.filter { it. contains(text, ignoreCase = true) }
        if(filteringOptions.isNotEmpty()){
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = !expanded
                },
                modifier.fillMaxWidth()
            ) {
                filteringOptions.forEach { selectedOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectedOption) },
                        onClick = {
                            onSelectionChanged(selectedOption)
                            onTextChange(selectedOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}