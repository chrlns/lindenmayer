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
package me.lins.lindenmayer.grammar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.lins.lindenmayer.Status;
import me.lins.lindenmayer.io.XMLParser;
import org.jdom.DataConversionException;

/**
 * The grammar that is used for "growing" the plant.
 *
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Grammar {

    private SymbolList alphabet = new SymbolList();
    private File file = null;
    private List<Rule> rules = new ArrayList<>();
    private Symbol startSymbol;
    private Status status;

    public Grammar(Status status) {
        this.status = status;
    }

    public Grammar(Status status, File file) {
        this(status);
        this.file = file;
    }

    /**
     * Checks this grammar for validity and completeness.
     *
     * @return
     */
    public boolean isValid() {
        if (startSymbol == null || rules.isEmpty() || alphabet.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Reads from XML file. TODO: Extract method to separate class.
     */
    public void loadFromXML()
            throws DataConversionException {
        XMLParser xml = new XMLParser(this.file);

        // Load parts of this grammar
        alphabet = xml.getAlphabet();
        rules = xml.getRules();
        startSymbol = xml.getStartSymbol();
    }

    /**
     * Saves to XML file. TODO: Extract method to seperate class.
     */
    public void saveToXML(File file) {
        if (!file.getAbsoluteFile().toString().endsWith(".xml")) {
            file = new File(file.getAbsoluteFile().toString() + ".xml");
        }

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<?xml version=\"1.0\" ?>\n");
            sb.append("<lindenmayer>\n");
            sb.append("<grammar>\n");
            sb.append("<symbols>\n");
            for (Symbol sym : alphabet) {
                if (sym instanceof Variable) {
                    sb.append("<symbol type=\"variable\">");
                    sb.append(sym);
                    sb.append("</symbol>\n");
                } else if (sym instanceof Constant) {
                    sb.append("<symbol type=\"terminal\">");
                    sb.append(sym);
                    sb.append("</symbol>\n");
                }
            }
            sb.append("</symbols>\n");

            sb.append("<rules>\n");
            for (Rule rule : rules) {
                sb.append("<rule>\n");
                sb.append("<left>\n");
                sb.append("<symbol>");
                sb.append(rule.getInput());
                sb.append("</symbol>\n");
                sb.append("</left>\n");
                sb.append("<right>\n");
                for (Symbol sym : rule.getProduction()) {
                    sb.append("<symbol>");
                    sb.append(sym);
                    sb.append("</symbol>\n");
                }
                sb.append("</right>\n");
                sb.append("</rule>\n");
            }
            sb.append("</rules>\n");
            sb.append("<startsymbol>\n");
            sb.append("<symbol>");
            if (startSymbol != null) {
                sb.append(startSymbol);
            }
            sb.append("</symbol>\n");
            sb.append("</startsymbol>\n");
            sb.append("</grammar>\n");
            sb.append("</lindenmayer>\n");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
            this.file = file;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public SymbolList getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(SymbolList set) {
        this.alphabet = set;
        this.status.fireStatusChangeEventInFuture();
    }

    /**
     * Adds a Symbol to the Alphabet
     *
     * @param sym specific Symbol to add
     */
    public void addElementToAlphabet(Symbol sym) {
        if (!alphabet.contains(sym)) {
            alphabet.add(sym);
            status.fireStatusChangeEventInFuture();
        }
    }

    public File getFile() {
        return file;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Symbol getStartSymbol() {
        return this.startSymbol;
    }

    /**
     * Adds a Rule to the RuleList
     *
     * @param rule specific rule to add to the RuleList
     */
    public void addRuleToRules(Rule rule) {
        if (!rules.contains(rule)) {
            rules.add(rule);
            status.fireStatusChangeEventInFuture();
        }
    }

    /**
     * Returns a Rule that handles the given SymbolList as input.
     *
     * @param sset
     * @param terminalOnly Returns a rule that has only terminals as output if
     * this is true.
     * @return
     */
    public Rule getRuleFor(Symbol symb, boolean terminalOnly) {
        List<Rule> possibleRules = new ArrayList<Rule>();

        for (Rule rule : rules) {
            if (rule.getInput().equals(symb)) {
                if (!terminalOnly) {
                    possibleRules.add(rule);
                } else {
                    if (checkRuleForVariable(rule)) {
                        break;
                    } else {
                        possibleRules.add(rule);
                    }
                }
            }
        }

        if (possibleRules.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        return possibleRules.get(rand.nextInt(possibleRules.size()));
    }

    public static boolean checkRuleForVariable(Rule rule) {
        return (rule.getInput() instanceof Variable);
    }

    public void setStartSymbol(Symbol start) {
        this.startSymbol = start;
        this.status.fireStatusChangeEventInFuture();
    }

    public void removeRule(Rule rule) {
        this.rules.remove(rule);
        this.status.fireStatusChangeEventInFuture();
    }
}
