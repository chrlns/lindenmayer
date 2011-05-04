/*
 *  Lindenmayer
 *  Copyright 2007, 2008 Kai Ritterbusch <kai.ritterbusch@osnanet.de>
 *  Copyright 2007, 2008 Christian Lins <christian.lins@web.de>
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

/**
 * A set of tokens. Contains at least one token.
 * @author Christian Lins 
 * @author Kai Ritterbusch
 */
public class SymbolSet extends ArrayList<Symbol>
{
  public SymbolSet()
  {  
  }
  
  public SymbolSet(Symbol token)
  {
    add(token);
  }
  
  /**
   * Initializes the symbol set from a string where each
   * letter is one symbol.
   * @param str
   */
  public SymbolSet(String str)
  {
    for(int n = 0; n < str.length(); n++)
    {
      add(new Symbol(str.substring(n, n+1)));
    }
  }
  
  public boolean equals(SymbolSet sset)
  {
    if(sset.size() != size())
      return false;
    else
    {
      for(int n = 0; n < size(); n++)
      {
        if(!sset.get(n).equals(get(n)))
          return false;
      }
      return true;
    }
  }
  
  public String toString()
  {
    StringBuilder str = new StringBuilder();
    for(Symbol tok : this)
      str.append(tok.toString());
    
    return str.toString();
  }
}
