package grader.trace.stepper.feature.score;

import grader.assignment.GradingFeature;
import grader.project.graded.OverviewProjectStepper;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.settings.GraderSettingsModel;
import grader.trace.stepper.SerializableStepperInfo;
import grader.trace.stepper.feature.SerializableGradingFeatureInfo;
import bus.uigen.trace.ConstantsMenuAdditionEnded;
import util.trace.TraceableInfo;

public class SerializableFeatureAutoScoreInfo extends SerializableGradingFeatureInfo {
double featureAutoScore;



public SerializableFeatureAutoScoreInfo(String aMessage,
			SakaiProjectDatabase aSakaiProjectDatabase,
			OverviewProjectStepper aProjectStepper, SakaiProject aProject, GradingFeature aFeature,
			double aScore,
			Object aFinder) {
		super(aMessage, aSakaiProjectDatabase, aProjectStepper, aProject, aFeature, aFinder);
		featureAutoScore = aScore;
		// TODO Auto-generated constructor stub
	}

public double getFeatureAutoScore() {
	return featureAutoScore;
}



public void setFeatureAutoScore(double featureAutoScore) {
	this.featureAutoScore = featureAutoScore;
}

@Override
public String toCSVRow() {
	return super.toCSVRow() 
			+ "," + featureAutoScore;
}

	
//	public static SerializableFeatureAutoScoreInfo newCase(SakaiProjectDatabase aSakaiProjectDatabase, 
//			OverviewProjectStepper aProjectStepper, 
//			SakaiProject aProject, GradingFeature aFeature,
//			String aScore,
//			Object aFinder) {
//		String aMessage = "Overview Score Autoly Changed to:" + aScore;
//		SerializableFeatureAutoScoreInfo retVal = new SerializableFeatureAutoScoreInfo(aMessage, aSakaiProjectDatabase, aProjectStepper, aProject, aFeature, aScore, aFinder);
//		retVal.announce();		
//		return retVal;
//	}
	

}
