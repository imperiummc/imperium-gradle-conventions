# imperium-gradle-conventions

<a rel="noreferrer" target="_blank" href="https://maven.imperium-mc.net/#/external/net/imperium-mc/imperium-gradle-conventions">
    <img src="https://maven.imperium-mc.net/api/badge/latest/external/net/imperium-mc/imperium-gradle-conventions?color=40c14a&name=imperium-gradle-conventions" alt="Latest version badge">
</a>

`settings.gradle.kts`

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.imperium-mc.net/external")
    }
}

plugins {
    id("net.imperium-mc.conventions") version "1.0.0"
}
```

`build.gradle.kts`

```kotlin
plugins {
    id("net.imperium-mc.conventions.java")
    id("net.imperium-mc.conventions.publishing")
}
```
