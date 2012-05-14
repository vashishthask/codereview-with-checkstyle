package com.shri.article.checkstyle.check;

import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This check is useful to limit people to put certain exceptions in
 * <code>throws</code> clause.
 * <p>
 * <b>Example: </b>
 * <p>
 * At some point of time, you may want to limit people using
 * <code>java.lang.Exception</code> in <code>throws</code> clause. In that
 * case this check will be useful. As name of the exceptions can be configured
 * in the configuration file, it's generic in use.
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
public final class IllegalExceptionThrowsCheck extends Check {

	List<String> illegalExceptionThrows;

	/** {@inheritDoc} */
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.LITERAL_THROWS };
	}

	/** {@inheritDoc} */
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	/** {@inheritDoc} */
	public void visitToken(DetailAST aDetailAST) {
		DetailAST identDetailAST = aDetailAST.findFirstToken(TokenTypes.IDENT);
		if (identDetailAST == null) {
			return;
		}
		if (illegalExceptionThrows.contains(identDetailAST.getText())) {
			log(aDetailAST, "Illegal Throws Clause  -> "
					+ identDetailAST.getText());
		}
	}

	public void setIllegalExceptionThrows(String illegalExceptionThrowsStr) {
		this.illegalExceptionThrows = Arrays.asList(illegalExceptionThrowsStr
				.split(","));
	}
}
