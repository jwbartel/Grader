package framework.wrappers;

import grader.assignment.AGradingFeature;
import grader.checkers.CheckResult;
import grader.checkers.FeatureChecker;
import util.trace.Tracer;

/**
 * This is a simple wrapper which prevents the text editor from opening when the checker returns null.
 */
public class GradingFeatureWrapper extends AGradingFeature {
    public GradingFeatureWrapper(String aFeature, double aMaxScore) {
        super(aFeature, aMaxScore);
    }

    public GradingFeatureWrapper(String aFeature, double aMaxScore, boolean anExtraCredit) {
        super(aFeature, aMaxScore, anExtraCredit);
    }

    public GradingFeatureWrapper(String aFeature, double aMaxScore, FeatureChecker aFeatureChecker) {
        super(aFeature, aMaxScore, aFeatureChecker);
    }

    public GradingFeatureWrapper(String aFeature, double aMaxScore, FeatureChecker aFeatureChecker, boolean anExtraCredit) {
        super(aFeature, aMaxScore, aFeatureChecker, anExtraCredit);
    }

    @Override
    public void autoGrade() {
        CheckResult result = getFeatureChecker().check();
        if (result == null) {
            Tracer.error("Could not autograde:" + this.getFeature());
            pureSetScore(0);
            return;
        }
        pureSetScore(result.getScore());
    }
}