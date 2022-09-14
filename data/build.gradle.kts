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

import java.util.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.apollographql.apollo3").version("3.6.0")
    kotlin("kapt")
}

val properties = Properties()
if (rootProject.file("local.properties").exists()) {
    properties.load(rootProject.file("local.properties").inputStream())
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "GITHUB_GRAPHQL_BASEURL", "\"https://api.github.com/graphql\"")
        buildConfigField("String", "GITHUB_GRAPHQL_TOKEN", "\"${properties.getProperty("github.token", "")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

apollo {
    packageName.set("dev.marcocattaneo.playground.data")
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    implementation(Network.APOLLO)

    implementation(HiltLibs.CORE)
    kapt(HiltLibs.ANDROID_COMPILER)

    testImplementation(kotlin("test"))
}