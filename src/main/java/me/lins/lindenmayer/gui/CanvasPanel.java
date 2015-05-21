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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import me.lins.lindenmayer.Status;
import me.lins.lindenmayer.StatusChangeListener;
import me.lins.lindenmayer.grammar.TreePlant;

/**
 * The CanvasPanel is on the left side of the MainFrame and contains
 * the Plant that is painted.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
class CanvasPanel extends JPanel implements StatusChangeListener {

	private JScrollPane scrollPane = null;

	public CanvasPanel() {
		setLayout(new BorderLayout());
	}

	private void setTreePlant(TreePlant plant) {
		if (scrollPane != null) {
			remove(scrollPane);
		}

		scrollPane = new JScrollPane(plant);
		add(scrollPane, BorderLayout.CENTER);

		// TODO: only a workaround
		scrollPane.getHorizontalScrollBar().setValue(getWidth() / 2);
		//scrollPane.getVerticalScrollBar().setValue(plant.getHeight());
		validate();
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void statusChanged(Status status) {
		// Redraw with top of the tree stack
		setTreePlant(new TreePlant(status.currentTree()));
	}

	public void statusReset(Status status) {
		// Redraw with top of the tree stack
		setTreePlant(new TreePlant(status.currentTree()));
	}
}
