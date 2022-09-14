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

object Versions {
    const val COMPOSE = "1.2.0"
    const val COMPOSE_NAVIGATION = "2.5.1"
    const val COMPOSE_HILT_NAVIGATION = "1.0.0"
    const val ACTIVITY = "1.5.0"
    const val KOTLIN = "1.7.0"
    const val HILT = "2.42"
    const val FLOW_REDUX = "1.0.0"
    const val COROUTINE_TEST = "1.6.4"
    const val TURBINE = "0.9.0"
    const val APOLLO = "3.6.0"
    const val ARROW = "1.0.1"
    const val MOCKK = "1.12.7"
}

object Sdk {
    const val COMPILE_SDK_VERSION = 33
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 33
}

object ComposeLibs {
    const val UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
    const val ACTIVITY = "androidx.activity:activity-compose:${Versions.ACTIVITY}"
    const val UI_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
    const val NAVIGATION = "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"
    const val HILT_NAVIGATION = "androidx.hilt:hilt-navigation-compose:${Versions.COMPOSE_HILT_NAVIGATION}"
}

object FlowRedux {
    const val CORE = "com.freeletics.flowredux:flowredux-jvm:${Versions.FLOW_REDUX}"
    const val COMPOSE = "com.freeletics.flowredux:compose:${Versions.FLOW_REDUX}"
}

object HiltLibs {
    const val ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val CORE = "com.google.dagger:hilt-core:${Versions.HILT}"
    const val COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}

object Network {
    const val APOLLO = "com.apollographql.apollo3:apollo-runtime:${Versions.APOLLO}"
}

object Common {
    const val ARROW_CORE = "io.arrow-kt:arrow-core:${Versions.ARROW}"
}

object AndroidXLibs {
    const val APP_COMPACT= "androidx.appcompat:appcompat:${Versions.ACTIVITY}"
    const val CORE = "androidx.core:core-ktx:1.7.0"
}

object AndroidXTestLibs {
    const val JUNIT = "androidx.test.ext:junit:1.1.3"
}

object TestLibs {
    const val JUNIT = "junit:junit:4.13.2"
    const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINE_TEST}"
    const val TURBINE = "app.cash.turbine:turbine:${Versions.TURBINE}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
}

object AndroidLibs {
    const val MATERIAL = "com.google.android.material:material:1.6.1"
}