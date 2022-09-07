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

package dev.marcocattaneo.todoapp.ui.screen.dashboard

import app.cash.turbine.test
import dev.marcocattaneo.todoapp.data.TodoListRepository
import dev.marcocattaneo.todoapp.domain.TodoItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class TodoStateMachineTest {

    @Test
    fun `Test load`() = runTest {
        // Given
        val todoStateMachine = TodoStateMachine(object : TodoListRepository {
            override suspend fun get(): List<TodoItem> = emptyList()

            override suspend fun add(value: String): Boolean = true

            override suspend fun update(index: Int, done: Boolean) {}

        })

        // When / Then
        todoStateMachine.state.test {
            todoStateMachine.dispatch(TodoAction.Type("Hello, World"))

            assertIs<TodoState.Load>(awaitItem())
            assertIs<TodoState.ContentState>(awaitItem())
        }
    }

}