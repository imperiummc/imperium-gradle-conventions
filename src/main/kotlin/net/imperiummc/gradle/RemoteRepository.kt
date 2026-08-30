package net.imperiummc.gradle

import org.gradle.api.Action
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.authentication.http.BasicAuthentication
import java.net.URI

class RemoteRepository(val name: String, val uri: URI) {

    companion object {
        val IMPERIUM_INTERNAL = RemoteRepository("imperiumInternal", "https://maven.imperium-mc.net/internal")
        val IMPERIUM_EXTERNAL = RemoteRepository("imperiumExternal", "https://maven.imperium-mc.net/external")
    }

    constructor(name: String, url: String) : this(name, URI.create(url))

    fun addTo(repositories: RepositoryHandler) = repositories.maven {
        name = this@RemoteRepository.name
        url = this@RemoteRepository.uri
    }

    fun addTo(repositories: RepositoryHandler, action: Action<MavenArtifactRepository>): MavenArtifactRepository {
        val repository = addTo(repositories)
        action.execute(repository)
        return repository
    }

    fun addAuthenticatedTo(repositories: RepositoryHandler) = addTo(repositories) {
        credentials(PasswordCredentials::class.java)
        authentication.create("basic", BasicAuthentication::class.java)
    }

}
