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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * GUI utilities.
 * @author Christian Lins
 */
class Utils {

	static int[] getCenteredLocation(Component comp) {
		// Size of screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Calculate frame position
		int top = (screenSize.height - comp.getHeight()) / 2;
		int left = (screenSize.width - comp.getWidth()) / 2;

		return new int[]{left, top};
	}

	public static void centerOnFrame(JFrame frame, Component comp) {
		Point p = frame.getLocation();
		int left = p.x + (frame.getWidth() - comp.getWidth()) / 2;
		int top = p.y + (frame.getHeight() - comp.getHeight()) / 2;
		comp.setLocation(left, top);
	}

	public static void centerOnScreen(Component comp) {
		int[] pos = getCenteredLocation(comp);
		comp.setLocation(pos[0], pos[1]);
	}
}
