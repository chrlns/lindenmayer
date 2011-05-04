/*
 *  Lindenmayer
 *  Copyright (C) 2007-2009 Christian Lins <christian.lins@fh-osnabrueck.de>
 *  Copyright (C) 2007,2008 Kai Ritterbusch <kai.ritterbusch@osnanet.de>
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

package lindenmayer.grammar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

import lindenmayer.Config;
import lindenmayer.gui.MainFrame;
import lindenmayer.util.Pair;

/**
 * The Plant class is the graphical component that actually
 * draws our generated tree/plan.
 * @author Christian Lins
 * @author Kai Ritterbusch
 */
public class Plant extends JComponent
{

  public static final int   DEFAULT_CANVAS_WIDTH  = 500;
  public static final int   DEFAULT_CANVAS_HEIGHT = 400;
  public static final float DEFAULT_HEADING_INT   = (float)(30 * Math.PI / 180); // 30 degree
  public static final float DEFAULT_LINE_LENGTH   = 55; 
  public static final float DEFAULT_SCALE_FACTOR  = 0.85f;
  public static final float DEFAULT_STROKE_WIDTH  = 6;
  
  private float            heading           = DEFAULT_HEADING_INT;
  private BufferedImage    image   
    = new BufferedImage(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
  private List<int[]>      linePoints        = new ArrayList<int[]>();
  private int              maxRecursionDepth = 1;
  private Map<Pair<Symbol, Depth>, Rule> ruleCache = new HashMap<Pair<Symbol, Depth>, Rule>();
  private SymbolSet        symbols;
  
  // The current zoom factor
  private double          zoom        = 1.0;
  
  public Plant(SymbolSet symbols)
  {
    this.symbols = symbols;
    
    setSize(new Dimension(image.getWidth(), image.getHeight()));
    setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    //paintImage((Graphics2D)image.getGraphics(), zoom);
    
    // Load options from config
    this.heading = Config.inst().get(Config.TURTLE_ANGLE, Float.MAX_VALUE);
    if(this.heading == Float.MAX_VALUE)
    {
      this.heading = DEFAULT_HEADING_INT;
    }
    else
    {
      // Switch from degree to radian
      this.heading = (float)(this.heading * Math.PI / 180);
    }
  }
  
  private void adaptSize(List<int[]> points)
  {
    int width  = 0;
    int height = 0;
    for(int[] p : points)
    {
      width = Math.max(p[0], width);
      width = Math.max(p[2], width);
      height = Math.max(p[1], height);
      height = Math.max(p[3], height);
    }

    width  = (int)Math.round(width * zoom);
    height = (int)Math.round(height * zoom);
    
    if(width > 0 && height > 0)
    {
      // Recreate the image
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
     // paintImage(image.createGraphics(), zoom);

      // Set new size
      setSize(width, height);
      setPreferredSize(getSize());
      repaint();
    }
  }
  
  /**
   * Tries to find a replacement rule for the given SymbolSet.
   * At first it looks into the cache if this rule was used before
   * (this is necessary for repainting while zooming!).
   * If no appropriate rule is found within the cache, this method
   * uses the previously loaded Grammar to get a rule which than
   * is returned and stored in the cache.
   * @param set
   * @param recursionDepth
   * @param terminalOnly
   * @return
   */
  private Rule getRuleFor(Symbol symbol, int recursionDepth, boolean terminalOnly)
  {
    Pair<Symbol, Depth> key = new Pair<Symbol, Depth>(symbol, new Depth(recursionDepth));
    Rule cachedRule = ruleCache.get(key);
    if(cachedRule == null)
    {
      Rule rule = MainFrame.getInstance().getGrammar().getRuleFor(symbol, terminalOnly);
      ruleCache.put(key, rule);
      return rule;
    }
    else
    {
      return cachedRule;
    }
  }
  
  /**
   * Draws the previous rendered image. We must render into a image,
   * otherwise scrolling would be terribly slow.
   */
  @Override
  public void paintComponent(Graphics g)
  {
    g.drawImage(image, 0, 0, null);
  }
  
  /**
   * This method contains the "growing" algorithm. It draws the plant
   * into a BufferedImage with the specified zoom factor.
   * This method is public for the exporter.
   * @param g
   * @param zoom
   */
  public void paintImage(Graphics2D g, double zoom, int depth)
  {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                       RenderingHints.VALUE_ANTIALIAS_ON);
    
    // Initial values
    float lineLength  = DEFAULT_LINE_LENGTH;
    float strokeWidth = DEFAULT_STROKE_WIDTH;
    
    g.scale(zoom, zoom); // Set zoom factor
    g.setColor(Color.decode("#11CC00"));
    g.setStroke(new BasicStroke(strokeWidth));
    
    TurtleState             initState = new TurtleState();
    LinkedList<TurtleState> queue     = new LinkedList<TurtleState>();
    LinkedList<TurtleState> stack     = new LinkedList<TurtleState>();
    
    initState.setColor(Color.decode("#004e00"));
//    initState.setSymbols(symbols);
    initState.setX((int)(image.getWidth() / 2 / zoom));
    initState.setY((int)(image.getHeight() / zoom));
    initState.setAngle(0);       // Heading, 0 means at right angle
    
    queue.addFirst(initState);
    
    int sizeofNextRecursion = 0;
    int currentRecSize      = 1;
    int recursionDepth      = 0;
    /*
    while(!queue.isEmpty() && currentRecSize > 0)
    {
      TurtleState state = queue.removeFirst();
      g.setColor(state.getColor());

      for(Symbol symb: state.getSymbols())
      {       
        if(symb.equals("f"))
        {
          int newPosX = Math.round(state.getX() + (float)Math.sin(state.getAngle()) * lineLength);
          int newPosY = Math.round(state.getY() - (float)Math.cos(state.getAngle()) * lineLength);
          
          // Store line points for later use in export methods
          int[] pnts = {(int)state.getX(), (int)state.getY(), newPosX, newPosY};
          linePoints.add(pnts);
          
          // Draw the line
          g.drawLine(pnts[0], pnts[1], pnts[2], pnts[3]);
          
          state.setX(newPosX);
          state.setY(newPosY);
        }
        else if(symb.equals("g"))
        {
          int newPosX = Math.round(state.getX() + (float)Math.sin(state.getAngle()) * lineLength);
          int newPosY = Math.round(state.getY() - (float)Math.cos(state.getAngle()) * lineLength);
          state.setX(newPosX);
          state.setY(newPosY);
        }        
        else if(symb.equals("+"))
        {
          state.rotateAngle(-this.heading);
        }
        else if(symb.equals("-"))
        {
          state.rotateAngle(this.heading);
        }
        else if(symb.equals("("))
        {
          // Push current TurtleState onto stack
          stack.push(state.clone());
        }
        else if(symb.equals(")"))
        {
          // Pop current TurtleState from stack
          state = stack.pop();
        }
        else
        {
          // A Variable was found and must be replaced by a rule
          TurtleState newState = state.clone();
          assert newState != null;
          
          Rule rule = getRuleFor(new SymbolSet(symb), recursionDepth, false);         
          if(rule == null)
          {
            System.err.println("No rule for " + symb);
            System.err.flush();
          }
          else
          {
            MainFrame.getInstance().getLogPanel().addNewRecDepth("Rekursion " + recursionDepth, symb + " ==> " + rule.getProduction());
            System.out.println("Rule substitution: " + symb + " ==> " + rule.getProduction());
            System.out.flush();            
            newState.setSymbols(rule.getProduction());
            newState.setColor(state.getColor().brighter());
            queue.add(newState);
            sizeofNextRecursion++;
          }
        }
      
      }
      
      if(--currentRecSize <= 0) // Current recursion layer has ended
      {
        recursionDepth++;
        
        // Set new sizes for stroke and branch
        strokeWidth = strokeWidth * DEFAULT_SCALE_FACTOR;
        g.setStroke(new BasicStroke(strokeWidth));
        lineLength *= DEFAULT_SCALE_FACTOR;
        
        // If wished depth not reached, continue
        if(recursionDepth < maxRecursionDepth)
        {
          currentRecSize = sizeofNextRecursion;
          // If sizeofNextRecursion = 0 no substitution occurred,
          // so the growth of the plant has ended
          if(sizeofNextRecursion == 0)
            MainFrame.getInstance().getToolBar().stopPlay();
        }
        sizeofNextRecursion = 0;
      }
    }*/

  }
  
  /**
   * Clears the rule cache that was stored for repainting the plant.
   * The cache must be cleared whenever we want to start a new "growth".
   */
  public void invalidateRuleCache()
  {
    this.ruleCache.clear();
  }
  
  public void repaintFull()
  {
   // paintImage((Graphics2D)image.getGraphics(), zoom);
  }
  
  /**
   * @param heading Radian value of heading (NOT degree!)
   */
  public void setHeading(float heading)
  {
    this.heading = heading;
  }

  /**
   * Divides the zoom factor by 0.77.
   */
  public void zoomIn()
  {
    zoom = zoom / 0.77;
    adaptSize(linePoints);
  }
  
  /**
   * Multiplies the zoom factor with 0.77.
   */
  public void zoomOut()
  {
    zoom = zoom * 0.77;
    adaptSize(linePoints);
  }

  public int getRecursionDepth()
  {
    return maxRecursionDepth;
  }

  /**
   * Sets the recursion depth to which the plant is grown.
   * @param maxRecursionDepth
   */
  public void setRecursionDepth(int maxRecursionDepth)
  {
    if(maxRecursionDepth < 1)
      return;
    
    this.maxRecursionDepth = maxRecursionDepth;
  }

  public List<int[]> getLinePoints()
  {
    return linePoints;
  }

}
