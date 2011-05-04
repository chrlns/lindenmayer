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

package lindenmayer.gui;

import java.awt.Dimension;

import javax.swing.JLabel;

/**
 * The StatusBar shows information at the bottom of the MainFrame.
 * @author Christian Lins (christian.lins@web.de)
 * @author Kai Ritterbusch (kai.ritterbusch@osnanet.de)
 */
public class StatusBar extends JLabel
{
  /** Default constructor */
  public StatusBar()
  {
    super.setPreferredSize(new Dimension(100,16));
    setMessage("Bereit");
  }
  
  /** Constructor */
  public StatusBar(String str)
  {
    this();
    setMessage(str);
  }
    
  /** Sets text content */
  public void setMessage(String message)
  {
    setText("  " + message);
  }
}
