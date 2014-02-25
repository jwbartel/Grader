package grader.assignment;

import grader.checkers.CheckResult;
import grader.checkers.FeatureChecker;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import util.annotations.*;
import util.misc.Common;
import util.trace.Tracer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

// a feature to be implemented by a student
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
public class AGradingFeature implements GradingFeature {
	public static final String FEEDBACK_FILE_PREFIX = "Score-";
	public static final String RESULTS_FILE_PREFIX = "Results-";
	public static final String NOTES_FILE_PREFIX = "Notes-";

	public static final String FEEDBACK_FILE_SUFFIX = ".txt";

	String featureName = "Some feature";
	// String comment = " ";
	double maxScore;
	double score;
	boolean graded;
	boolean autoGraded;
	boolean extraCredit;
	FileProxy feedbackFolder;
	FeatureChecker featureChecker;
	String feedbackFileName, resultsFileName, notesFileName;
	SakaiProject project;
	SakaiProjectDatabase projectDatabase;
	String[] outputFiles;
	String[] inputFiles;
	GradingFeature linkedFeature;
	boolean cannotAutoGrade;
	String notes = "";
	String result = "";
	boolean isRestriction;

	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public AGradingFeature(String aFeature, double aMaxScore) {
		featureName = aFeature;

		maxScore = aMaxScore;
		processMaxScore();
	}

	public AGradingFeature(String aFeature, double aMaxScore,
			FeatureChecker aFeatureChecker) {
		featureName = aFeature;
		maxScore = aMaxScore;
		processMaxScore();
		// score = aMaxScore;
		featureChecker = aFeatureChecker;
		featureChecker.init(this);
	}

	void processMaxScore() {
		if (maxScore > 0)
			score = maxScore;
		else {
			score = 0;
			isRestriction = true;
		}
	}

	@Visible(false)
	@Override
	public boolean isRestriction() {
		return isRestriction;
	}

	@Visible(false)
	public GradingFeature getLinkedFeature() {
		return linkedFeature;
	}

	public void setLinkedFeature(GradingFeature aGradingFeature) {
		linkedFeature = aGradingFeature;
	}

	public AGradingFeature(String aFeature, double aMaxScore,
			boolean anExtraCredit) {
		this(aFeature, aMaxScore);
		extraCredit = anExtraCredit;
	}

	public String toString() {
		return "Feature:" + featureName + "\tMax:" + maxScore + "\tScore: "
				+ score + (extraCredit ? "\tExtra" : "");
	}

	public AGradingFeature(String aFeature, double aMaxScore,
			FeatureChecker aFeatureChecker, boolean anExtraCredit) {
		this(aFeature, aMaxScore, aFeatureChecker);
		extraCredit = anExtraCredit;
	}

	@Visible(false)
	public FeatureChecker getFeatureChecker() {
		return featureChecker;
	}

	@Visible(false)
	public String[] getOutputFiles() {
		return outputFiles;
	}

	public void setOutputFiles(String[] outputFiles) {
		this.outputFiles = outputFiles;
	}

	@Visible(false)
	public String[] getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(String[] inputFiles) {
		this.inputFiles = inputFiles;
	}

	@Label("Extra")
	@ComponentWidth(60)
	@Position(4)
	public boolean isExtraCredit() {
		return extraCredit;
	}

	/**
	 * @return If this feature is ready to grade
	 */
	public boolean preAutoGrade() {
		return featureChecker != null && !cannotAutoGrade && !graded
				&& project != null && project.hasBeenRun()
				&& project.canBeRun();

	}

	public void setProject(SakaiProject aProject) {
		project = aProject;
		if (featureChecker != null)
			featureChecker.setProject(aProject);
		feedbackFolder = aProject.getStudentAssignment().getFeedbackFolder();
		feedbackFileName = feedbackFolder.getAbsoluteName() + "/"
				+ FEEDBACK_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX;
		notesFileName = feedbackFolder.getAbsoluteName() + "/"
				+ NOTES_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX;
		resultsFileName = feedbackFolder.getAbsoluteName() + "/"
				+ RESULTS_FILE_PREFIX + featureName + FEEDBACK_FILE_SUFFIX;

		graded = false;
		cannotAutoGrade = false;
		notes = retrieveNotes();
		result = retrieveResult();
		firePropertyChange("this", null, this);
	}

	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) {
		projectDatabase = aProjectDatabase;
	}

	@Visible(false)
	public String getFeedbackFileName() {
		return feedbackFileName;
	}

	@Visible(false)
	public String getNotesFileName() {
		return notesFileName;
	}

	@Visible(false)
	public String getResultFileName() {
		return resultsFileName;
	}

	public void autoGrade() {
		if (cannotAutoGrade)
			return;

		CheckResult result = featureChecker.check();
		if (result == null) {
			Tracer.error("Could not autograde:" + this.getFeature());
			cannotAutoGrade = true;
			pureSetScore(0);
			return;
		}
		pureSetScore(result.getScore());
		projectDatabase.getAutoFeedback().recordAutoGrade(this, result);
	}

	@Override
	public void comment() {
		projectDatabase.getManualFeedback().comment(this);
	}

	@Position(0)
	@ComponentWidth(250)
	public String getFeature() {
		return featureName;
	}

	@Position(1)
	@ComponentWidth(30)
	public double getMax() {
		return maxScore;
	}

	@Position(2)
	@ComponentWidth(40)
	public double getScore() {
		return score;
	}

	public void reset() {
		pureSetScore(maxScore);
	}

	public boolean preSetScore() {
		return featureChecker == null || preAutoGrade();
	}

	public void setScore(double score) {
		pureSetScore(score);
		if (score != maxScore)
			comment();

	}

	public void initScore(double score) {
		this.score = score;
	}

	public void pureSetScore(double score) {
		pureSetGraded(true);
		double oldScore = this.score;
		this.score = score;
		propertyChangeSupport.firePropertyChange("Score", oldScore, score);
		firePropertyChange("Graded", null, true);
	}

	@util.annotations.Explanation("test")
	@Position(5)
	@ComponentWidth(70)
	public boolean isGraded() {
		return graded;
	}

	public void pureSetGraded(boolean newValue) {
		if (graded == newValue)
			return;

		graded = newValue;
		if (linkedFeature != null && graded && !cannotAutoGrade) {
			linkedFeature.setGraded(true);
		}
		if (cannotAutoGrade) {
			propertyChangeSupport.firePropertyChange("this", null, this); // our
																			// companion
																			// has
																			// been
																			// graded,
																			// so
																			// we
																			// are
																			// also,
																			// and
																			// let
																			// the
																			// preconditions
																			// update
			return;
		}
	}

	public void setGraded(boolean newValue) {
		if (graded == newValue)
			return;
		graded = newValue;

		if (featureChecker != null) {
			autoGrade();
		} else {
			correct();
		}
	}

	// this is not displayed
	// @Position(3)
	public void correct() {
		pureSetScore(maxScore);
	}

	@Override
	@Position(3)
	@Label("Auto")
	@ComponentWidth(60)
	public boolean isAutoGradable() {
		return featureChecker != null;
	}

	boolean isSelected;

	@util.annotations.Explanation("test")
	@Position(6)
	@ComponentWidth(80)
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean newVal) {
		if (isSelected == newVal)
			return;
		boolean oldVal = isSelected;
		isSelected = newVal;
		if (newVal) {
			setGraded(true);
		}
		propertyChangeSupport
				.firePropertyChange("Selected", oldVal, isSelected);

	}

	@Visible(false)
	public String getResult() {
		return result;
	}

	@Visible(false)
	public void setResult(String result) {
		String oldVal = result;
		this.result = result;
		propertyChangeSupport.firePropertyChange("result", oldVal, result);

	}

	@Visible(false)
	public String getNotes() {
		return notes;
	}

	void recordNotes() {
		try {
			FileUtils.writeStringToFile(new File(getNotesFileName()), notes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	String retrieveResult() {
		try {
			return FileUtils.readFileToString(new File(getResultFileName()));
		} catch (IOException e) {
			return "";
		}
	}
	
	void recordResults() {
		try {
			FileUtils.writeStringToFile(new File(getResultFileName()), result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	String retrieveNotes() {
		try {
			return FileUtils.readFileToString(new File(getNotesFileName()));
		} catch (IOException e) {
			return "";
		}
	}

	@Visible(false)
	public void setNotes(String notes) {
		String oldVal = notes;
		this.notes = notes;
		propertyChangeSupport.firePropertyChange("notes", oldVal, notes);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener aListener) {
		propertyChangeSupport.addPropertyChangeListener(aListener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener aListener) {
		propertyChangeSupport.removePropertyChangeListener(aListener);
	}

	public void firePropertyChange(String aName, Object anOldValue,
			Object aNewValue) {
		propertyChangeSupport.firePropertyChange(aName, anOldValue, aNewValue);
	}

}
