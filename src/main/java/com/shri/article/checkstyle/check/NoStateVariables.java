package com.shri.article.checkstyle.check;

import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.shri.article.checkstyle.util.DetailASTUtil;

/**
 * Generally Singletons are considered to hold read-only data. You may run into
 * threading issues when there are some instance variables which could be
 * updated. So you may want to put a rule for certain classes which will raise
 * an error flag if you have any update-able instance variables in them.
 * 
 * 
 * @author ShriKant (http://www.svashishtha.com)
 * 
 * @since v1.0, Oct 10, 2008
 * 
 */
public class NoStateVariables extends Check {

	List<String> classTypes;

	/**
	 * @see com.puppycrawl.tools.checkstyle.api.Check#getDefaultTokens()
	 */
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF };
	}

	public void setClassTypes(String classTypesStr) {
		classTypes = Arrays.asList(classTypesStr.split(","));
	}

	/**
	 * @see com.puppycrawl.tools.checkstyle.api.Check#visitToken(com.puppycrawl.tools.checkstyle.api.DetailAST)
	 */
	@Override
	public void visitToken(DetailAST aast) {
		// TODO Auto-generated method stub
		super.visitToken(aast);
		boolean checkClass = isInsideInclusionList(aast);
		if (!checkClass)
			return;

		DetailAST objBlock = aast.findFirstToken(TokenTypes.OBJBLOCK);
		List<DetailAST> variables = DetailASTUtil
				.getDetailASTsForTypeInChildren(objBlock,
						TokenTypes.VARIABLE_DEF);
		for (DetailAST ast : variables) {
			List<DetailAST> modifiers = DetailASTUtil.getAllChildren(ast);
			boolean firstCondition = false;
			if (modifiers.contains("static"))
				firstCondition = true;
			boolean secondCondition = false;
			if (modifiers.contains("final"))
				secondCondition = true;
			if (!firstCondition || !secondCondition)
				log(ast, "variable should be static final constant");
		}
	}

	private boolean isInsideInclusionList(DetailAST aast) {
		DetailAST firstIdent = aast.findFirstToken(TokenTypes.IDENT);
		String className = firstIdent.getText();
		boolean insideInclusionList = false;
		for (String classType : classTypes) {
			if (className != null && className.endsWith(classType)) {
				insideInclusionList = true;
				break;
			}
		}
		return insideInclusionList;
	}
}
