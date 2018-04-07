package org.shipkit.gradle.release;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.shipkit.internal.gradle.release.tasks.ReleaseNeeded;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Decides if the release is needed.
 * It is necessary to avoid making releases in certain scenarios like when we are building pull requests.
 * <p>
 * See {@link org.shipkit.internal.gradle.release.ReleaseNeededPlugin} to get more details about how we are checking
 * if a release is needed.
 */
public class ReleaseNeededTask extends DefaultTask {

    private String branch;
    private String releasableBranchRegex;
    private String commitMessage;
    private boolean pullRequest;
    private boolean skipComparePublications;
    private List<File> comparisonResults = new LinkedList<>();

    /**
     * The branch we currently operate on
     */
    public String getBranch() {
        return branch;
    }

    /**
     * See {@link #getBranch()}
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Regex to be used to identify branches that are entitled to be released, for example "master|release/.+"
     */
    public String getReleasableBranchRegex() {
        return releasableBranchRegex;
    }

    /**
     * See {@link #getReleasableBranchRegex()}
     */
    public void setReleasableBranchRegex(String releasableBranchRegex) {
        this.releasableBranchRegex = releasableBranchRegex;
    }

    /**
     * Commit message the build job was triggered with
     */
    public String getCommitMessage() {
        return commitMessage;
    }

    /**
     * See {@link #getCommitMessage()}
     */
    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    /**
     * Pull request this job is building
     */
    public boolean isPullRequest() {
        return pullRequest;
    }

    /**
     * See {@link #isPullRequest()}
     */
    public void setPullRequest(boolean pullRequest) {
        this.pullRequest = pullRequest;
    }

    /**
     * If the comparison of publications should be skipped.
     * <br>
     * This can be useful if you would like to trigger a release (based on the given condition) even if
     * publications are identical.
     * <br>
     * E.g. mockito project uses this to make sure that releases to mavenCentral are actually triggered:
     * <br>
     * <code>assertReleaseNeeded.skipComparePublications = shouldReleaseToCentral(project)</code>
     */
    public boolean isSkipComparePublications() {
        return skipComparePublications;
    }

    /**
     * See {@link #isSkipComparePublications()}
     */
    public void setSkipComparePublications(boolean skipComparePublications) {
        this.skipComparePublications = skipComparePublications;
    }

    /**
     * Publication comparison results, generated by {@link org.shipkit.gradle.java.ComparePublicationsTask}.
     * When the list is empty the task assumes that the comparison was not done and we should make the release.
     * If the list is non-empty, files are checked for size. If any of the file size is not 0 it means there are differences.
     * If there are differences, it means the release is needed.
     * If all files are zero size (e.g. they are empty) it means there are no diffs between publications and the release is not needed.
     */
    public List<File> getComparisonResults() {
        return comparisonResults;
    }

    /**
     * See {@link #getComparisonResults()}
     */
    public void setComparisonResults(List<File> comparisonResults) {
        this.comparisonResults = comparisonResults;
    }

    @TaskAction public boolean releaseNeeded() {
        return new ReleaseNeeded().releaseNeeded(this);
    }
}
