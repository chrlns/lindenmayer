/*
 *  Lindenmayer
 *  Copyright (C) 2007,2008 Kai Ritterbusch <kai.ritterbusch@osnanet.de>
 *  Copyright (C) 2007-2009 Christian Lins <christian.lins@fh-osnabrueck.de>
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

package lindenmayer.io;

import java.io.File;
import java.util.ArrayList;

import java.util.List;
import lindenmayer.grammar.Alphabet;
import lindenmayer.grammar.Constant;
import lindenmayer.grammar.Rule;
import lindenmayer.grammar.Symbol;
import lindenmayer.grammar.SymbolSet;
import lindenmayer.grammar.Variable;

import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * JDOM XML parser that loads the Grammar from an XML file.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class XMLParser
{
  private Document doc  = null;
  private Element  root = null;
  
  public XMLParser(File file)
  {
    try
    {
      doc  = new SAXBuilder().build(file.getAbsolutePath());
      root = doc.getRootElement();
    }
    catch (Exception e) 
    {
      e.printStackTrace();
    }
  }
  
  /** Reads from XML-File Saves to SymbolSet */
  public Alphabet getAlphabet()
  {
    SymbolSet tokens = new SymbolSet();
    
    Element grammar = root.getChild("grammar");
    Element alphabet = grammar.getChild("symbols");
    for(Object el  : alphabet.getChildren())
      if( ((Element)el).getAttributeValue("type").equals("variable") )
        tokens.add(new Variable( ((Element)el).getValue() ));
      else             
        tokens.add(new Constant(((Element)el).getValue()));
      
    return new Alphabet(tokens);
  }

  public List<Rule> getRules()
    throws DataConversionException
  {
    ArrayList<Rule> rulesList = new ArrayList<Rule>();
    
    Element grammar = root.getChild("grammar");
    Element rules   = grammar.getChild("rules");
    for(Object obj : rules.getChildren("rule"))
    {
      Element rule  = (Element)obj;
      Element left  = rule.getChild("left");
      Element right = rule.getChild("right");
      
      SymbolSet inputTokens = new SymbolSet();
      SymbolSet prodTokens  = new SymbolSet();
      
      // This can be only one!
      for(Object objT : left.getChildren("symbol"))
      {
        inputTokens.add(getSymbol((Element)objT));
      }
      
      for(Object objT : right.getChildren("symbol"))
      {
        prodTokens.add(getSymbol((Element)objT));
      }
      
      boolean   isStackless = false;
      Attribute attr        = rule.getAttribute("stackless");
      if(attr != null)
      {
        isStackless = attr.getBooleanValue();
      }
      
      rulesList.add(new Rule(isStackless, inputTokens.get(0), prodTokens));
    }
    
    return rulesList;
  }
  
  public Symbol getStartSymbol()
  {
    Element grammar = root.getChild("grammar");
    Element rules   = grammar.getChild("startsymbol");
    
    return Symbol.create(rules.getValue());
  }
  
  private Symbol getSymbol(Element e)
  {
    return Symbol.create(e.getValue());
  }
}