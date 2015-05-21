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

import java.awt.Dimension;
import javax.swing.JLabel;
import me.lins.lindenmayer.i18n.Lang;

/**
 * The StatusBar shows information at the bottom of the MainFrame.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
class StatusBar extends JLabel {

	/** Default constructor */
	public StatusBar() {
		super.setPreferredSize(new Dimension(100, 16));
		setMessage(Lang.get(15)); // "Ready"
	}

	/** Constructor */
	public StatusBar(String str) {
		this();
		setMessage(str);
	}

	/** Sets text content */
	public void setMessage(String message) {
		setText("  " + message);
	}
}
