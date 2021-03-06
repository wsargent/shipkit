package org.shipkit.internal.gradle.release

import org.gradle.testfixtures.ProjectBuilder
import org.shipkit.internal.gradle.git.GitBranchPlugin
import org.shipkit.internal.gradle.git.GitSetupPlugin
import spock.lang.Specification

class TravisPluginTest extends Specification {

    def project = new ProjectBuilder().build()

    def "applies"() {
        expect:
        project.plugins.apply(TravisPlugin)
        project.plugins.apply(GitBranchPlugin)
        project.plugins.apply(GitSetupPlugin)
        project.plugins.apply(ReleaseNeededPlugin)
    }
}
