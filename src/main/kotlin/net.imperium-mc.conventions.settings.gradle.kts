import net.imperiummc.gradle.RemoteRepository

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.convention(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        RemoteRepository.IMPERIUM_EXTERNAL.addTo(this)
        RemoteRepository.IMPERIUM_INTERNAL.addAuthenticatedTo(this)
    }
}