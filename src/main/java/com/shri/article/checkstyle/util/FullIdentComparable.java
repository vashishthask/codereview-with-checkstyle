package com.shri.article.checkstyle.util;

import com.puppycrawl.tools.checkstyle.api.FullIdent;

/**
 * This class is useful when one needs to sort a list of <code>FullIdent</code>
 * objects based on the location (line number, column) of the node.
 * 
 * @author ShriKant (http://www.svashishtha.com)
 * 
 * @since v1.0, Oct 10, 2008
 * 
 */
public class FullIdentComparable implements Comparable<FullIdentComparable> {

	private FullIdent fullIdent;

	/**
	 * Creates a FullIdentComparable with <code>fullIdent</code> param
	 * 
	 * @param fullIdent
	 * 
	 */
	public FullIdentComparable(FullIdent fullIdent) {
		this.fullIdent = fullIdent;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(FullIdentComparable o) {
		if (o == null)
			return -1;
		int thisLineNumber = this.fullIdent.getLineNo();
		int otherLineNumber = o.getFullIdent().getLineNo();
		int thisColumnNumber = this.fullIdent.getColumnNo();
		int otherColumnNumber = o.getFullIdent().getColumnNo();

		if (thisLineNumber < otherLineNumber)
			return -1;
		if (thisLineNumber > otherLineNumber)
			return 1;
		if (thisLineNumber == otherLineNumber) {
			if (thisColumnNumber > otherColumnNumber)
				return 1;
			else if (thisColumnNumber == otherColumnNumber)
				return 0;
			else if (thisColumnNumber < otherColumnNumber)
				return -1;
		}

		return 0;
	}

	/**
	 * Returns the fullIdent
	 * 
	 * @return FullIdent
	 */
	public FullIdent getFullIdent() {
		return fullIdent;
	}
}
