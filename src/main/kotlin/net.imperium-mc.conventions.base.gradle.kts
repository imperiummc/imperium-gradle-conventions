import com.diffplug.gradle.spotless.SpotlessExtension
import net.imperiummc.gradle.ImperiumExtension
import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
    idea
    id("com.diffplug.spotless")
}

group = "net.imperium-mc"

if (project == rootProject || rootProject.extensions.findByType<ImperiumExtension>() == null) {
    val imperium = extensions.create<ImperiumExtension>("imperium")
    imperium.javaVersion.convention(25)
    imperium.failOnWarnings.convention(true)
}

configurations.configureEach {
    resolutionStrategy.cacheChangingModulesFor(1, TimeUnit.DAYS)
}

the<IdeaModel>().module {
    isDownloadSources = true
    isDownloadJavadoc = true
}

the<SpotlessExtension>().apply {
    encoding(Charsets.UTF_8.name())
    ratchetFrom("origin/trunk")
    kotlinGradle {
        toggleOffOn()
        leadingTabsToSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks {
    withType<ProcessResources>().configureEach {
        filteringCharset = Charsets.UTF_8.name()
    }
    withType<AbstractArchiveTask>().configureEach {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }
}