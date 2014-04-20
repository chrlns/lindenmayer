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
package lindenmayer.io;

import java.io.IOException;
import java.io.OutputStream;
import lindenmayer.grammar.TreePlant;

/**
 * An interface for all Plant exporters.
 * @author Christian Lins
 */
public interface Exporter {

	/**
	 * Exports the plant to the output stream. Implmenting classes may throw
	 * an IOException.
	 * @param plant
	 * @param out
	 */
	void export(TreePlant plant, OutputStream out) throws IOException;

	/**
	 * @return A short description of the output file format.
	 */
	String getDescription();

	/**
	 * If this exporter is able to export to a file this method returns the
	 * file extension, e.g. ".bmp"
	 */
	String getFileExtension();
}
