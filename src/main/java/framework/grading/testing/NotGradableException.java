package framework.grading.testing;

import grader.trace.CheckedGraderException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/8/13
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class NotGradableException extends CheckedGraderException {
	public NotGradableException(String aMessage) {
		super(aMessage);
	}
	
	public NotGradableException(String aMessage, Object aFinder) {
		super(aMessage, aFinder);
		// TODO Auto-generated constructor stub
	}

	public NotGradableException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static NotGradableException newCase(String aMessage, Object aFinder) {
		NotGradableException retVal = new NotGradableException(aMessage,  aFinder);
		retVal.announce();		
		return retVal;
	}
//	public static void main (String[] args) {
//		Tracer.showInfo(true);
//		Tracer.setKeywordPrintStatus(Tracer.ALL_KEYWORDS, true);
//		newCase("", null);
//	}
}
