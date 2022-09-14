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

@file:OptIn(ExperimentalFoundationApi::class)

package dev.marcocattaneo.playground.ui.screen.repoinfo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import dev.marcocattaneo.playground.domain.TodoItem

@Composable
fun TodoScreen(
    todoListViewModel: GithubViewModel
) {
    val uiState by todoListViewModel.rememberState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is GithubState.ContentState -> {
                Column {
                    val contentState = (uiState as GithubState.ContentState)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(placeholder = {
                            Text(text = "Owner")
                        }, value = contentState.owner, onValueChange = {
                            todoListViewModel.dispatch(GithubAction.TypeOwner(it))
                        })

                        Button(
                            modifier = Modifier.padding(start = 16.dp),
                            onClick = { todoListViewModel.dispatch(GithubAction.Confirm) },
                            content = { Text(text = "OK") }
                        )

                    }

                    LazyColumn {
                        contentState.repositories.forEach {
                            item { Text(text = it.name) }
                        }
                    }

                }
            }

            is GithubState.Error -> Column {
                Text(text = "FAIL")
                Button(onClick = { todoListViewModel.dispatch(GithubAction.RetryLoadingAction) }) {
                    Text(text = "Retry")
                }
            }

            is GithubState.Load -> {
                Text(text = "Loading stuff")
            }

            null -> Unit
        }
    }
}

private fun LazyListScope.items(items: List<TodoItem>, onClickDone: (Int, Boolean) -> Unit) {
    items.forEachIndexed { index, todo ->
        item {
            TodoItem(text = todo.text, checked = todo.done) {
                onClickDone(index, it)
            }
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