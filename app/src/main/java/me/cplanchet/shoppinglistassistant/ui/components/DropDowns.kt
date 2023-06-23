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
                    text = {Text(text = selectedOption)},
                    onClick = {
                        onSelectionChanged(selectedOption)
                        expanded = false
                    }
                )
            }
        }
    }
}