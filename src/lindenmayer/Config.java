/*
 *  Lindenmayer
 *  Copyright (C) 2007-2009 Christian Lins <cli@openoffice.org>
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

package lindenmayer;

import java.awt.Color;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Manages the configuration of the program.
 * @author Christian Lins
 */
public class Config
{

  // Constants
  // Grammar constants

  /** Default angle of the drawing turtle */
  public static final String TURTLE_ANGLE        = "TurtleAngle";
  
  // Program constants
  public static final String LAST_FILE_0         = "LastFile0";
  public static final String LAST_FILE_1         = "LastFile1";
  public static final String LAST_FILE_2         = "LastFile2";
  public static final String LAST_FILE_3         = "LastFile3";
  public static final String LAST_FILE_4         = "LastFile4";
  public static final String WINDOW_HEIGHT       = "WindowHeight";
  public static final String WINDOW_WIDTH        = "WindowWidth";
  public static final String WINDOW_STATE        = "WindowState";
  
  public static final String CONFIG_NODE = "/de/fhos/lindenmayer";
  
  private static Config instance = null; 

  /**
   * @return Singleton-Instance of this class.
   */
  public static synchronized Config inst()
  {
    if(instance == null)
    {
      instance = new Config();
      try
      {
        instance.load();
      }
      catch(IOException ex)
      {
        System.err.println(ex.getLocalizedMessage());
      }
    }
    
    return instance;
  }
   
  private Preferences prop = null;
  
  /**
   * Adds a file name to the list of recent file names.
   * @param name
   */
  public void addToRecentFileNames(String name)
  {
    // Check if name is already in the list
    for(int n = 0; n < 5; n++)
    {
      if(get("LastFile" + n, "").equals(name))
      {
        // Move names from 0 to n ...
        for(int m = n; m > 0; m--)
          set("LastFile" + m, get("LastFile" + (m-1), ""));
        
        // ... and set our new number one
        set(LAST_FILE_0, name);
        return;
      }
    }
    
    // Otherwise move old names
    for(int n = 5; n > 0; n--)
      set("LastFile" + n, get("LastFile" + (n-1), ""));
    set(LAST_FILE_0, name);
  }
 
  /**
   * Load the properties from the Java registry.
   * @throws IOException
   */
  public void load()
    throws IOException
  {
    prop = Preferences.userRoot().node(CONFIG_NODE);
  }
  
  /**
   * Returns the config value for the given key. If the key does not
   * exist the default value @see{def} is returned.
   * @param key
   * @param def
   * @return
   */
  public String get(String key, String def)
  {
    return prop.get(key, def);
  }
  
  /**
   * Returns the config value for the given key. If the key does not
   * exist the default value @see{def} is returned.
   * @param key
   * @param def
   * @return
   */
  public int get(String key, int def)
  {
    return prop.getInt(key, def);
  }
  
  /**
   * Returns the configuration value for the given key or returns the
   * default if the key was not set before.
   * @param key
   * @param def
   * @return
   */
  public float get(String key, float def)
  {
    return prop.getFloat(key, def);
  }
  
  /**
   * Returns the config value for the given key. If the key does not
   * exist the default value @see{def} is returned.
   * @param key
   * @param def
   * @return
   */
  public Color get(String key, Color def)
  {
    int rgb = get(key, def.getRGB());
    return new Color(rgb);
  }
  
  /**
   * Returns the config value for the given key. If the key does not
   * exist the default value @see{def} is returned.
   * @param key
   * @param def
   * @return
   */
  public boolean get(String key, boolean def)
  {
    String result = get(key, Boolean.toString(def));
    return Boolean.parseBoolean(result);
  }
  
  /**
   * Set a config value. If there is already a value for the
   * given key, the old value is replaced by the new one.
   * @param key
   * @param value
   */
  public void set(String key, String value)
  {
    prop.put(key, value);
  }
  
  /**
   * Set a config value. If there is already a value for the
   * given key, the old value is replaced by the new one.
   * @param key
   * @param value
   */
  public void set(String key, int value)
  {
    set(key, Integer.toString(value));
  }
  
  public void set(String key, float value)
  {
    set(key, Float.toString(value));
  }
  
  /**
   * Set a config value. If there is already a value for the
   * given key, the old value is replaced by the new one.
   * @param key
   * @param value
   */
  public void set(String key, Color value)
  {
    set(key, value.getRGB());
  }
  
  /**
   * Set a config value. If there is already a value for the
   * given key, the old value is replaced by the new one.
   * @param key
   * @param value
   */
  public void set(String key, boolean value)
  {
    set(key, Boolean.toString(value));
  }

}