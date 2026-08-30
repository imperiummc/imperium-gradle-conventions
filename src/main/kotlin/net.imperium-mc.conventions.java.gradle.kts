import com.diffplug.gradle.spotless.SpotlessExtension
import com.github.jengelman.gradle.plugins.shadow.ShadowExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.imperiummc.gradle.ImperiumExtension
import net.ltgt.gradle.errorprone.ErrorPronePlugin
import net.ltgt.gradle.errorprone.errorprone
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("net.imperium-mc.conventions.base")
    `java-library`
    id("net.ltgt.errorprone")
    id("com.gradleup.shadow")
}

val imperium = rootProject.extensions.findByType<ImperiumExtension>() ?: extensions.getByType<ImperiumExtension>()
dependencies {
    add(ErrorPronePlugin.CONFIGURATION_NAME, "com.google.errorprone:error_prone_core:2.50.0")
}

configure<ShadowExtension> {
    addShadowVariantIntoJavaComponent.convention(false)
}

configure<JavaPluginExtension> {
    toolchain.languageVersion.set(imperium.javaVersion.map(JavaLanguageVersion::of))
    withJavadocJar()
    withSourcesJar()
}

configure<PublishingExtension> {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

configure<SpotlessExtension> {
    java {
        toggleOffOn()

        replaceRegex(
            "Remove blank lines before closing brackets",
            "(\\r?\\n){2,}(\\s*})",
            "${lineEndings.str()}$2"
        )
        replaceRegex(
            "Remove multiple blank lines",
            "(\\r?\\n){2,}",
            lineEndings.str()
        )
        replaceRegex(
            "Replace toUpper/LowerCase calls without a Locale argument",
            "(to(?:Upper|Lower)Case)\\(\\)",
            "$1\\(Locale.ROOT\\)"
        )

        removeUnusedImports()
        shortenFullyQualifiedTypes()
        forbidWildcardImports()

        cleanthat()
        formatAnnotations()

        leadingTabsToSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlin {
        toggleOffOn()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(imperium.javaVersion)
        options.compilerArgs.add("-parameters")
        options.compilerArgs.add("-Xlint:all")
        options.compilerArgumentProviders.add(CommandLineArgumentProvider {
            @Suppress("UnstableApiUsage")
            imperium.failOnWarnings.filter { it }.map { listOf("-Werror") }.get()
        })
        options.isDeprecation = true

        val errorprone = options.errorprone
        errorprone.allErrorsAsWarnings.set(true)
        errorprone.disableWarningsInGeneratedCode.set(true)
    }

    withType<KotlinJvmCompile>().configureEach {
        compilerOptions.jvmTarget.set(imperium.javaVersion.map(Int::toString).map(JvmTarget::fromTarget))
        compilerOptions.javaParameters.set(true)
        compilerOptions.allWarningsAsErrors.set(imperium.failOnWarnings)
    }

    withType<Test>().configureEach(Test::useJUnitPlatform)

    withType<Javadoc>().configureEach {
        options.encoding = Charsets.UTF_8.name()

        val options = options as StandardJavadocDocletOptions
        options.addBooleanOption("Werror", imperium.failOnWarnings.get())
        options.addBooleanOption("Xdoclint:all", true)
        options.addBooleanOption("Xdoclint:-missing", true)
        options.tags(
            "apiNote:a:API Note:",
            "implSpec:a:Implementation Requirements:",
            "implNote:a:Implementation Note:"
        )
    }

    withType<ShadowJar>().configureEach {
        minimizeJar.convention(true)
    }
}
