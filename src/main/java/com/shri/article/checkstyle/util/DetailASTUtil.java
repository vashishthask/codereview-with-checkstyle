package com.shri.article.checkstyle.util;

import java.util.ArrayList;
import java.util.List;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * This class provides utility methods on top of <code>DetailAST</code>. They
 * are more based on traversal of nodes which do not exist in
 * <code>DetailAST</code> class right now but nevertheless required and very
 * useful for any Check creation
 * 
 * @author ShriKant (http://www.svashishtha.com)
 * 
 * @since v1.0, Oct 10, 2008
 * 
 */
public class DetailASTUtil {

	/**
	 * In ANTLR tree structure, it searches and provides a list of nodes for a
	 * token type under a branch. It searches all the nodes to any nesting level
	 * for a token type.
	 * 
	 * @param expr
	 *            a node detailing a branch
	 * @param tokenType
	 *            token type to seach for
	 * @return a <code>List<DetailAST></code> of nodes for given
	 *         <code>tokenType</code> in an element <code>expr</code>
	 * 
	 */
	public static List<DetailAST> getDetailASTsForTypeInBranch(DetailAST expr,
			int tokenType) {
		return getDetailASTsForTypeInBranch(expr, tokenType, null);
	}

	private static List<DetailAST> getDetailASTsForTypeInBranch(DetailAST expr,
			int tokenType, List<DetailAST> list) {
		if (list == null)
			list = new ArrayList<DetailAST>();
		DetailAST child = (DetailAST) expr.getFirstChild();
		while (child != null) {
			if (child.getType() == tokenType) {
				list.add(child);
			} else {
				list = getDetailASTsForTypeInBranch(child, tokenType, list);
			}
			child = (DetailAST) child.getNextSibling();
		}
		return list;
	}

	/**
	 * In ANTLR tree structure, it searches and provides a list of nodes for a
	 * token type under a branch. It searches only immediate children for a
	 * token type and doesn't search in deep nested levels.
	 * 
	 * @param expr
	 *            a node detailing a branch
	 * @param tokenType
	 *            token type to seach for
	 * @return a <code>List<DetailAST></code> of nodes for given
	 *         <code>tokenType</code> in an element <code>expr</code>
	 */
	public static List<DetailAST> getDetailASTsForTypeInChildren(
			DetailAST expr, int tokenType) {
		List<DetailAST> result = null;
		DetailAST retVal = null;
		for (AST i = expr.getFirstChild(); i != null; i = i.getNextSibling()) {
			if (i.getType() == tokenType) {
				retVal = (DetailAST) i;
				if (result == null)
					result = new ArrayList<DetailAST>();
				result.add(retVal);
			}
		}
		return result;
	}

	/**
	 * Provides a list of all children under a node.
	 * 
	 * @param expr
	 *            a node to search for
	 * @return a <code>List<DetailAST></code> of nodes
	 * 
	 */
	public static List<DetailAST> getAllChildren(DetailAST expr) {
		List<DetailAST> result = null;
		DetailAST retVal = null;
		for (AST i = expr.getFirstChild(); i != null; i = i.getNextSibling()) {
			if (result == null)
				result = new ArrayList<DetailAST>();
			retVal = (DetailAST) i;
			result.add(retVal);
		}
		return result;
	}

	/**
	 * Provides a list of siblings below a node.
	 * 
	 * @param expr
	 *            a node to search for
	 * @return a <code>List<DetailAST></code> of nodes
	 * 
	 */
	public static List<DetailAST> getAllSiblingsBelow(DetailAST expr) {
		List<DetailAST> result = null;
		DetailAST retVal = null;
		for (AST i = expr.getNextSibling(); i != null; i = i.getNextSibling()) {
			if (result == null)
				result = new ArrayList<DetailAST>();
			retVal = (DetailAST) i;
			result.add(retVal);
		}
		return result;
	}
}
