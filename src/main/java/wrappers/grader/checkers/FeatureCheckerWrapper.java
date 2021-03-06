package wrappers.grader.checkers;

import framework.grading.testing.Checkable;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.logging.recorder.ConglomerateRecorder;
import framework.project.Project;
import framework.utils.GradingEnvironment;
import wrappers.framework.project.ProjectWrapper;
import grader.checkers.ACheckResult;
import grader.checkers.CheckResult;
import grader.sakai.project.SakaiProject;

/**
 * This wraps a {@link Checkable} in a feature checker so as to handle the exceptions.
 */
public class FeatureCheckerWrapper extends ErrorHandlingFeatureChecker {

    private Checkable checkable;

    public FeatureCheckerWrapper(Checkable checkable) {
        this.checkable = checkable;
        setOverridable(false);
    }

    @Override
    protected CheckResult doCheck() throws Exception {

        Project project = new ProjectWrapper(this.project, GradingEnvironment.get().getAssignmentName());
        framework.grading.testing.CheckResult checkResult = checkable.check(project, false);
        ConglomerateRecorder.getInstance().save(checkResult);
        
        CheckResult result = new ACheckResult();

        // Check the test status. Only accept successes
        if (checkResult.getStatus() != framework.grading.testing.CheckResult.CheckStatus.Successful)
            throw new NotGradableException();

        result.setScore(checkResult.getScore());
        result.setResults(checkResult.getResults());

        // Add general notes
        if (!checkResult.getNotes().isEmpty()) {
            String autoNotes = checkResult.getNotes();
            result.getLog().add(autoNotes);
            result.setAutoNotes(autoNotes);
        }

        // Add notes from test case results
        for (TestCaseResult testCaseResult : checkResult.getResults()) {
            if (!testCaseResult.getNotes().isEmpty())
                result.getLog().add(testCaseResult.getNotes());
        }
        
        return result;
    }
}
