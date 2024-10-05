package me.cplanchet.shoppinglistassistant.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardDropdownBox(
    modifier: Modifier = Modifier,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    selected: String,
    label: @Composable (() -> Unit)?
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
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
fun LinkDropDownBox(
    modifier: Modifier = Modifier,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    onLinkSelected: () -> Unit,
    selected: String,
    label: @Composable (() -> Unit)?,
    linkText: String
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
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
            modifier = modifier.menuAnchor()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = !expanded
            },
            modifier
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
                text = { Text(linkText) },
                onClick = {
                    onLinkSelected()
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
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onTextChange(it) },
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        val filteringOptions = options.filter { it.contains(text, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
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