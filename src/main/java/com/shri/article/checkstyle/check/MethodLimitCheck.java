package com.shri.article.checkstyle.check;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * A simple check to limit the number of methods in a class. It's useful to
 * define certain standards which warns if number of methods go beyond a certain
 * limit. The number of methods in a class can be configured using
 * <code>max</code> property.
 * 
 * @author ShriKant (http://www.svashishtha.com)
 * 
 * @since v1.0, Oct 11, 2008
 * 
 */
public class MethodLimitCheck extends Check {
	private int max = 5;

	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
	}

	public void visitToken(DetailAST ast) {
		// find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
		// count the number of direct children of the OBJBLOCK
		// that are METHOD_DEFS
		int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);
		// report error if limit is reached
		if (methodDefs > max) {
			log(ast.getLineNo(), "too many methods, only " + max
					+ " are allowed");
		}
	}

	// code from above omitted for brevity
	public void setMax(int limit) {
		max = limit;
	}
}
