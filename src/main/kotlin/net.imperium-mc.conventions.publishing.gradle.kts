import net.imperiummc.gradle.ImperiumExtension
import net.imperiummc.gradle.RemoteRepository

plugins {
    id("net.imperium-mc.conventions.base")
    `maven-publish`
}

val imperium = rootProject.extensions.findByType<ImperiumExtension>() ?: extensions.getByType<ImperiumExtension>()
configure<PublishingExtension> {
    repositories {
        RemoteRepository.IMPERIUM_INTERNAL.addAuthenticatedTo(this)
        RemoteRepository.IMPERIUM_EXTERNAL.addAuthenticatedTo(this)
    }
}
