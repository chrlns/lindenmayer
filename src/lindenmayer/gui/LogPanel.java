/*
 *  Lindenmayer
 *  Copyright (C) 2007,2008 Kai Ritterbusch <kai.ritterbusch@osnanet.de>
 *  Copyright (C) 2007-2009 Christian Lins <cli@openoffice.org>
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
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LogPanel extends JPanel
{  
  private DefaultListModel listModel = new DefaultListModel();
  private JList            recDepth  = new JList(listModel);    // List with recursion depth
  private JTextArea        subs      = new JTextArea();         // Description of the substitutions 
  
  /**
   *  The key is "Rekursion XX" for the XX recursion. The value is an ArrayList
   *  of Strings representing the replacement rules.
   */
  private Map<String, Set<String>> logs = new HashMap<String, Set<String>>();
  
  public LogPanel()
  {
    setLayout(new GridLayout(0, 1));
    
    
    JPanel recPanel   = new JPanel(new BorderLayout());
    JPanel subPanel   = new JPanel(new BorderLayout());
    
    recPanel.add(new JLabel("Rekursionstiefe"), BorderLayout.NORTH);
    recPanel.add(new JScrollPane(recDepth), BorderLayout.CENTER);
    subPanel.add(new JLabel("Angew. Regeln"), BorderLayout.NORTH);
    subPanel.add(new JScrollPane(subs), BorderLayout.CENTER);     
    
    recDepth.addListSelectionListener(new ListSelectionListener() 
    {
      public void valueChanged( ListSelectionEvent e ) 
      {
        if(recDepth.getSelectedValue() == null)
          return;
        
        String        key = recDepth.getSelectedValue().toString();
        StringBuilder tmp = new StringBuilder();
        tmp.append("Ersetzungen in ");
        tmp.append(key);
        tmp.append("\n");
        
        Set<String> log = logs.get(key);
        if(log == null)
          tmp.append("Keine\n");
        else
        {
          for(String s : log)
          {
            tmp.append(s);
            tmp.append("\n");
          }
        }
        
        subs.setText(tmp.toString());
      }
    } );
  
    add(recPanel);
    add(subPanel);
  }
  
  public void addNewRecDepth(String recursion, String rule)
  {
    Set<String> log = logs.get(recursion);
    if(log == null)
    {
      log = new HashSet<String>();
      logs.put(recursion, log);
      
      listModel.addElement(recursion);
    }
    
    // This method may be called multiple time, so we have to check
    // for duplicates
    if(!log.contains(rule))
    {
      log.add(rule);
    }
  }
  
  public void reset()
  {
    listModel.removeAllElements();
    logs.clear();
    subs.setText("");
  }
  
}
