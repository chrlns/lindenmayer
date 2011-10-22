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
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lindenmayer.Config;
import lindenmayer.io.Resource;

/**
 * Retrieve i18n language strings via this class.
 * @author Christian Lins
 */
public class Lang {

	private static Map<Integer, String> strings = new ConcurrentHashMap<Integer, String>();

	static {
		try {
			// Load english strings as fall back
			loadStrings("lindenmayer/resource/text/strings.en");

			String lang = Config.inst().get(Config.LANGUAGE, "");
			if("".equals(lang)) {
				lang = Locale.getDefault().getCountry().toLowerCase();
			}

			if(!("en".equals(lang))) {
				// Load locale strings
				loadStrings("lindenmayer/resource/text/strings." + lang);
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}

	private static void loadStrings(String resourceName)
			throws IOException {
		BufferedReader in = null;
		try {
			InputStreamReader insr = new InputStreamReader(
				Resource.getAsStream(resourceName), Charset.forName("UTF-8"));
			in = new BufferedReader(insr);

			String line = in.readLine();
			while (line != null) {
				String[] toks = line.split("=", 2);
				strings.put(Integer.parseInt(toks[0].trim()), toks[1].trim());
				line = in.readLine();
			}
		} catch(IOException ex) {
			throw ex;
		} finally {
			if(in != null) {
				in.close();
			}
		}
	}

	public static String get(int id) {
		return strings.get(id);
	}

	/**
	 * Returns the localized string identified by the given id after
	 * the first occurrence of "%s" is replaced by the replacement.
	 * @param id
	 * @param replacement
	 * @return
	 */
	public static String get(int id, String replacement) {
		String str = get(id);
		str = str.replaceFirst("%s", replacement);
		return str;
	}

	public static String get(int id, String r0, String r1) {
		String str = get(id, r0);
		str = str.replace("%s", r1);
		return str;
	}
}
