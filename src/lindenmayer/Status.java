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
package lindenmayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.SwingUtilities;
import lindenmayer.grammar.Node;
import lindenmayer.grammar.Grammar;

/**
 * Contains references to currently loaded grammar and status of running
 * program.
 * @author Christian Lins
 */
public class Status {

	// Model specific fields
	private Grammar grammar = new Grammar(this);
	private Stack<Node> trees = new Stack<Node>();

	// StatusChange fields
	private List<StatusChangeListener> listeners = new ArrayList<StatusChangeListener>();

	protected Status() {
	}

	public void addStatusChangeListener(StatusChangeListener listener) {
		this.listeners.add(listener);
	}

	public void removeStatusChangeListener(StatusChangeListener listener) {
		this.listeners.remove(listener);
	}

	protected void fireStatusChangeEvent() {
		for(StatusChangeListener listener : listeners) {
			listener.statusChanged(this);
		}
	}

	protected void fireStatusResetEvent() {
		for(StatusChangeListener listener : listeners) {
			listener.statusReset(this);
		}
	}

	public void fireStatusChangeEventInFuture() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fireStatusChangeEvent();
			}
		});
	}

	public Grammar getGrammar() {
		return this.grammar;
	}

	/**
	 * Sets the grammar of this status and fires a reset event.
	 * @param grammar
	 */
	public void setGrammar(Grammar grammar) {
		this.grammar = grammar;
		resetTreeStack(); // Invalidate already calculated trees
	}

	public Node currentTree() {
		if(trees.empty()) {
			return null;
		} else {
			return trees.peek();
		}
	}

	/**
	 * Pops the tree stack and makes the last tree the current top.
	 * @return
	 */
	public Node lastTree() {
		if(trees.empty()) {
			return null;
		}

		Node top = null;
		trees.pop();
		if(!trees.empty()) {
			top = trees.peek();
		}

		fireStatusChangeEventInFuture();
		
		return top;
	}

	/**
	 * Creates a new tree with a by one increased recursion depth and pushes
	 * the newly created tree on top of the tree stack.
	 * @return
	 */
	public Node nextTree() {
		Node currentTree = currentTree();
		Node tree;
		if(currentTree == null) { 
			tree = Node.createParseTree(grammar, trees.size() + 1);
		} else {
			tree = currentTree.clone(1, grammar);
		}
		
		trees.push(tree);
		fireStatusChangeEventInFuture();

		return tree;
	}

	public void reset() {
		grammar = new Grammar(this);
		trees = new Stack<Node>();

		fireStatusResetEvent();
	}

	public void resetTreeStack() {
		trees = new Stack<Node>();

		fireStatusChangeEvent();
	}
}
