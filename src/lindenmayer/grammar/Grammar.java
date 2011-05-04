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

package lindenmayer.grammar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lindenmayer.io.XMLParser;
import org.jdom.DataConversionException;

/**
 * The grammar that is used for "growing" the plant.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Grammar
{

  private Alphabet   alphabet    = new Alphabet(new SymbolSet());
  private File       file        = null;
  private List<Rule> rules       = new ArrayList<Rule>();
  private Symbol     startSymbol;
  
  public Grammar()
  {
  }
  
  public Grammar(File file)
  {
    this.file = file;
  }
  
  /** Reads from XML-File */
  public void loadFromXML()
    throws DataConversionException
  {
    XMLParser xml = new XMLParser(this.file);
    
    // Load parts of this grammar
    alphabet    = xml.getAlphabet();
    rules       = xml.getRules();
    startSymbol = xml.getStartSymbol();
  }
  
  /** Saves to XML-File */
  public void saveToXML(File file)
  {
    if(!file.getAbsoluteFile().toString().endsWith(".xml"))
      file = new File(file.getAbsoluteFile().toString() + ".xml");
    
    StringBuffer sb = new StringBuffer();
    
    try
    {
      sb.append("<?xml version=\"1.0\" ?>\n");
      sb.append("<lindenmayer>\n");
      sb.append("<grammar>\n");
      sb.append("<symbols>\n");
      for(Symbol sym : alphabet.getSymbols())
      {
        if(sym instanceof Variable)        
          sb.append("<symbol type=\"variable\">"+ sym +"</symbol>\n");
        else if (sym instanceof Constant)
          sb.append("<symbol type=\"terminal\">"+ sym +"</symbol>\n");
      }    
      sb.append("</symbols>\n");
      
      sb.append("<rules>\n");
      for(Rule rule : rules)
      {
        sb.append("<rule>\n");
        sb.append("<left>\n");
        sb.append("<symbol>"+ rule.getInput() +"</symbol>\n");
        sb.append("</left>\n");
        sb.append("<right>\n");
        for(Symbol sym : rule.getProduction())
        {
          sb.append("<symbol>"+ sym +"</symbol>\n");
        }
        sb.append("</right>\n");
        sb.append("</rule>\n");
      }
      sb.append("</rules>\n");
      sb.append("<startsymbol>\n");
      sb.append("<symbol>"+ startSymbol +"</symbol>\n");
      sb.append("</startsymbol>\n");  
      sb.append("</grammar>\n");
      sb.append("</lindenmayer>\n");
    }
    catch (Exception e) 
    {
      e.printStackTrace();
    }
    try
    {
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw); 
      bw.write(sb.toString());
      bw.close(); 
    }
    catch (Exception e) 
    {
      e.printStackTrace();
    }
  }

  public Alphabet getAlphabet()
  {
    return alphabet;
  }
  
  /**
   * Adds a Symbol to the Alphabet
   * @param sym specific Symbol to add
   */   
  public void addElementToAlphabet(Symbol sym)
  {
    alphabet.addSymbol(sym);
  }
  
  /**
   * 
   * @param oldSym
   * @param newSym
   */
  public void changeElementInAlphabet(Symbol oldSym, Symbol newSym)
  {    
    int index = alphabet.getSymbols().indexOf(oldSym);    
    alphabet.getSymbols().set(index, newSym);
  }

  public File getFile()
  {
    return file;
  }

  public List<Rule> getRules()
  {
    return rules;
  }
  
  public Symbol getStartSymbol()
  {
    return this.startSymbol;
  }
  
  /**
   * Adds a Rule to the RuleList
   * @param rule specific rule to add to the RuleList
   */
  public void addRuleToRules(Rule rule)
  {
    rules.add(rule);
  }
  
  /**
   * 
   * @param oldRule
   * @param newRule
   */
  public void changeElementInRuleSet(Rule oldRule, Rule newRule)
  {
    int index = rules.indexOf(oldRule);    
    rules.set(index, newRule);    
  }
  
  /**
   * Returns a Rule that handles the given SymbolSet as input.
   * @param sset
   * @param terminalOnly Returns a rule that has only terminals as output if this is true.
   * @return
   */
  public Rule getRuleFor(Symbol symb, boolean terminalOnly)
  {
    List<Rule> possibleRules = new ArrayList<Rule>();
    
    for(Rule rule : rules)
    {
      if(rule.getInput().equals(symb))
      {
        if(!terminalOnly)
          possibleRules.add(rule);
        else
        {
          if(checkRuleForVariable(rule))  
            break;
          else
            possibleRules.add(rule);
        }
      }
    }
    
    if(possibleRules.size() == 0)
      return null;
    
    Random rand = new Random();
    
    return possibleRules.get(rand.nextInt(possibleRules.size())); 
  }
  
  public static boolean checkRuleForVariable(Rule rule)
  {
    return (rule.getInput() instanceof Variable);
  }

  public void setStartSymbol(String strStartSymbol)
  {
    this.startSymbol = new Symbol(strStartSymbol);
  }

}
