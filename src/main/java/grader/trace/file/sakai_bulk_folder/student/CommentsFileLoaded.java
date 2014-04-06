package grader.trace.file.sakai_bulk_folder.student;

import grader.trace.file.SerializableFileInfo;

public class CommentsFileLoaded extends SerializableFileInfo {

	public CommentsFileLoaded(String aMessage, String aFileName,
			Object aFinder) {
		super(aMessage, aFileName, aFinder);
	}
	
	public static CommentsFileLoaded newCase(String aFileName,
			Object aFinder) {
		String aMessage =  "Comments file loaded: " + aFileName;
		CommentsFileLoaded retVal = new CommentsFileLoaded(aMessage, aFileName, aFinder);
		retVal.announce();
		return retVal;
	}

}
