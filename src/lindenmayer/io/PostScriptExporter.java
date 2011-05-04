/*
 *  Lindenmayer
 *  Copyright (C) 2009 Christian Lins <cli@openoffice.org>
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
import lindenmayer.grammar.Plant;

/**
 * Exports a drawn Plant to a PostScript document. This class uses the
 * PostScriptGraphics class of the
 * <a href="http://www.coredesigner.org/">Coredesigner Project</a>.
 * @author Christian Lins
 */
public class PostScriptExporter implements Exporter
{

  public void export(Plant plant, OutputStream out) throws IOException 
  {
    /*PostScriptGraphics psg = new PostScriptGraphics();
    //plant.paintImage(psg, 1.0);
    
    String rawps = psg.createPostScript();
    out.write(rawps.getBytes());
    out.flush();
    out.close();*/
  }

  public String getDescription() 
  {
    return "PostScript";
  }

  public String getFileExtension() 
  {
    return ".ps";
  }

}
