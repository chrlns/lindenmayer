/*
 *  Lindenmayer
 *  see AUTHORS for a list of contributors.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package lindenmayer.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Parse tree node of a Lindenmayer system.
 * @author Christian Lins
 */
public class Node {

	/**
	 * Create parse tree for the given grammar down to maxLevel.
	 * @param grammar
	 * @param maxLevel
	 */
	public static Node createParseTree(Grammar grammar, int maxLevel) {
		Symbol symb = grammar.getStartSymbol();
		Rule rule = grammar.getRuleFor(symb, false);

		return new Node(grammar, symb, rule, maxLevel);
	}

	private List<Node> children = new ArrayList<Node>();
	private int maxLevel;
	private Symbol symbol = null;
	private Rule rule = null;

	/**
	 * Performes a substution of the symbol using the given rule.
	 * The resulting symbols are added as child nodes of this node.
	 * @param symb
	 * @param rule
	 * @param maxLevel
	 */
	public Node(Grammar grammar, Symbol symb, Rule rule, int maxLevel) {
		this.symbol = symb;
		this.rule = rule;
		this.maxLevel = maxLevel;
		makeChildren(grammar, maxLevel);
	}
	
	/**
	 * Empty constructor used by clone().
	 */
	private Node() {
	}
	
	/**
	 * Use this Node as tree root and clone it including the whole subtree.
	 * Adds an addition level to maxLevel and produces new children if 
	 * addLevel > 0 using the given grammar.
	 */
	public Node clone(int addLevel, Grammar grammar) {
		Node newRoot = new Node();
		
		// Copy fields
		newRoot.maxLevel = this.maxLevel + addLevel;
		newRoot.rule     = this.rule;
		newRoot.symbol   = this.symbol;
		
		// Clone children
		newRoot.children = new ArrayList<Node>();
		for(Node child : children) {
			Node newChild = child.clone(addLevel, grammar);
			newRoot.children.add(newChild);
			if(newChild.maxLevel == addLevel) {
				newChild.makeChildren(grammar, addLevel);
			}
		}
		
		return newRoot;
	}

	public List<Node> getChildren() {
		return this.children;
	}

	/**
	 * @return Depth of this (sub)tree
	 */
	public int getDepth() {
		int depth = 0;
		
		for(Node child : children) {
			depth = Math.max(depth, child.getDepth());
		}
		
		return depth + 1;
	}

	public Rule getRule() {
		return this.rule;
	}

	public Symbol getSymbol() {
		return this.symbol;
	}
	
	private void makeChildren(Grammar grammar, int maxLevel) {
		if (maxLevel > 0 && rule != null) {
			maxLevel--;
			SymbolSet production = rule.getProduction();
	
			for (Symbol prod : production) {
				Rule prodRule = grammar.getRuleFor(prod, false);
				Node child = new Node(grammar, prod, prodRule, maxLevel);
				this.children.add(child);
			}
		}
	}
}
