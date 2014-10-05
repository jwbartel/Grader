package gradingTools.comp110f14lab.lab4.testcases;

import framework.grading.testing.TestCaseResult;
import gradingTools.sharedTestCase.CodeInspectorTestCase;

import com.github.antlrjavaparser.api.stmt.WhileStmt;

public class HasForLoop extends CodeInspectorTestCase {
	
	boolean hasForLoop = false;

	public HasForLoop() {
		super("Has for loop");
	}
	
	@Override
	protected void inspectWhileStatement(WhileStmt statement) {
		hasForLoop = true;
		super.inspectWhileStatement(statement);
	}

	@Override
	public TestCaseResult codeInspectionResult() {
		if (hasForLoop) {
			return pass();
		} else {
			return fail("No while loop found");
		}
	}
}
