// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").version("8.8.0").apply(false)
    kotlin("jvm") version "2.1.0"
    alias(libs.plugins.kotlin.android) apply false
}