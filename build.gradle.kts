// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "9.5.1" apply false
    // AGP 9 has built-in Kotlin support; this entry only pins the Kotlin version used by the build.
    id("org.jetbrains.kotlin.android") version "2.4.21" apply false
    // Since Kotlin 2.0 the Compose compiler ships as a Kotlin plugin (same version as Kotlin).
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21" apply false
}
