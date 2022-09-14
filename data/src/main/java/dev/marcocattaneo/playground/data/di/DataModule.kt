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

package dev.marcocattaneo.playground.data.di

import com.apollographql.apollo3.ApolloClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.marcocattaneo.playground.data.BuildConfig
import dev.marcocattaneo.playground.data.network.GraphQLResponseHandler
import dev.marcocattaneo.playground.data.network.GraphQLResponseHandlerImpl
import dev.marcocattaneo.playground.data.repository.GithubGraphQLDataSource
import dev.marcocattaneo.playground.domain.repository.GithubRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApollo(): ApolloClient = ApolloClient.Builder()
        .serverUrl(BuildConfig.GITHUB_GRAPHQL_BASEURL)
        .addHttpHeader("Authorization", "Bearer ${BuildConfig.GITHUB_GRAPHQL_TOKEN}")
        .build()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGithubRepository(githubGraphQL: GithubGraphQLDataSource): GithubRepository

    @Binds
    abstract fun bindGraphQLResponseHandler(graphQLResponseHandler: GraphQLResponseHandlerImpl): GraphQLResponseHandler

}