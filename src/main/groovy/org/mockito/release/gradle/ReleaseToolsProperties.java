package org.mockito.release.gradle;

/**
 * Properties required by release tools plugins.
 */
public enum ReleaseToolsProperties {

    /**
     * Issue tracker label mappings.
     * The mapping of "GitHub label" to human readable and presentable name.
     * The order of labels is important and will influence the order
     * in which groups of issues are generated in release notes.
     * Examples: ['java-9': 'Java 9 support', 'BDD': 'Behavior-Driven Development support']
     */
    releaseNotes_labelMapping,

    /**
     * Developers to include in generated pom file.
     * It should be a collection of elements like "GITHUB_USER:FULL_NAME", example:
     * ['szczepiq:Szczepan Faber', 'mstachniuk:Marcin Stachniuk'].
     * <p>
     * See POM reference for <a href="https://maven.apache.org/pom.html#Developers">Developers</a>.
     */
    pom_developers,

    /**
     * Contributors to include in generated pom file.
     * It should be a collection of elements like "GITHUB_USER:FULL_NAME", example:
     * ['szczepiq:Szczepan Faber', 'mstachniuk:Marcin Stachniuk']
     * <p>
     * See POM reference for <a href="https://maven.apache.org/pom.html#Contributors">Contributors</a>.
     */
    pom_contributors,
}
