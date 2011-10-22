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

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import lindenmayer.i18n.Lang;

/**
 * Filechooser that let the users select XML files.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
@SuppressWarnings("serial")
class XMLFileChooser extends JFileChooser {

	public XMLFileChooser() {
		super(new File("."));

		addChoosableFileFilter(new FileFilter() {

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return f.getName().toLowerCase().endsWith(".xml");
			}

			public String getDescription() {
				return Lang.get(18); // "XML grammar files"
			}
		});
	}
}
