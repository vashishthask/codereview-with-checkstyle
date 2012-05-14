package com.shri.article.checkstyle.check;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * TODO A summary sentence containing a concise but complete description of the API item
 * <p>
 * <b>Overview: </b>
 * <p>
 * TODO Mention the overview of class 
 * using <b>,<p>,<li>,<ui>,<code></code>,<pre></pre>,<i></i>
 * 
 * <p>
 * <DL>
 * <DT><B>History: </B>
 * <DD>Oct 11, 2008</DD>
 * </DL>
 * 
 * @author ShriKant (http://www.xebia.com)
 * 
 * @since v1.0, Oct 11, 2008
 *  
 */
public final class IllegalExceptionCatchCheck extends Check {

	/** Creates new instance of the check. */
	String inclClassNamesStr;
	List<String> inclClassNames;
	String excClassNamesStr;
	List<String> excClassNames;

	public IllegalExceptionCatchCheck() {
		super();
	}

	private boolean isClassToBeChecked(DetailAST aDetailAST) {
		DetailAST tempDetailAST = null;
		while (aDetailAST != null) {
			tempDetailAST = aDetailAST.getParent();
			if (tempDetailAST == null) {
				break;
			}
			tempDetailAST = aDetailAST;
		}
		DetailAST identDetailAST = null;
		if (aDetailAST.getType() == TokenTypes.CLASS_DEF) {
			identDetailAST = aDetailAST.findFirstToken(TokenTypes.IDENT);
			if (isInclExcpClass(identDetailAST.getText())) {
				return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.LITERAL_CATCH };
	}

	/** {@inheritDoc} */
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	/** {@inheritDoc} */
	public void visitToken(DetailAST aDetailAST) {
		if (!isClassToBeChecked(aDetailAST)) {
			return;
		}
		DetailAST pdDetailAST = aDetailAST
				.findFirstToken(TokenTypes.PARAMETER_DEF);
		if (pdDetailAST == null) {
			return;
		}
		DetailAST typeDetailAST = pdDetailAST.findFirstToken(TokenTypes.TYPE);
		if (typeDetailAST == null) {
			return;
		}

		DetailAST identDetailAST = typeDetailAST
				.findFirstToken(TokenTypes.IDENT);
		if (identDetailAST == null) {
			return;
		}

		if (isIllegalExcpCatch(identDetailAST.getText())) {
			log(aDetailAST, "The Exception caught is illegal -> "
					+ identDetailAST.getText());
		}
	}

	protected final boolean isIllegalExcpCatch(final String aIdent) {
		return excClassNames.contains(aIdent);
	}

	protected final boolean isInclExcpClass(final String aIdent) {
		Iterator<String> iter = inclClassNames.iterator();
		while (iter.hasNext()) {
			if (aIdent.endsWith(iter.next())) {
				return true;
			}
		}
		return false;
	}

	public void setInclClassNames(String inclClassNamesStr) {
		this.inclClassNamesStr = inclClassNamesStr;
		inclClassNames = Arrays.asList(inclClassNamesStr.split(","));
	}

	public void setExcClassNames(String excClassNamesStr) {
		this.excClassNamesStr = excClassNamesStr;
		excClassNames = Arrays.asList(excClassNamesStr.split(","));
	}
}
