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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import lindenmayer.grammar.Grammar;
import lindenmayer.grammar.Rule;
import lindenmayer.grammar.Symbol;

/**
 * This Panel allows editing of the loaded Grammar.
 * @author Christian Lins (christian.lins@web.de)
 * @author Kai Ritterbusch (kai.ritterbusch@osnanet.de)
 */
public class GrammarPanel extends JPanel
{
  private MainFrame parent;

  private JTextArea txtRules = new JTextArea();
  private JTextArea txtInput = new JTextArea();
  
  private DefaultListModel listModelGrammar = new DefaultListModel();
  private JList listGrammar;
  private DefaultListModel listModelRules = new DefaultListModel();
  private JList listRules;
  
  public GrammarPanel(MainFrame prt)
  {    
    this.parent = prt;
    
    txtInput.addKeyListener(new KeyAdapter()
    {
      public void keyReleased( KeyEvent e ) 
      {
        parent.getGrammar().setStartSymbol(txtInput.getText().trim());
        System.out.println("neues Startsymbol: " + parent.getGrammar().getStartSymbol().toString());
      } 
    });
    
    StringBuilder str = new StringBuilder();
    
    Grammar g = MainFrame.getInstance().getGrammar();
    if(g != null)
    {
      if(g.getStartSymbol() != null)
        txtInput.setText(g.getStartSymbol().toString().trim());
      
      if(g.getAlphabet() != null)
      {
        for(Symbol tok : g.getAlphabet().getSymbols())
        {
          str.append(tok.toString()+"\n");
          listModelGrammar.addElement(tok);
        }
      }
      
      str = new StringBuilder();
      if(g.getRules() != null)
      {
        for(Rule rule : g.getRules())
        {
          str.append(rule.toString()+"\n");
          listModelRules.addElement(rule);
        }
      }
    }
    
    listGrammar = new JList(listModelGrammar);
    listGrammar.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    listGrammar.setLayoutOrientation(JList.VERTICAL);
    listGrammar.setVisibleRowCount(2);
    JScrollPane listScroller = new JScrollPane(listGrammar);
    listScroller.setPreferredSize(new Dimension(250, 100));
    //txtGrammar.setText(str.toString());
    //  Mouseklicks abfangen
    listGrammar.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        if(e.getButton() == MouseEvent.BUTTON3)
        { 
          listGrammar.setSelectedIndex(listGrammar.locationToIndex(e.getPoint()));
          new ContextMenu(listGrammar, ContextMenu.Caller.TYPE_ALPHABET).show(listGrammar, e.getX(), e.getY());
        }
      }
    });
    
    listRules = new JList(listModelRules);
    listRules.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    listRules.setLayoutOrientation(JList.VERTICAL);
    listRules.setVisibleRowCount(2);
    
    //  Mouseklicks abfangen
    listRules.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {        
        if(SwingUtilities.isRightMouseButton(e))
        {           
          listRules.setSelectedIndex(listRules.locationToIndex(e.getPoint()));
          new ContextMenu(listRules, ContextMenu.Caller.TYPE_RULE).show(listRules, e.getX(), e.getY());
        }
      }
    });
    JScrollPane listScrollerRules = new JScrollPane(listRules);
    listScrollerRules.setPreferredSize(new Dimension(250, 100));    
    
    JPanel inputs    = new JPanel(new BorderLayout());
    JPanel alphabet  = new JPanel(new BorderLayout());
    JPanel rules     = new JPanel(new BorderLayout());
    
    inputs.add(new JLabel("Startsymbol"), BorderLayout.NORTH);
    inputs.add(txtInput, BorderLayout.CENTER);    
    
    alphabet.add(new JLabel("Alphabet"), BorderLayout.NORTH);
    alphabet.add(listScroller, BorderLayout.CENTER);    
    
    rules.add(new JLabel("Regeln"), BorderLayout.NORTH);
    rules.add(listScrollerRules, BorderLayout.CENTER);
    
    setLayout(new GridLayout(0, 1));
    
    add(inputs);
    add(alphabet);
    add(rules);
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(250, getHeight());
  }

  public JTextArea getTxtInput()
  {
    return txtInput;
  }

  public void setTxtInput(JTextArea txtInput)
  {
    this.txtInput = txtInput;
  }

  public JTextArea getTxtRules()
  {
    return txtRules;
  }
}
