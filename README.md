[![Build Status](https://travis-ci.org/cli/lindenmayer.svg?branch=master)](https://travis-ci.org/cli/lindenmayer)

Lindenmayer - L-system grammar viewer and editor
================================================================================

Lindenmayer is a small Java application that allows one to edit and view context
free L-systems grammars that produce plant-looking turtle graphics.

To learn more about L(indenmayer)-systems please refere to Wikipedia [1].

[1] http://en.wikipedia.org/wiki/L-system


== Installation ==

Users of Debian based distributions can use the packages provided by the authors
PPA at launchpad, see [2].

Download Release 0.4.0:
 * Source https://www.dropbox.com/s/a8ii8cpxjvw1fxg/lindenmayer-0.4.0.tar.gz
 * Binary https://www.dropbox.com/s/a8ii8cpxjvw1fxg/lindenmayer-0.4.0.jar

[2] https://launchpad.net/~cli/+archive/ppa


== Usage ==

Debian-like:
Invoke "lindenmayer" or use the entry from the application menu.

Other:
Invoke "java -cp lindenmayer.jar:jdom1.jar". Please note that JDom is not 
distributed with Lindenmayer's source code. On Windows additionally replace ":"
 with ";".

After startup the application shows the main window which contains a toolbar and
three tabs (Tree graphics, Grammar, Log).

The sample-tree.xml file shipped with this source distribution is a good start. 
Load this file with "File" -> "Load grammar". Then press the "Play" button in 
the toolbar. You should see a small plant "growing" from the bottom of the 
window on the "Tree graphic" tab. Press the "Pause" button in the toolbar to 
stop the growth as it will otherwise run indefinitely.

On the Grammar tab you can view and edit the symbols of the grammar and the 
replacement rules.

The Log tab provides information about which rule was used to replace a specific
symbol.


== Development ==

Lindenmayer was initially developed by Christian Lins and Kai Ritterbusch to fit
the needs of Prof. Jürgen Biermann of the University of Applied Sciences 
Osnabrück.
See AUTHORS file for more information and other contributors.

If you discover a bug, you can write a mail to Christian or file the bug 
directly at the bugtracker [3]. Same applies to suggestions of any kind.

[3] https://bitbucket.org/cli/lindenmayer/issues
