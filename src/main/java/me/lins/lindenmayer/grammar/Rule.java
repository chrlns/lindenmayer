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

/**
 * A replacement rule. A rule has one (or possibly more) input symbol(s) and
 * a specified set of symbols as production (output).
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Rule {

	private Symbol input;
	private SymbolList production;

	public Rule(Symbol input, SymbolList production) {
		this.input = input;
		this.production = production;
	}

	public Symbol getInput() {
		return input;
	}

	public SymbolList getProduction() {
		return production;
	}

	@Override
	public String toString() {
		return input.toString() + " --> " + production.toString();
	}
}
