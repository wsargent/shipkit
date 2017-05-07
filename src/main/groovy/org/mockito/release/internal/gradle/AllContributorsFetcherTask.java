package org.mockito.release.internal.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.mockito.release.gradle.ReleaseConfiguration;
import org.mockito.release.notes.contributors.AllContributorsSerializer;
import org.mockito.release.notes.contributors.Contributors;
import org.mockito.release.notes.contributors.GitHubContributorsProvider;
import org.mockito.release.notes.contributors.ProjectContributorsSet;

import java.io.File;

/**
 * Fetch data about all project contributors and store it in file.
 * It is used later in generation pom.xml.
 * Uses GitHub contributors endpoint, quoting documentation:
 * "Contributors data is cached for performance reasons. This endpoint may return information that is a few hours old."
 */
public class AllContributorsFetcherTask extends DefaultTask {

    private static final Logger LOG = Logging.getLogger(AllContributorsFetcherTask.class);

    @Input private String repository;
    @Input private String readOnlyAuthToken;

    @OutputFile private File outputFile;

    /**
     * See {@link ReleaseConfiguration.GitHub#getRepository()}
     */
    public String getRepository() {
        return repository;
    }

    /**
     * See {@link #getRepository()}
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    /**
     * See {@link ReleaseConfiguration.GitHub#getReadOnlyAuthToken()}
     */
    public String getReadOnlyAuthToken() {
        return readOnlyAuthToken;
    }

    /**
     * See {@link #getReadOnlyAuthToken()}
     */
    public void setReadOnlyAuthToken(String readOnlyAuthToken) {
        this.readOnlyAuthToken = readOnlyAuthToken;
    }

    /**
     * Where serialized information about contributors will be stored.
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * See {@link #getOutputFile()}
     */
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    @TaskAction
    public void fetchAllProjectContributorsFromGitHub() {
        LOG.lifecycle("  Fetching all contributors for project");

        GitHubContributorsProvider contributorsProvider = Contributors.getGitHubContributorsProvider(repository, readOnlyAuthToken);
        ProjectContributorsSet allContributorsForProject = contributorsProvider.getAllContributorsForProject();

        AllContributorsSerializer serializer = Contributors.getAllContributorsSerializer(outputFile);
        serializer.serialize(allContributorsForProject);

        LOG.lifecycle("  Serialized all contributors into: {}", getProject().relativePath(outputFile));
    }
}
