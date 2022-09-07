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

package dev.marcocattaneo.todoapp.data

import dev.marcocattaneo.todoapp.domain.TodoItem
import javax.inject.Inject

class TodoListDataSource @Inject constructor(): TodoListRepository {

    private var data = mutableListOf<TodoItem>()

    override suspend fun get() = data

    override suspend fun add(value: String) = data.add(TodoItem(value))

    override suspend fun update(index: Int, done: Boolean) {
        data[index].let {
            data[index] = it.copy(done = !it.done)
        }
    }

}