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

/**
 * This class represents a symbol of an alphabet.
 * @author Christian Lins (christian.lins@web.de)
 * @author Kai Ritterbusch (kai.ritterbusch@osnanet.de)
 */
public class Symbol
{

  public static Symbol create(String text)
  {
    char c = text.charAt(0);
    if(Character.isUpperCase(c))
      return new Variable(text);
    else
      return new Constant(text);
  }
  
  private String text;
  
  protected Symbol(String text)
  {
    assert text != null;

    this.text = text.trim();
  }
  
  public boolean equals(String str)
  {
    return this.text.equals(str);
  }
  
  public boolean equals(Symbol symb)
  {
    return equals(symb.toString());
  }
  
  public int hashCode()
  {
    return this.text.hashCode();
  }
  
  public String toString()
  {
    return this.text;
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }
  
  
  
}
