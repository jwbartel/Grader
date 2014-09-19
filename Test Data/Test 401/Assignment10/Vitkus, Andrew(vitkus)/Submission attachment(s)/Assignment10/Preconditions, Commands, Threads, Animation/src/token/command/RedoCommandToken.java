package token.command;

import token.WordToken;
import util.annotations.Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@Tags({ "redo" })
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({ "String", "Word" })
@EditablePropertyNames({ "String" })
public class RedoCommandToken extends WordToken implements IRedoCommandToken {

	public RedoCommandToken(String s) {
		super(s);
	}

}