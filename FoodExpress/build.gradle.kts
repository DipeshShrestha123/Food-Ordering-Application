buildscript {
    dependencies {
        classpath(libs.google.services)
    }
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io")}
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}