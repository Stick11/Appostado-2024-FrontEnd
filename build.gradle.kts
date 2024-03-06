// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google() // Make sure Google's Maven repository is included
        mavenCentral() // or jcenter() if necessary
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.1") // Add the classpath correctly
        // Add any other classpath dependencies here

    }
}

plugins {
    id("com.android.application") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false

}


