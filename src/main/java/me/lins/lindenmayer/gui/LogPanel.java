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
package me.lins.lindenmayer.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import me.lins.lindenmayer.Status;
import me.lins.lindenmayer.StatusChangeListener;
import me.lins.lindenmayer.grammar.Node;
import me.lins.lindenmayer.grammar.Rule;
import me.lins.lindenmayer.i18n.Lang;

@SuppressWarnings("serial")
class LogPanel extends JPanel implements StatusChangeListener {

	public static final String LINE = "---------------------------------------\n";
	
	private JTextArea log = new JTextArea(); // Description of the substitutions

	public LogPanel() {
		setLayout(new GridLayout(0, 1));

		JPanel subPanel = new JPanel(new BorderLayout());
		subPanel.add(new JLabel(Lang.get(30)), BorderLayout.NORTH);
		subPanel.add(new JScrollPane(log), BorderLayout.CENTER);

		add(subPanel);
	}

	private void reset() {
		log.setText("");
	}

	public void statusChanged(Status status) {
		try {
			reset();

			if(status.currentTree() != null) {
				StringBuilder logBuf = new StringBuilder();
				
				// Walk trough the current tree and log its creation
				List<Node> walkList = new ArrayList<Node>();
				Node root = status.currentTree();
				int rootDepth = root.getDepth();
				walkList.add(root);
				
				while(!walkList.isEmpty()) {
					Node node = walkList.remove(0);
					for(Node child : node.getChildren()) {
						walkList.add(child);
					}

					Rule rule = node.getRule();
					int depth = node.getDepth();
		
					logBuf.append(Lang.get(46)); // "Recursion"
					logBuf.append(' ');
					logBuf.append(rootDepth - depth);
					logBuf.append(": ");
		
					String str;
					if(rule != null) {
						str = Lang.get(40, node.getSymbol().getText(), rule.toString());
					} else {
						str = Lang.get(45, node.getSymbol().getText()); 
					}
					
					logBuf.append(str);
					logBuf.append('\n');
				}

				this.log.setText(logBuf.toString());
			}
		} catch(OutOfMemoryError err) {
			System.gc();
			reset();
			System.err.println("OutOfMemoryError in LogPanel.statusChanged()");
			this.log.setText(Lang.get(44));
			MainFrame.getInstance().getToolbar().stopPlay();
		}
	}

	public void statusReset(Status status) {
	}
}
