plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose)
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "feature"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                projects.shared.apply {
                    implementation(domain)
                    implementation(ui)
                    implementation(core)
                }
                compose.apply {
                    implementation(runtime)
                    implementation(foundation)
                    implementation(animation)
                    implementation(material3)
                }
                implementation(libs.compose.coil)
                implementation(libs.coroutine)
                libs.koin.apply {
                    implementation(core)
                    implementation(compose)
                }
                implementation(libs.koin.compose)
                libs.mvvm.apply {
                    implementation(compose)
                    implementation(compose.flow)
                }
                implementation(libs.kotlinx.collections.immutable)
            }
        }
        commonTest.dependencies { }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                compose.apply {
                    implementation(preview)
                    implementation(uiTooling)
                }
                implementation(libs.koin.android)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.moviearchive.feature"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
}