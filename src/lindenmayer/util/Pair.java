/*
 *  Lindenmayer
 *  Copyright (c) 2007-2009 Christian Lins <christian.lins@web.de>
 *  Copyright (c) 2007,2008 Kai Ritterbusch <kai.ritterbusch@osnanet.de>
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

package lindenmayer.util;

/**
 * A generic pair of objects.
 * @author Christian Lins (christian.lins@web.de)
 *
 * @param <T1>
 * @param <T2>
 */
public class Pair<T1, T2>
{
  private T1 objA;
  private T2 objB;
  
  public Pair(T1 a, T2 b)
  {
    objA = a;
    objB = b;
  }

  @Override
  public boolean equals(Object obj)
  {
    if(obj instanceof Pair)
    {
      Pair<?, ?> p = (Pair<?, ?>)obj;
      return p.getObjA().equals(getObjA()) && p.getObjB().equals(getObjB());      
    }
    else
      return false;
  }

  @Override
  public int hashCode()
  {
    return objA.hashCode() ^ objB.hashCode();
  }

  public T1 getObjA()
  {
    return objA;
  }

  public void setObjA(T1 objA)
  {
    this.objA = objA;
  }

  public T2 getObjB()
  {
    return objB;
  }

  public void setObjB(T2 objB)
  {
    this.objB = objB;
  }
  
}
