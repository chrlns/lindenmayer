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
package me.lins.lindenmayer.grammar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A container of tokens.
 *
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class SymbolList implements Iterable<Symbol> {

    private final List<Symbol> container;

    public SymbolList() {
        this.container = new ArrayList<>();
    }

    public SymbolList(Symbol token) {
        this.container = new ArrayList<>();
        container.add(token);
    }

    /**
     * Initializes the symbol container from a string where each letter is one symbol.
     *
     * @param str
     */
    public SymbolList(String str) {
        this.container = new ArrayList<>();
        for (int n = 0; n < str.length(); n++) {
            container.add(Symbol.create(str.substring(n, n + 1)));
        }
    }

    public void add(Symbol symbol) {
        this.container.add(symbol);
    }

    public boolean contains(Symbol symbol) {
        return this.container.contains(symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SymbolList) {
            return this.container.equals(((SymbolList) obj).container);
        }
        return false;
    }

    public Symbol first() {
        return this.container.get(0);
    }

    /**
     * @return A String containing the concatenated Symbol strings.
     */
    public String getText() {
        StringBuilder str = new StringBuilder();
        this.container.stream().forEach((tok) -> {
            str.append(tok.toString());
        });
        return str.toString();
    }

    /**
     * 
     * @return A new List containing all Variables 
     */
    public List<Variable> getVariables() {
        List<Variable> variables = new ArrayList<>(container.size());

        container.stream()
                .filter(s -> (s instanceof Variable))
                .forEach(s -> variables.add((Variable)s));

        return variables;
    }

    @Override
    public int hashCode() {
        return this.container.hashCode();
    }

    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    @Override
    public Iterator<Symbol> iterator() {
        return this.container.iterator();
    }

    public int size() {
        return this.container.size();
    }

    /**
     * @return A verbose textual representation of this SymbolList.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        container.stream()
                .forEach((Symbol tok) -> 
                    {
                        str.append(tok.toString()); 
                        str.append(" ");
                    });
        return str.toString();
    }
}
