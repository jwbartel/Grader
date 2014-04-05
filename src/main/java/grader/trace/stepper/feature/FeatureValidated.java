package grader.trace.stepper.feature;

import java.awt.Color;

import grader.assignment.GradingFeature;
import grader.project.graded.OverviewProjectStepper;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.settings.GraderSettingsModel;
import grader.trace.stepper.SerializableStepperInfo;
import grader.trace.stepper.StepperInfo;
import bus.uigen.trace.ConstantsMenuAdditionEnded;
import util.trace.TraceableInfo;

public class FeatureValidated extends SerializableGradingFeatureInfo {



public FeatureValidated(String aMessage,
			SakaiProjectDatabase aSakaiProjectDatabase,
			OverviewProjectStepper aProjectStepper, SakaiProject aProject, GradingFeature aFeature,			
			Object aFinder) {
		super(aMessage, aSakaiProjectDatabase, aProjectStepper, aProject, aFeature, aFinder);
		// TODO Auto-generated constructor stub
	}



	
	public static FeatureValidated newCase(SakaiProjectDatabase aSakaiProjectDatabase, 
			OverviewProjectStepper aProjectStepper, 
			SakaiProject aProject, GradingFeature aFeature,
			Object aFinder) {
		String aMessage = "Feature: "  + aFeature.getFeatureName() + "Validated.";
		FeatureValidated retVal = new FeatureValidated(aMessage, aSakaiProjectDatabase, aProjectStepper, aProject, aFeature,  aFinder);
		retVal.announce();		
		return retVal;
	}
	

}
