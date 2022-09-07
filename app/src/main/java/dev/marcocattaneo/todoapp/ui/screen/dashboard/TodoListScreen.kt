/*
 * Copyright 2021 Marco Cattaneo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.marcocattaneo.todoapp.ui.screen.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TodoScreen(
    todoListViewModel: TodoListViewModel
) {
    val uiState by todoListViewModel.rememberState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is TodoState.ContentState -> {
                Column {
                    val contentState = (uiState as TodoState.ContentState)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(value = contentState.input, onValueChange = {
                            todoListViewModel.dispatch(TodoAction.Type(it))
                        })
                        Button(
                            modifier = Modifier.padding(start = 16.dp),
                            onClick = { todoListViewModel.dispatch(TodoAction.Confirm) },
                            content = { Text(text = "OK") }
                        )

                    }

                    LazyColumn {
                        contentState.todos.forEachIndexed { index, todo ->
                            item {
                                TodoItem(text = todo.text, checked = todo.done) {
                                    todoListViewModel.dispatch(TodoAction.TaskChange(index, it))
                                }
                            }
                        }
                    }
                }
            }

            is TodoState.Error -> Column {
                Text(text = "FAIL")
                Button(onClick = { todoListViewModel.dispatch(TodoAction.RetryLoadingAction) }) {
                    Text(text = "Retry")
                }
            }

            TodoState.Load -> {
                Text(text = "Loading stuff")
            }

            null -> Unit
        }
    }
}

@Composable
private fun TodoItem(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(
            text = text,
            style = if (checked) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle.Default
        )
    }
}