package com.kodeco.recipefinder.ui.groceries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.recipefinder.ui.widgets.SpacerW4
import com.kodeco.recipefinder.viewmodels.GroceryListViewModel


@Composable
fun RowScope.FilterIcon(groceryListViewModel: GroceryListViewModel) {
    val groceryUIState by groceryListViewModel.groceryUIState.collectAsState()
    val allListShowing = groceryUIState.allListShowing
    val expanded = remember { mutableStateOf(false) }
    Box(modifier = Modifier.align(Alignment.CenterVertically)) {
        IconButton(onClick = {
            expanded.value = !expanded.value
        }) {
            Icon(Icons.Filled.FilterAlt, contentDescription = "Filter")
        }
        SpacerW4()
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text("All") },
                leadingIcon = {
                    if (allListShowing) Icon(
                        imageVector = Icons.Filled.CheckBox,
                        contentDescription = "All"
                    )
                },
                onClick = {
                    groceryListViewModel.setAllShowing(true)
                    expanded.value = false
                }
            )
            DropdownMenuItem(
                text = { Text("Need/Have") },
                leadingIcon = {
                    if (!allListShowing) Icon(
                        imageVector = Icons.Filled.CheckBox,
                        contentDescription = "Need"
                    )
                },
                onClick = {
                    groceryListViewModel.setAllShowing(false)
                    expanded.value = false
                }
            )
        }
    }
}


@Preview
@Composable
fun PreviewFilterIcon() {
    Surface {
        Row {
            FilterIcon(GroceryListViewModel())
        }
    }
}