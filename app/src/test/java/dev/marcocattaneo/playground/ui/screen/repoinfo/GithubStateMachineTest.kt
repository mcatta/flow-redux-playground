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

package dev.marcocattaneo.playground.ui.screen.repoinfo

import app.cash.turbine.test
import arrow.core.Either
import dev.marcocattaneo.playground.CoroutinesTestRule
import dev.marcocattaneo.playground.domain.error.AppError
import dev.marcocattaneo.playground.domain.repository.GithubRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class GithubStateMachineTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @MockK
    lateinit var githubRepository: GithubRepository

    private val stateMachine by lazy { GithubStateMachine(githubRepository) }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test typing`() = coroutineTestRule.scope.runTest {
        // When / Then
        stateMachine.state.test {
            stateMachine.dispatch(GithubAction.TypeOwner("mcatta"))

            assertEquals("", (awaitItem() as GithubState.ContentState).owner)
            assertEquals("mcatta", (awaitItem() as GithubState.ContentState).owner)
        }
    }

    @Test
    fun `Test confirm upon a failure`() = coroutineTestRule.scope.runTest {
        // Given
        coEvery { githubRepository.repositoryByOwner(any()) } returns Either.Left(AppError.Network)

        // When / Then
        stateMachine.state.test {
            stateMachine.dispatch(GithubAction.TypeOwner("mcatta"))
            stateMachine.dispatch(GithubAction.Confirm)

            assertIs<GithubState.ContentState>(awaitItem())
            assertIs<GithubState.ContentState>(awaitItem())
            assertIs<GithubState.Load>(awaitItem())
            assertIs<GithubState.Error>(awaitItem())

            coVerify { githubRepository.repositoryByOwner(eq("mcatta")) }
        }
    }

    @Test
    fun `Test confirm upon a success`() = coroutineTestRule.scope.runTest {
        // Given
        coEvery { githubRepository.repositoryByOwner(any()) } returns Either.Right(emptyList())

        // When / Then
        stateMachine.state.test {
            stateMachine.dispatch(GithubAction.TypeOwner("mcatta"))
            stateMachine.dispatch(GithubAction.Confirm)

            assertIs<GithubState.ContentState>(awaitItem())
            assertIs<GithubState.ContentState>(awaitItem())
            assertIs<GithubState.Load>(awaitItem())
            assertIs<GithubState.ContentState>(awaitItem())

            coVerify { githubRepository.repositoryByOwner(eq("mcatta")) }
        }
    }

}