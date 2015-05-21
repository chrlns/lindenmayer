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
package me.lins.lindenmayer.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.lins.lindenmayer.grammar.Constant;
import me.lins.lindenmayer.grammar.Rule;
import me.lins.lindenmayer.grammar.Symbol;
import me.lins.lindenmayer.grammar.SymbolList;
import me.lins.lindenmayer.grammar.Variable;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * JDOM XML parser that loads the Grammar from an XML file.
 *
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class XMLParser {

    private Document doc = null;
    private Element root = null;

    public XMLParser(File file) {
        try {
            doc = new SAXBuilder().build(file.getAbsolutePath());
            root = doc.getRootElement();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Reads from XML-File Saves to SymbolList
     */
    public SymbolList getAlphabet() {
        SymbolList tokens = new SymbolList();

        Element grammar = root.getChild("grammar");
        Element alphabet = grammar.getChild("symbols");
        for (Object el : alphabet.getChildren()) {
            if (((Element) el).getAttributeValue("type").equals("variable")) {
                tokens.add(new Variable(((Element) el).getValue()));
            } else {
                tokens.add(new Constant(((Element) el).getValue()));
            }
        }

        return tokens;
    }

    public List<Rule> getRules()
            throws DataConversionException {
        List<Rule> rulesList = new ArrayList<Rule>();

        Element grammar = root.getChild("grammar");
        Element rules = grammar.getChild("rules");
        for (Object obj : rules.getChildren("rule")) {
            Element rule = (Element) obj;
            Element left = rule.getChild("left");
            Element right = rule.getChild("right");

            SymbolList inputTokens = new SymbolList();
            SymbolList prodTokens = new SymbolList();

            // This can be only one!
            for (Object objT : left.getChildren("symbol")) {
                inputTokens.add(getSymbol((Element) objT));
            }

            for (Object objT : right.getChildren("symbol")) {
                prodTokens.add(getSymbol((Element) objT));
            }

            rulesList.add(new Rule(inputTokens.first(), prodTokens));
        }

        return rulesList;
    }

    public Symbol getStartSymbol() {
        Element grammar = root.getChild("grammar");
        Element rules = grammar.getChild("startsymbol");

        return Symbol.create(rules.getValue());
    }

    private Symbol getSymbol(Element e) {
        return Symbol.create(e.getValue());
    }
}
