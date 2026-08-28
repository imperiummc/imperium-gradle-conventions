pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.imperium-mc.net/external") {
            name = "imperiumExternal"
        }
    }
}

plugins {
    id("net.imperium-mc.conventions") version "1.0.0"
}

@Suppress("UnstableApiUsage") dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "imperium-gradle-conventions"
