import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqlDelight)
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//            }
//        }
//        binaries.executable()
//    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting
//        val wasmJsMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.exoplayer.dash)
            implementation(libs.androidx.media3.exoplayer.hls)
            implementation(libs.androidx.media3.exoplayer.rtsp)
            implementation(libs.androidx.media3.exoplayer.smoothstreaming)
            implementation(libs.androidx.media3.ui)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.android.driver)
            implementation(libs.androidx.media3.exoplayer.ima)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.vlcj)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.native.driver)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.runtime)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kamel)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.bottom.sheet.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.koin.compose)
        }
//        wasmJsMain.dependencies {
//            implementation(libs.ktor.client.okhttp)
//        }
    }
}

android {
    namespace = "org.ramt57.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.ramt57.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 2
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts += "META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.ramt57.project"
            packageVersion = "1.0.0"
        }
    }
}

//compose.experimental {
//    web.application {}
//}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("sqlfiles")
        }
    }
}