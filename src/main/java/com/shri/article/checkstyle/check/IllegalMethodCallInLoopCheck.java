package com.shri.article.checkstyle.check;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;
import com.shri.article.checkstyle.util.DetailASTUtil;

/**
 * This check identifies scenarios where a method is called in loop condition
 * repeatedly.
 * <p>
 * <b>Overview: </b>
 * <p>
 * Sometimes we call <code>size()</code> method on a <code>List</code> in a
 * for loop condition as follows:
 * 
 * <pre>
 * <code>
 * for(int i=0;i&lt;list.size();i++)
 * </code>
 * </pre>
 * 
 * This method size() is unnecessary and could be avoided as follows:
 * 
 * <pre>
 * <code>
 * int listSize = list.size();
 * for(int i=0;i&lt;listSize;i++)
 * </code>
 * </pre>
 * 
 * This Check shows an error flag in case a method is called inside loop (for, while, do-while)
 * condition.
 * 
 * @author ShriKant (http://www.xebia.com)
 * 
 * @since v1.0, Oct 11, 2008
 * 
 */
public final class IllegalMethodCallInLoopCheck extends Check {

	String methodNamesStr;

	List<String> methodNames;

	/** Creates new instance of the check. */
	public IllegalMethodCallInLoopCheck() {
		super();
	}

	public void executeCheck(DetailAST aDetailAST) {

	}

	/** {@inheritDoc} */
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.FOR_CONDITION, TokenTypes.LITERAL_WHILE,
				TokenTypes.DO_WHILE };
	}

	/** {@inheritDoc} */
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	/** {@inheritDoc} */
	public void visitToken(DetailAST aDetailAST) {
		DetailAST expr = aDetailAST.findFirstToken(TokenTypes.EXPR);

		if (expr == null) {
			return;
		}

		List<DetailAST> methodCallAstList = DetailASTUtil
				.getDetailASTsForTypeInBranch(expr, TokenTypes.METHOD_CALL);

		if (methodCallAstList == null) {
			return;
		}
		int size = methodCallAstList.size();
		for (int i = 0; i < size; i++) {
			DetailAST methodCallAst = methodCallAstList.get(i);
			DetailAST dotAst = methodCallAst.findFirstToken(TokenTypes.DOT);

			final FullIdent ident;
			if (dotAst == null || dotAst.getType() != TokenTypes.DOT) {
				ident = CheckUtils.createFullType((DetailAST) methodCallAst);
			} else {
				ident = FullIdent.createFullIdent(dotAst);
			}

			if (isIllegalMethodName(ident.getText().substring(
					ident.getText().lastIndexOf(".") + 1))) {
				log(aDetailAST,
						"API Method call not allowed in Conditional Expr -> "
								+ ident.getText());
			} else {
				log(aDetailAST,
						"Method call not allowed in Conditional Expr -> "
								+ ident.getText());
			}
		}
	}

	public void setMethodNames(String methodNamesStr) {
		this.methodNamesStr = methodNamesStr;
		if (!StringUtils.isBlank(methodNamesStr))
			methodNames = Arrays.asList(methodNamesStr.split(","));
	}

	private final boolean isIllegalMethodName(final String aIdent) {
		return methodNames.contains(aIdent);
	}
}
