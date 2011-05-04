/*
 *  Lindenmayer
 *  Copyright (C) 2007-2009 Christian Lins <christian.lins@fh-osnabrueck.de>
 *  Copyright (C) 2007,2008 Kai Ritterbusch <kai.ritterbusch@osnanet.de>
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

/**
 * A replacement rule. A rule has one (or possibly more) input symbol(s) and
 * a specified set of symbols as production (output).
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Rule
{

  private Symbol    input;
  private SymbolSet production;
  private boolean   stackless;
  
  public Rule(boolean stackless, Symbol input, SymbolSet production)
  {
    this.input      = input;
    this.production = production;
    this.stackless  = stackless;
  }

  public Symbol getInput()
  {
    return input;
  }

  public SymbolSet getProduction()
  {
    return production;
  }
  
  /**
   * If this method returns true the evaluation algorithm must simply replace
   * the input by the production, without pushing/popping states from the
   * evaluation stack.
   */
  public boolean isStackless()
  {
    return this.stackless;
  }
  
  @Override
  public String toString()
  {
    String arrow = "-->";
    if(stackless)
    {
      arrow = "-->>";
    }
    return input.toString() + arrow + production.toString();
  }

}
