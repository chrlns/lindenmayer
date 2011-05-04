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
 * Represents a variable symbol. A Variable can be replaced by a set of
 * symbols (constants and variables) through a Rule.
 * @author Christian Lins (christian.lins@netvader.de)
 * @author Kai Ritterbusch (kai.ritterbusch@osnanet.de)
 */
public class Variable extends Symbol
{
   public Variable(String text)
   {
     super(text);
   }
}
