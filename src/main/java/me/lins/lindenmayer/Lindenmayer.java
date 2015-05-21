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
package me.lins.lindenmayer;

import javax.swing.UIManager;
import me.lins.lindenmayer.gui.MainFrame;

/**
 * Start class of the Lindenmayer program.
 *
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Lindenmayer {

    public static final String VERSION = "Lindenmayer/0.4.0";
    public static final Status STATUS = new Status();

    /**
     * Entrypoint method.
     *
     * @param args
     */
    public static void main(String[] args) {
        boolean usePlaf = false;

        for (int n = 0; n < args.length; n++) {
            if (args[n].equals("-plaf")) {
                usePlaf = true;
            } else if (args[n].equals("-version")) {
                System.out.println(VERSION);
                return;
            }
        }

        try {
            if (!usePlaf) {
                if (UIManager.getCrossPlatformLookAndFeelClassName().equals(
                        UIManager.getSystemLookAndFeelClassName())) {
					// No native LaF available, e.g. on KDE.
                    // So, try to load Nimbus LaF...
                    UIManager.setLookAndFeel(
                            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } else {
                    System.err.println("Could not load Nimbus LaF!");
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
            }
        } catch (Exception ex) {
            System.err.println("Could not load Java LaF!");
            System.err.println(ex.getLocalizedMessage());
        }

        MainFrame.getInstance().setVisible(true);
    }
}
