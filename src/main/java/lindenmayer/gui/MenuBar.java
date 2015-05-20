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
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import lindenmayer.i18n.Lang;
import lindenmayer.io.Resource;

/**
 * Program's menu bar.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
class MenuBar extends JMenuBar {

	// File-Menu
	private JMenuItem mnuItemNew = new JMenuItem(Lang.get(16)); // "New grammar"
	private JMenuItem mnuItemOpen = new JMenuItem(Lang.get(1)); // "Load grammar..."
	private JMenuItem mnuItemSave = new JMenuItem(Lang.get(2)); // "Save grammar"
	private JMenuItem mnuItemSaveAs = new JMenuItem(Lang.get(14));  // "Save grammar as..."
	private JMenuItem mnuItemExport = new JMenuItem("Baum exportieren...");
	private JMenuItem mnuItemConfig = new JMenuItem(Lang.get(4));
	private JMenuItem mnuItemExit = new JMenuItem(Lang.get(17)); // "Exit"
	private JMenuItem mnuItemInfo;
	private MainFrame parent;

	public MenuBar(MainFrame mainFrame) {
		this.parent = mainFrame;

		JMenu mnuFile = new JMenu(Lang.get(3)); // File menu
		JMenu mnuInfo = new JMenu("?");
		add(mnuFile);
		add(mnuInfo);

		// Load icons
		mnuItemNew.setIcon(
				Resource.getImage("gfx/document-new.png"));
		mnuItemOpen.setIcon(
				Resource.getImage("gfx/document-open.png"));
		mnuItemSave.setIcon(
				Resource.getImage("gfx/document-save.png"));
		mnuItemSaveAs.setIcon(
				Resource.getImage("gfx/document-save-as.png"));
		mnuItemExit.setIcon(
				Resource.getImage("gfx/system-log-out.png"));

		mnuFile.add(mnuItemNew);
		mnuFile.add(mnuItemOpen);
		mnuFile.addSeparator();
		mnuFile.add(mnuItemSave);
		mnuFile.add(mnuItemSaveAs);
		mnuFile.addSeparator();
		//mnuFile.add(mnuItemExport);
		mnuFile.add(mnuItemConfig);
		mnuFile.addSeparator();
		mnuFile.add(mnuItemExit);

		mnuItemInfo = new JMenuItem(Lang.get(18)); // "About"
		mnuInfo.add(mnuItemInfo);

		// Disable exporter until we can load the exporter classes
		mnuItemExport.setEnabled(false);

		// Add action listener
		mnuItemNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				parent.newGrammar();
			}
		});

		mnuItemOpen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				parent.loadGrammar();
			}
		});

		mnuItemSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				MainFrame.getInstance().saveGrammar();
			}
		});

		mnuItemSaveAs.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				MainFrame.getInstance().saveGrammarAs();
			}
		});

		mnuItemExport.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				try {
					JFileChooser fc = new JFileChooser();
					if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
						//LatexExporter.exportToFile(fc.getSelectedFile(), parent.getCanvasPanel().getPlant());
					}
					//PostScriptExporter pse = new PostScriptExporter();
					//pse.export(parent.getCanvasPanel().getPlant(), new FileOutputStream("test.ps"));
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
			}
		});

		mnuItemConfig.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				new ConfigDialog().setVisible(true);
			}
		});

		mnuItemExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		mnuItemInfo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				new InfoDialog();
			}
		});
	}
}
