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

import java.util.ArrayList;
import java.util.List;

/**
 * Parse tree node of a Lindenmayer System.
 * @author Christian Lins
 */
public class DefaultNode implements Node
{

  /**
   * Create parse tree for the given grammar down to maxLevel.
   * @param grammar
   * @param maxLevel
   */
  public static DefaultNode createParseTree(Grammar grammar, int maxLevel)
  {
    Symbol symb = grammar.getStartSymbol();
    Rule   rule = grammar.getRuleFor(symb, false);

    return new DefaultNode(grammar, symb, rule, maxLevel);
  }
  
  private List<Node>  children = new ArrayList<Node>();
  private Symbol      symbol   = null;
  private Rule        rule     = null;
  
  /**
   * Performes a substution of the symbol using the given rule.
   * The resulting symbols are added as child nodes of this node.
   * @param symb
   * @param rule
   * @param maxLevel
   */
  public DefaultNode(Grammar grammar, Symbol symb, Rule rule, int maxLevel)
  {
    this.symbol = symb;
    this.rule   = rule;
    
    if(maxLevel > 0 && rule != null)
    {
      maxLevel--;
      SymbolSet production = rule.getProduction();

      // If we use the stack we enclose every invocation of a rule with "(" ")"
     /* if(!rule.isStackless())
      {
        this.children.add(new StackPushNode());
      }*/
      
      for(Symbol prod : production)
      {
        Rule prodRule = grammar.getRuleFor(prod, false);
        Node child    = new DefaultNode(grammar, prod, prodRule, maxLevel);
        this.children.add(child);
      }
      
      /*if(!rule.isStackless())
      {
        this.children.add(new StackPopNode());
      }*/
    }
  }
  
  public List<Node> getChildren()
  {
    return this.children;
  }
  
  public Rule getRule()
  {
    return this.rule;
  }
  
  public Symbol getSymbol()
  {
    return this.symbol;
  }
  
}
