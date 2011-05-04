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

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lindenmayer.grammar.DefaultNode;
import lindenmayer.grammar.Node;
import lindenmayer.grammar.Plant;
import lindenmayer.grammar.SymbolSet;
import lindenmayer.grammar.TreePlant;

/**
 * The CanvasPanel is on the left side of the MainFrame and contains
 * the Plant that is painted.
 * @author Christian Lins (christian.lins@web.de)
 * @author Kai Ritterbusch (kai.ritterbusch@osnanet.de)
 */
public class CanvasPanel extends JPanel
{
  private JScrollPane      scrollPane = null;
  private Plant            plant;
  
  public CanvasPanel()
  {
    setLayout(new BorderLayout());
  }

  /**
   * Creates a new Plant.
   */
  public void createPlant()
  {
    try
    {
      System.out.println("startsymbol: "+MainFrame.getInstance().getGrammar().getStartSymbol().toString().trim());
      plant = new Plant(new SymbolSet(MainFrame.getInstance().getGrammar().getStartSymbol().toString().trim()));
      Node treeRoot = DefaultNode.createParseTree(MainFrame.getInstance().getGrammar(), 5);
      
      if(scrollPane != null)
        remove(scrollPane);
      
      scrollPane = new JScrollPane(new TreePlant(treeRoot));
      add(scrollPane, BorderLayout.CENTER);
      MainFrame.getInstance().getSplitPane().setDividerLocation(
          MainFrame.getInstance().getSplitPane().getDividerLocation());
     
      // TODO: nur ein workaround
      scrollPane.getHorizontalScrollBar().setValue(getWidth()/2);
      scrollPane.getVerticalScrollBar().setValue(plant.getHeight());
      
    }
    catch(NullPointerException ex)
    {
      ex.printStackTrace();
      String[] msg = {"Keine Grammatik geladen!"};
      JOptionPane.showMessageDialog(MainFrame.getInstance(), msg);
    }
  }
  
  public Plant getPlant()
  {
    return this.plant;
  }

  public JScrollPane getScrollPane()
  {
    return scrollPane;
  }

  public void setScrollPane(JScrollPane scrollPane)
  {
    this.scrollPane = scrollPane;
  }
}
