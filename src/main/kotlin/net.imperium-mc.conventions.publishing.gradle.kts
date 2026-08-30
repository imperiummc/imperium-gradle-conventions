import net.imperiummc.gradle.RemoteRepository

plugins {
    id("net.imperium-mc.conventions.base")
    `maven-publish`
}

configure<PublishingExtension> {
    repositories {
        RemoteRepository.IMPERIUM_INTERNAL.addAuthenticatedTo(this)
    }
}
