/*
 * Copyright 2022 Marco Cattaneo
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

@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package dev.marcocattaneo.todoapp.ui.screen.dashboard

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.freeletics.flowredux.dsl.State
import dagger.hilt.android.scopes.ViewModelScoped
import dev.marcocattaneo.todoapp.data.TodoListRepository
import dev.marcocattaneo.todoapp.domain.TodoItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ViewModelScoped
class TodoStateMachine @Inject constructor(
    private val todoListRepository: TodoListRepository,
) : FlowReduxStateMachine<TodoState, TodoAction>(initialState = TodoState.Load) {
    init {
        spec {
            // Load
            inState {
                onEnter { state: State<TodoState.Load> ->
                    // executes this block whenever we enter Loading state
                    try {
                        val items = loadItems()
                        state.override { TodoState.ContentState(items) } // Transition to ContentState
                    } catch (t: Throwable) {
                        state.override { TodoState.Error(t) } // Transition to Error state
                    }
                }

            }

            // Error
            inState {
                on { _: TodoAction.RetryLoadingAction, state: State<TodoState.Error> ->
                    state.override { TodoState.Load }
                }
            }

            // ContentState
            inState {
                on { action: TodoAction.Type, state: State<TodoState.ContentState> ->
                    state.mutate { copy(input = action.input) }
                }

                on { _: TodoAction.Confirm, state: State<TodoState.ContentState> ->
                    val input = state.snapshot.input
                    if (input.isNotBlank()) {
                        todoListRepository.add(input)
                        state.override { TodoState.Load }
                    } else {
                        state.noChange()
                    }
                }

                on { action: TodoAction.TaskChange, state: State<TodoState.ContentState> ->
                    todoListRepository.update(action.index, action.done)
                    state.override { TodoState.Load }
                }
            }

        }
    }


    private suspend fun loadItems() = todoListRepository.get()
}

sealed interface TodoState {
    object Load: TodoState
    data class Error(val e: Throwable) : TodoState
    data class ContentState(
        val todos: List<TodoItem>,
        val input: String = ""
    ) : TodoState
}

sealed interface TodoAction {
    object Confirm: TodoAction
    object RetryLoadingAction : TodoAction
    data class Type(val input: String) : TodoAction
    data class TaskChange(val index: Int, val done: Boolean) : TodoAction
}