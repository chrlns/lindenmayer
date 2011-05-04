/*
 *  Lindenmayer
 *  Copyright (C) 2007-2009 Christian Lins <cli@openoffice.org>
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

package lindenmayer;

import javax.swing.UIManager;

import lindenmayer.gui.MainFrame;

/**
 * Start class of the Lindenmayer program.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Main 
{

  public static final String VERSION = "Lindenmayer/0.4";
  
  /**
   * Entrypoint method.
   * @param args
   */
  public static void main(String[] args) 
  {
    boolean usePlaf = true;
	  
    for(int n = 0; n < args.length; n++)
    {
      if(args[n].equals("-noplaf"))
      {
        usePlaf = false;
      }
    }

    try
    {
      if(!usePlaf)
      {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
    }
    catch(Exception ex)
    {
      System.err.println("Could not load system Look and Feel");
      System.err.println(ex.getLocalizedMessage());
    }

    MainFrame.getInstance().setVisible(true);
  }

}
