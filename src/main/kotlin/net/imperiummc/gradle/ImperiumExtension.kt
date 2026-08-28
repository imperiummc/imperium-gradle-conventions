package net.imperiummc.gradle

import org.gradle.api.provider.Property

interface ImperiumExtension {

    val javaVersion: Property<Int>

    val failOnWarnings: Property<Boolean>

}