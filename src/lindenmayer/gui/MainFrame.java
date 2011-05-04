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
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import lindenmayer.Main;
import lindenmayer.grammar.Grammar;
import org.jdom.DataConversionException;

/**
 * The main application window.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class MainFrame extends JFrame
{

  private static volatile MainFrame instance = null;
  
  /** @return The current instance of the MainFrame */
  public static MainFrame getInstance()
  {
    if(instance == null)
    {
      instance = new MainFrame();
    }
    
    return instance;
  }
  
  private Grammar grammar = null;

  private StatusBar statusBar;
  private JSplitPane splitPane; 
  private LogPanel inputPanel;
  private ToolBar     toolBar;

  private CanvasPanel canvasPanel;
  
  // Panels
  private GrammarPanel grammarPanel = null;
  private LogPanel     logPanel     = null;
  
  private MainFrame()
  {
    setTitle(Main.VERSION);
    setSize(800, 600);    

    setJMenuBar(new MenuBar(this));
    getContentPane().setLayout(new BorderLayout());
    
    getContentPane().add(toolBar = new ToolBar(), BorderLayout.NORTH);
    
    // Create TabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Grammatik", grammarPanel);
    tabbedPane.addTab("Log", logPanel);
    
    // Creates JSplitPane
    canvasPanel = new CanvasPanel();
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvasPanel, tabbedPane);
    splitPane.setOneTouchExpandable(true);    
    splitPane.setDividerLocation((int)(getWidth() * 0.75));   
    getContentPane().add(splitPane, BorderLayout.CENTER);
    statusBar = new StatusBar();
    getContentPane().add(statusBar, BorderLayout.SOUTH);
   
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Utils.centerOnScreen(this);
  }
  
  public Grammar getGrammar()
  {
    return this.grammar;
  }
  
  public void loadGrammar()
  {
    try
    {
      XMLFileChooser chooser = new XMLFileChooser();

      if (chooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION)
      {
        loadGrammarFromFile(chooser.getSelectedFile());
        this.statusBar.setText("Datei " + chooser.getSelectedFile() + " geladen.");
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      String[] msg = {
        "Beim Laden der Grammatik ist ein Fehler aufgetreten:",
        ex.getLocalizedMessage()
      };
      JOptionPane.showMessageDialog(this, msg, "Lindenmayer", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public void newGrammar()
  {
    this.grammar = new Grammar();
    
    canvasPanel = new CanvasPanel();
    toolBar.setButtonStatus(true);    // enable Buttons
    getContentPane().remove(splitPane);    
    //  create TabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Grammatik", new GrammarPanel(this));
    logPanel = new LogPanel();
    tabbedPane.addTab("Log", logPanel);
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvasPanel, tabbedPane);
    splitPane.setOneTouchExpandable(true);    
    splitPane.setDividerLocation((int)(getWidth() * 0.75));  
    getContentPane().add(splitPane, BorderLayout.CENTER);
    
    setVisible(true);
  }
  
  public void saveGrammar()
  {
    // If no grammar is loaded, create an empty one
    if(this.grammar == null)
    {
      this.grammar = new Grammar();
    }

    if(grammar.getFile() == null)
    {
      // Save to new file
      saveGrammarAs();
    }
    else
    {
      // Save to already known file
      grammar.saveToXML(grammar.getFile());
    }
  }
  
  public void saveGrammarAs()
  {
    // If no grammar is loaded, create an empty one
    if(this.grammar == null)
    {
      this.grammar = new Grammar();
    }

    XMLFileChooser chooser = new XMLFileChooser();
    if (chooser.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION)
    {      
      MainFrame.getInstance().statusBar.setText("Datei "+chooser.getSelectedFile()+" gespeichert.");      
      grammar.saveToXML(chooser.getSelectedFile());
    }
  }
  
  public void loadGrammarFromFile(File file)
    throws DataConversionException
  {    
    grammar = new Grammar(file);
    grammar.loadFromXML();
    canvasPanel = new CanvasPanel();
    toolBar.setButtonStatus(true);    // enable Buttons
    getContentPane().remove(splitPane);    
    // create TabbedPane
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Grammatik", new GrammarPanel(this));
    logPanel = new LogPanel();
    tabbedPane.addTab("Log", logPanel);
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvasPanel, tabbedPane);
    splitPane.setOneTouchExpandable(true);    
    splitPane.setDividerLocation((int)(getWidth() * 0.75));  
    getContentPane().add(splitPane, BorderLayout.CENTER);
  }
  
  public LogPanel getInputPanel()
  {
    return this.inputPanel;
  }

  public CanvasPanel getCanvasPanel()
  {
    return this.canvasPanel;
  }
  
  public JSplitPane getSplitPane()
  {
    return this.splitPane;
  }
  
  public void setCanvasPanel(CanvasPanel cp)
  {
    this.canvasPanel = cp;
  }

  public GrammarPanel getGrammarPanel()
  {
    return grammarPanel;
  }
  
  public LogPanel getLogPanel()
  {
    return logPanel;
  }
  
  public ToolBar getToolBar()
  {
    return toolBar;
  }

}
