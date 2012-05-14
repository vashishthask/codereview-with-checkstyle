package com.shri.article.checkstyle.check;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.shri.article.checkstyle.util.DetailASTUtil;
import com.shri.article.checkstyle.util.JavaClassUtil;

/**
 * This is a project specific check. For logging purposes some projects have
 * methods like <code>traceEnter</code> when you enter into a method and
 * <code>traceExit</code> when you exit from it.
 * <p>
 * <b>Overview: </b>
 * <p>
 * Ensuring that developers have used both in their methods may be a pain. This
 * check solves that problem and raise an error when you have
 * <code>traceEnter</code> but no corresponding <code>traceExit</code> and
 * vice-versa. If you have none of them, still it raise an error.
 * <p>
 * Names of the enter and exit methods are configurable.
 * 
 * @author ShriKant (http://www.svashishtha.com)
 * 
 * @since v1.0, Oct 10, 2008
 * 
 */
public class TraceMessagesCheck extends Check {

	private String traceEnterMethod;
	private String traceExitMethod;

	/**
	 * @see com.puppycrawl.tools.checkstyle.api.Check#getDefaultTokens()
	 */
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.METHOD_DEF };
	}

	/**
	 * @see com.puppycrawl.tools.checkstyle.api.Check#visitToken(com.puppycrawl.tools.checkstyle.api.DetailAST)
	 */
	@Override
	public void visitToken(DetailAST aast) {
		// TODO Auto-generated method stub
		super.visitToken(aast);
		boolean publicMethod = JavaClassUtil.isPublicMethod(aast);
		if (!publicMethod)
			return;

		List<DetailAST> dotASTs = DetailASTUtil.getDetailASTsForTypeInBranch(
				aast.findFirstToken(TokenTypes.SLIST), TokenTypes.DOT);
		boolean traceEnterInd = false;
		boolean traceExitInd = false;
		for (DetailAST dot : dotASTs) {
			List<DetailAST> idents = DetailASTUtil
					.getDetailASTsForTypeInChildren(dot, TokenTypes.IDENT);
			for (DetailAST ident : idents) {
				if ("traceEnter".equals(ident.getText()))
					traceEnterInd = true;
				if ("traceExit".equals(ident.getText()))
					traceExitInd = true;
			}
		}

		if (!traceEnterInd || !traceExitInd)
			log(aast, "Method does not contain either " + traceEnterMethod
					+ " or " + traceExitMethod + " or both");

	}

	public void setTraceEnterMethod(String traceEnterMethod) {
		this.traceEnterMethod = traceEnterMethod;
	}

	public void setTraceExitMethod(String traceExitMethod) {
		this.traceExitMethod = traceExitMethod;
	}
}
