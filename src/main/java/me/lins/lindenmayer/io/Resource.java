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

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.swing.ImageIcon;

/**
 * Provides useful static methods for handling resources.
 *
 * @author Christian Lins
 */
public class Resource {

    /**
     * Loads an image from a local resource.
     *
     * @param name
     */
    public static ImageIcon getImage(String name) {
        URL url = getAsURL(name);

        if (url == null) {
            Image img = Toolkit.getDefaultToolkit().createImage(name);
            return new ImageIcon(img);
        }

        return new ImageIcon(url);
    }

    /**
     * Loads a resource and returns an URL on it.
     */
    public static URL getAsURL(String name) {
        return Resource.class.getClassLoader().getResource(name);
    }

    /**
     * Loads a resource and returns an InputStream on it.
     */
    public static InputStream getAsStream(String name) {
        try {
            URL url = getAsURL(name);
            return url.openStream();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Reads a text file into a complete String.
     */
    public static String getAsString(String name, boolean withNewline) {
        BufferedReader in = null;
        try {
            InputStreamReader insr
                    = new InputStreamReader(getAsStream(name), Charset.forName("UTF-8"));
            in = new BufferedReader(insr);
            StringBuilder buf = new StringBuilder();

            String line = in.readLine();
            while (line != null) {
                buf.append(line);
                if (withNewline) {
                    buf.append('\n');
                }

                line = in.readLine();
            }

            return buf.toString();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    System.err.println("Error closing stream: " + ex.getLocalizedMessage());
                }
            }
        }
    }
}
