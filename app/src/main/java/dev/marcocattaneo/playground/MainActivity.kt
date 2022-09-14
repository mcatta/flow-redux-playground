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

package dev.marcocattaneo.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.marcocattaneo.playground.navigation.NavigationComponent
import dev.marcocattaneo.playground.navigation.NavigationControllerImpl
import dev.marcocattaneo.playground.navigation.composable
import dev.marcocattaneo.playground.ui.screen.Routes
import dev.marcocattaneo.playground.ui.screen.repoinfo.TodoScreen
import dev.marcocattaneo.playground.ui.screen.repoinfo.GithubViewModel
import dev.marcocattaneo.playground.ui.theme.AndroidcomposetemplateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostState = rememberNavController()
            val controller = NavigationControllerImpl(navHostState)
            AndroidcomposetemplateTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavigationComponent(
                        startRoute = Routes.RepoInfo,
                        navigationController = controller
                    ) {

                        composable<GithubViewModel>(
                            route = Routes.RepoInfo,
                            navigationController = controller
                        ) { _, vm ->
                            TodoScreen(vm)
                        }
                    }
                }
            }
        }
    }
}