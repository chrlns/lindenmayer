/*
 *  Lindenmayer
 *  see AUTHORS for a list of contributors.
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import lindenmayer.grammar.Rule;
import lindenmayer.grammar.Symbol;

/**
 * The ContextMenu that allows editing of the Rules.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class ContextMenu extends JPopupMenu implements ActionListener 
{
  /**
   * Two types of menus: the one for editing the Alphabet und the other
   * one for editing a Rule.
   */
  public static enum Caller
  {
    TYPE_ALPHABET,
    TYPE_RULE
  }
  
  private Caller    callerType;
  private JMenuItem mnuItemNew    = new JMenuItem("Neu..."); 
  private JMenuItem mnuItemEdit   = new JMenuItem("Bearbeiten...");
  private JMenuItem mnuItemDelete = new JMenuItem("Entfernen");
  private JList     parentList;
  
  public ContextMenu(JList parentLi, Caller callerType)
  {
    this.callerType = callerType;
    this.parentList = parentLi;
    
    add(mnuItemNew);    
    add(mnuItemEdit);
    add(mnuItemDelete);
  
    // Add action listener
    mnuItemNew.addActionListener(this);
    mnuItemEdit.addActionListener(this);
    mnuItemDelete.addActionListener(this);
  }

  private Rule createRule(String str)
  {
    String[] split = str.split("-->");
    if(split == null || split.length != 2)
    {
      System.err.println("Invalid input!");
      return null;
    }
    
    String input  = split[0].trim();
    String output = split[1].trim();
    
    if(Character.isLowerCase(input.charAt(0)))
    {
      System.err.println("Only capital letters as input!");
      return null;
    }
    
    return null; //new Rule(Symbol.create(input), new SymbolSet(output));
  }
  
  public void actionPerformed(ActionEvent e)
  {
    Object src     = e.getSource();
   
    //TODO: Regel oder Alphabet abpruefen
    if(src.equals(mnuItemNew))
    {
      EditRuleDialog editRuleDlg = new EditRuleDialog();
      editRuleDlg.setVisible(true);
      
      String s = JOptionPane.showInputDialog( "Bitte eingeben" );
      
      if(callerType == Caller.TYPE_RULE)
      {
        Rule rule = createRule(s);
        if(rule == null)
        {
          JOptionPane.showMessageDialog(this, "Ungueltige Eingabe (Bitte immer Form Input --> Production beachten)");
          return;
        }
        MainFrame.getInstance().getGrammar().addRuleToRules(rule);
        ((DefaultListModel)parentList.getModel()).addElement(rule);        
      }
      else if(callerType == Caller.TYPE_ALPHABET)
      {
        MainFrame.getInstance().getGrammar().addElementToAlphabet(Symbol.create(s));
        ((DefaultListModel)parentList.getModel()).addElement(Symbol.create(s));        
      }
      else
      {
        System.err.println("Fehler");
      }
    }
    else if(src.equals(mnuItemEdit))
    {
      //TODO: Fehler bei doppelten Aenderungen
      if(callerType == Caller.TYPE_ALPHABET)
      {
        String s = JOptionPane.showInputDialog("Bitte eingeben", parentList.getSelectedValue());        
        Symbol sym = Symbol.create(s);
        MainFrame.getInstance().getGrammar().changeElementInAlphabet( (Symbol)parentList.getSelectedValue(), sym );
        int index = ((DefaultListModel)parentList.getModel()).indexOf( (Symbol)parentList.getSelectedValue());
        ((DefaultListModel)parentList.getModel()).set(index, sym);        
      }
      else if(callerType == Caller.TYPE_RULE)
      {
        String s = JOptionPane.showInputDialog("Bitte eingeben", parentList.getSelectedValue());
        Rule rule = createRule(s);
        MainFrame.getInstance().getGrammar().changeElementInRuleSet( (Rule)parentList.getSelectedValue(), rule );
        int index = ((DefaultListModel)parentList.getModel()).indexOf((Rule)parentList.getSelectedValue());
        ((DefaultListModel)parentList.getModel()).set(index, rule);
      }
    }
    else if(src.equals(mnuItemDelete))
    {
      if(callerType == Caller.TYPE_ALPHABET)
      {
        MainFrame.getInstance().getGrammar().getAlphabet().getSymbols().remove(parentList.getSelectedValue());
        ((DefaultListModel)parentList.getModel()).removeElement(parentList.getSelectedValue());
      }
      else if(callerType == Caller.TYPE_RULE)
      {
        MainFrame.getInstance().getGrammar().getRules().remove(parentList.getSelectedValue());
        ((DefaultListModel)parentList.getModel()).removeElement(parentList.getSelectedValue());
      }
    }
  }
}
