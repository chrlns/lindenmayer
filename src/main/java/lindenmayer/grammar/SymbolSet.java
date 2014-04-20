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
import java.util.Iterator;
import java.util.List;

/**
 * A set of tokens.
 * @author Christian Lins 
 * @author Kai Ritterbusch
 */
public class SymbolSet implements Iterable<Symbol> {

	private List<Symbol> set = new ArrayList<Symbol>();

	public SymbolSet() {
	}

	public SymbolSet(Symbol token) {
		set.add(token);
	}

	/**
	 * Initializes the symbol set from a string where each
	 * letter is one symbol.
	 * @param str
	 */
	public SymbolSet(String str) {
		for (int n = 0; n < str.length(); n++) {
			set.add(Symbol.create(str.substring(n, n + 1)));
		}
	}

	public void add(Symbol symbol) {
		this.set.add(symbol);
	}

	public boolean contains(Symbol symbol) {
		return this.set.contains(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SymbolSet) {
			return this.set.equals(((SymbolSet)obj).set);
		}
		return false;
	}

	public Symbol get(int n) {
		return this.set.get(n);
	}

	/**
	 * @return A String containing the concatenated Symbol strings.
	 */
	public String getText() {
		StringBuilder str = new StringBuilder();
		for (Symbol tok : this.set) {
			str.append(tok.toString());
		}
		return str.toString();
	}

	public List<Variable> getVariables() {
		List<Variable> variables = new ArrayList<Variable>();

		for(Symbol symbol : set) {
			if(symbol instanceof Variable) {
				variables.add((Variable)symbol);
			}
		}

		return variables;
	}

	@Override
	public int hashCode() {
		return this.set.hashCode();
	}

	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	public Iterator<Symbol> iterator() {
		return this.set.iterator();
	}

	public int size() {
		return this.set.size();
	}

	/**
	 * @return A verbose textual representation of this SymbolSet.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Symbol tok : this.set) {
			str.append(tok.toString());
			str.append(' ');
		}
		return str.toString();
	}
}
