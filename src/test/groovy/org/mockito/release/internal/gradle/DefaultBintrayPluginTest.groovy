package org.mockito.release.internal.gradle

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class DefaultBintrayPluginTest extends Specification {

    def project = new ProjectBuilder().build()

    def "applies"() {
        project.ext.bintray_repo = "my-repo"
        project.ext.gh_repository = "mockito/mockito"

        expect:
        project.plugins.apply("org.mockito.mockito-release-tools.bintray")
    }
}