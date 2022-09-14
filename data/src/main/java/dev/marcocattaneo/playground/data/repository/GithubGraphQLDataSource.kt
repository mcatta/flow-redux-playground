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

package dev.marcocattaneo.playground.data.repository

import arrow.core.Either
import com.apollographql.apollo3.ApolloClient
import dev.marcocattaneo.playground.data.RepositoryByOwnerQuery
import dev.marcocattaneo.playground.data.mapper.RepositoryMapper
import dev.marcocattaneo.playground.data.network.GraphQLResponseHandler
import dev.marcocattaneo.playground.domain.error.AppError
import dev.marcocattaneo.playground.domain.models.Repository
import dev.marcocattaneo.playground.domain.repository.GithubRepository
import javax.inject.Inject

class GithubGraphQLDataSource @Inject constructor(
    private val apolloClient: ApolloClient,
    graphQLResponseHandler: GraphQLResponseHandler,
    // Mappers
    private val repositoryMapper: RepositoryMapper,
) : GithubRepository,
    GraphQLResponseHandler by graphQLResponseHandler {

    override suspend fun repositoryByOwner(owner: String): Either<AppError, List<Repository>> {
        return handleResponse {
            apolloClient
                .query(RepositoryByOwnerQuery(owner = owner, number_of_repos = 100))
                .execute()
        }.map(repositoryMapper::mapTo)
    }

}