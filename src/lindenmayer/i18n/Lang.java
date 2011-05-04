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

package lindenmayer.i18n;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lindenmayer.io.Resource;

/**
 * Retrieve i18n language strings via this class.
 * @author Christian Lins
 */
public class Lang 
{

  private static Map<String, String> strings = new ConcurrentHashMap<String, String>();
  
  static
  {
    try
    {
      // Load english strings as fall back
      loadStrings("lindenmayer/resource/text/strings.en");

      // Load locale strings
      String lang = Locale.getDefault().getCountry().toLowerCase();
      loadStrings("lindenmayer/resource/text/strings." + lang);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  private static void loadStrings(String resourceName)
    throws IOException
  {
    BufferedReader in = new BufferedReader(new InputStreamReader(
      Resource.getAsStream(resourceName)));
    
    String line = in.readLine();
    while(line != null)
    {
      String[] toks = line.split("=", 2);
      strings.put(toks[0].trim(), toks[1].trim());
      line = in.readLine();
    }
  }
  
  public static String get(String id)
  {
    return strings.get(id);
  }
  
}
