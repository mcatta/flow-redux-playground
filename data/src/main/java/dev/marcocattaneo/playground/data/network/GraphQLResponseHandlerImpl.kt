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

package dev.marcocattaneo.playground.data.network

import arrow.core.Either
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import dev.marcocattaneo.playground.domain.error.AppError
import javax.inject.Inject

class GraphQLResponseHandlerImpl @Inject constructor(): GraphQLResponseHandler {

    override suspend fun <D : Operation.Data> handleResponse(block: suspend () -> ApolloResponse<D>): Either<AppError, D> {
        return with(block()) {
            if (data != null && !hasErrors()) {
                Either.Right(data!!)
            } else {
                // Todo improve error mapping
                Either.Left(AppError.Unknown(errors?.map { Error(it.message) } ?: emptyList()))
            }
        }
    }

}