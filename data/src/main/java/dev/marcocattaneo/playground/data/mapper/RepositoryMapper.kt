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

package dev.marcocattaneo.playground.data.mapper

import dev.marcocattaneo.playground.data.RepositoryByOwnerQuery
import dev.marcocattaneo.playground.domain.common.Mapper
import dev.marcocattaneo.playground.domain.models.Repository
import javax.inject.Inject

class RepositoryMapper @Inject constructor() : Mapper<RepositoryByOwnerQuery.Data, List<Repository>> {
    override fun mapTo(from: RepositoryByOwnerQuery.Data): List<Repository> =
        (from.user?.repositories?.nodes ?: emptyList())
            .filterNotNull()
            .map {
                Repository(name = it.name, id = it.id, url = it.url as String)
            }

}