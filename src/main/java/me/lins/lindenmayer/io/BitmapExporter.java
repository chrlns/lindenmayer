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

import java.io.IOException;
import java.io.OutputStream;
import me.lins.lindenmayer.grammar.TreePlant;

/**
 * Exports a Plant to a Windows Bitmap.
 * @author chris
 */
public class BitmapExporter implements Exporter {

	public void export(TreePlant plant, OutputStream out) throws IOException {
	}

	public String getDescription() {
		return "Bitmap";
	}

	public String getFileExtension() {
		return ".bmp";
	}
}
