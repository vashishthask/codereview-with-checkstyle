package com.shri.article.checkstyle.util;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This class provides utility methods on the structure of a Java class.
 * 
 * @author ShriKant (http://www.svashishtha.com)
 * 
 * @since v1.0, Oct 10, 2008
 * 
 */
public class JavaClassUtil {
	/**
	 * Checks if the method node passed is public or not.
	 * 
	 * @param methodDefAST
	 *            a node of method definition type
	 * @return <code>true</code> if method is public, else returns
	 *         <code>false</code>
	 */
	public static boolean isPublicMethod(DetailAST methodDefAST) {
		DetailAST modifiers = methodDefAST.findFirstToken(TokenTypes.MODIFIERS);
		if (modifiers == null)
			return false;
		DetailAST literalPublic = modifiers
				.findFirstToken(TokenTypes.LITERAL_PUBLIC);
		if (literalPublic != null)
			return true;
		return false;
	}

	/**
	 * Checks if the variable is initialized.
	 * 
	 * @param variableDef
	 *            a node containing the variable definition
	 * @return <code>true</code> if variable is initialized, else returns
	 *         <code>false</code>
	 * 
	 */
	public static boolean isVariableInitialized(DetailAST variableDef) {
		DetailAST type = variableDef.findFirstToken(TokenTypes.TYPE);
		String typeName = type.findFirstToken(TokenTypes.IDENT).getText();

		boolean initialized = false;
		DetailAST assign = variableDef.findFirstToken(TokenTypes.ASSIGN);
		if (assign != null) {
			DetailAST expr = assign.findFirstToken(TokenTypes.EXPR);
			if (expr != null) {
				if ("String".equals(typeName)) {
					DetailAST stringLiteral = expr
							.findFirstToken(TokenTypes.STRING_LITERAL);
					if (stringLiteral != null)
						initialized = true;
				}

				DetailAST newOp = expr.findFirstToken(TokenTypes.LITERAL_NEW);
				if (newOp != null)
					initialized = true;
			}
		}
		return initialized;
	}
}
