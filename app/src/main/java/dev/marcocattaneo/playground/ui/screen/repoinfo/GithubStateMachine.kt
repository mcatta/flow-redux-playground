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

package dev.marcocattaneo.playground.ui.screen.repoinfo

import com.freeletics.flowredux.dsl.FlowReduxStateMachine
import com.freeletics.flowredux.dsl.State
import dagger.hilt.android.scopes.ViewModelScoped
import dev.marcocattaneo.playground.domain.models.Repository
import dev.marcocattaneo.playground.domain.repository.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ViewModelScoped
class TodoStateMachine @Inject constructor(
    private val githubRepository: GithubRepository
) : FlowReduxStateMachine<GithubState, GithubAction>(initialState = GithubState.ContentState()) {
    init {
        spec {
            // Load
            inState {
                onEnter { state: State<GithubState.Load> ->
                    val owner = state.snapshot.owner
                    githubRepository.repositoryByOwner(owner = owner).fold(
                        ifLeft = {
                            state.override { GithubState.Error(Throwable("Fail")) }
                        },
                        ifRight = {
                            state.override { GithubState.ContentState(repositories = it, owner = owner) }
                        }
                    )
                }
            }

            // Error
            inState {
                on { _: GithubAction.RetryLoadingAction, state: State<GithubState.Error> ->
                    state.noChange()
                }
            }

            // ContentState
            inState {
                on { action: GithubAction.TypeOwner, state: State<GithubState.ContentState> ->
                    state.mutate { copy(owner = action.input) }
                }

                on { _: GithubAction.Confirm, state: State<GithubState.ContentState> ->
                    val owner = state.snapshot.owner
                    if (owner.isNotBlank()) {
                        state.override { GithubState.Load(owner = owner) }
                    } else {
                        state.noChange()
                    }
                }

            }

        }
    }
}

sealed interface GithubState {
    data class Load(val owner: String) : GithubState
    data class Error(val e: Throwable) : GithubState
    data class ContentState(
        val repositories: List<Repository> = emptyList(),
        val owner: String = "",
    ) : GithubState
}

sealed interface GithubAction {
    object Confirm : GithubAction
    object RetryLoadingAction : GithubAction
    data class TypeOwner(val input: String) : GithubAction
}