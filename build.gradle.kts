import net.imperiummc.gradle.RemoteRepository

plugins {
    id("net.imperium-mc.conventions.base")
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    implementation(kotlin("gradle-plugin-api"))
    implementation(plugin(libs.plugins.foojay))
    implementation(plugin(libs.plugins.shadow))
    implementation(plugin(libs.plugins.errorprone))
    implementation(plugin(libs.plugins.spotless))
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(25)
}

publishing {
    repositories {
        RemoteRepository.IMPERIUM_EXTERNAL.addAuthenticatedTo(this)
    }
}

fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
