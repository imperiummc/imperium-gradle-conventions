plugins {
    id("net.imperium-mc.conventions.publishing")
    `kotlin-dsl`
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

fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
