# imperium-gradle-conventions

<a rel="noreferrer" target="_blank" href="https://maven.imperium-mc.net/#/external/net/imperium-mc/conventions/imperium-gradle-conventions">
    <img src="https://maven.imperium-mc.net/api/badge/latest/external/net/imperium-mc/conventions/imperium-gradle-conventions?color=40c14a&name=imperium-gradle-conventions" alt="Latest version badge">
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
    id("net.imperium-mc.conventions") version "<VERSION>"
}
```

`build.gradle.kts`

```kotlin
plugins {
    id("net.imperium-mc.conventions.java") version "<VERSION>"
}
```
