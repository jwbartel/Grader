package grader.steppers;

import util.annotations.ComponentWidth;
import util.annotations.Row;
import util.annotations.Visible;
import util.misc.ClearanceManager;
import util.models.LabelBeanModel;
import util.models.PropertyListenerRegisterer;
import grader.assignment.GradingFeatureList;
import grader.project.Project;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.settings.navigation.NavigationSetter;
import grader.trace.settings.MissingOnyenException;

import java.beans.PropertyChangeListener;


public interface GradedProjectNavigator   extends /*ClearanceManager,*/ PropertyListenerRegisterer/*, PropertyChangeListener*/{
	public boolean setProject(SakaiProject newVal) ;
	
//	public void output();
	
//	public void sources() ;
//	public double getScore() ;
//	public void setScore(double newVal) ;
//	public  void waitForClearance() ;
	public SakaiProjectDatabase getProjectDatabase() ;

	public void setProjectDatabase(SakaiProjectDatabase aProjectDatabase) ;
//	public void setOnyen(String anOnyen) throws MissingOnyenException ;
//	public boolean setProject(String anOnyen) ;
//	public boolean isAutoRun() ;
//    public void setAutoRun(boolean newVal);sss
//    public void autoRun() ;
//    public boolean hasMoreSteps();
	
//	public void setHasMoreSteps(boolean newVal);
    public SakaiProject getProject();
//    boolean runProjectsInteractively();
    public void configureNavigationList();

	boolean preDone();

	void done();

//	String getNavigationFilter();
//
//	void setNavigationFilter(String newVal);

//	boolean preGetGradingFeatures();

//	boolean preAutoGrade();

//	void autoGrade();

//	GradingFeatureList getGradingFeatures();

//	boolean isAllGraded();

	boolean preNext();

	void next();

	boolean prePrevious();

	void previous();

//	boolean preRunProjectsInteractively();

	boolean move(boolean forward);
//	public boolean isAutoAutoGrade() ;
//    public void setAutoAutoGrade(boolean newVal) ;
//    public void autoAutoGrade() ;

	boolean isProceedWhenDone();

	void toggleProceedWhenDone();
	

	void internalSetOnyen(String anOnyen) throws MissingOnyenException;

	boolean shouldVisit();

	void resetNoFilteredRecords();

	int getCurrentOnyenIndex();

	void setCurrentOnyenIndex(int currentOnyenIndex);

	void setHasMoreSteps(boolean newVal);

	boolean hasMoreSteps();

	int getFilteredOnyenIndex();

	void setFilteredOnyenIndex(int filteredOnyenIndex);

	String getSequenceNumber();

	boolean isPlayMode();

	void setPlayMode(boolean playMode);

	void togglePlayPause();
	public void setFrame(Object aFrame);

	boolean preTogglePlayPause();

	void save();

	void quit();

	void setProceedWhenDone(boolean proceedWhenDone);
	public boolean isExitOnQuit();
	public void setExitOnQuit(boolean newVal);

	void sync();

	void openSource();


//	void setFrame(Object aFrame);
//
//	Object getFrame();
//
//	LabelBeanModel getPhoto();
//
//	String getFeedback();

//	String getTranscript();

//	NavigationSetter getNavigationSetter();

//	void validate();

//	boolean runProjectsInteractively(String aGoToOnyen) throws MissingOnyenException;
	

}
