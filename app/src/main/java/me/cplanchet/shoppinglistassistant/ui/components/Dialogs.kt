package me.cplanchet.shoppinglistassistant.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun DeleteDialog(
    @StringRes textString: Int,
    @StringRes titleString: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        title = { Text(stringResource(titleString))},
        text = { Text(stringResource(textString))},
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ){
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }){
                Text("Cancel")
            }
        }
    )
}