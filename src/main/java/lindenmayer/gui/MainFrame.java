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

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import lindenmayer.Lindenmayer;
import lindenmayer.grammar.Grammar;
import lindenmayer.i18n.Lang;
import org.jdom.DataConversionException;

/**
 * The main application window.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static volatile MainFrame instance = null;

	/** @return The current instance of the MainFrame */
	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	private StatusBar statusBar = new StatusBar();
	private ToolBar toolBar = new ToolBar();
	private CanvasPanel canvasPanel = new CanvasPanel();
	private GrammarPanel grammarPanel = new GrammarPanel();
	private LogPanel logPanel = new LogPanel();

	private MainFrame() {
		setTitle(Lang.get(31));
		setSize(900, 700);

		setJMenuBar(new MenuBar(this));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// Create TabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(Lang.get(33), new JScrollPane(canvasPanel));  // "Tree graphic"
		tabbedPane.addTab(Lang.get(20), grammarPanel); // "Grammar"
		tabbedPane.addTab(Lang.get(21), logPanel);     // "Log"
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		// Add view elements as listener to status changes
		Lindenmayer.STATUS.addStatusChangeListener(canvasPanel);
		Lindenmayer.STATUS.addStatusChangeListener(grammarPanel);
		Lindenmayer.STATUS.addStatusChangeListener(logPanel);
		Lindenmayer.STATUS.addStatusChangeListener(toolBar);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Utils.centerOnScreen(this);
	}

	public ToolBar getToolbar() {
		return this.toolBar;
	}

	public void loadGrammar() {
		try {
			XMLFileChooser chooser = new XMLFileChooser();

			if (chooser.showOpenDialog(MainFrame.getInstance())
					== JFileChooser.APPROVE_OPTION) {
				loadGrammarFromFile(chooser.getSelectedFile());
				this.statusBar.setText(Lang.get(38, chooser.getSelectedFile().toString()));
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			String[] msg = {
				Lang.get(37),
				ex.getLocalizedMessage()
			};
			JOptionPane.showMessageDialog(this, msg, "Lindenmayer", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void newGrammar() {
		Lindenmayer.STATUS.reset();
	}

	public void saveGrammar() {
		Grammar grammar = Lindenmayer.STATUS.getGrammar();
		if (grammar.getFile() == null) {
			// Save to new file
			saveGrammarAs();
		} else {
			// Save to already known file
			grammar.saveToXML(grammar.getFile());
		}
		this.statusBar.setText(Lang.get(39, grammar.getFile().toString()));
	}

	public void saveGrammarAs() {
		Grammar grammar = Lindenmayer.STATUS.getGrammar();

		XMLFileChooser chooser = new XMLFileChooser();
		if (chooser.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
			grammar.saveToXML(chooser.getSelectedFile());
		}
	}

	public void loadGrammarFromFile(File file)
			throws DataConversionException {
		Grammar grammar = new Grammar(Lindenmayer.STATUS, file);
		grammar.loadFromXML();
		Lindenmayer.STATUS.setGrammar(grammar);
	}
}
