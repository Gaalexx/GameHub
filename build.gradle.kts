// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android / Kotlin
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false

    // Кодогенерация
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}
