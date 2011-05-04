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
import java.util.List;
import java.util.Stack;
import javax.swing.JComponent;

/**
 * Graphical component showing a Tree.
 * @author Christian Lins
 */
public class TreePlant extends JComponent
{

  public static final float DEFAULT_HEADING_INT   = (float)(30 * Math.PI / 180); // 30
  
  private Node root;
  
  public TreePlant(Node root)
  {
    this.root = root;
  }
  
  @Override
  public Dimension getPreferredSize()
  {
    return new Dimension(500, 400);
  }
  
  @Override
  public void paintComponent(Graphics g1)
  {
    Graphics2D g = (Graphics2D)g1;
    
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, 500, 400);

    Stack<Node>         nodeStack   = new Stack<Node>();
    Stack<TurtleState>  turtleStack = new Stack<TurtleState>();
    nodeStack.push(root);
    
    TurtleState turtle = new TurtleState();
    turtle.setColor(Color.decode("#004e00"));
    turtle.setX((int) (500 / 2));
    turtle.setY((int) (400));
    turtle.setAngle(0);       // Heading, 0 means at right angle

    while(!nodeStack.empty())
    {
      Node node = nodeStack.pop();
      List<Node> children = node.getChildren();
      for(int n = children.size() - 1; n >= 0; n--)
      {
        nodeStack.push(children.get(n));
      }
      
      Symbol symb = node.getSymbol();

      g.setStroke(new BasicStroke(turtle.getStrokeWidth()));
      g.setColor(turtle.getColor());

      System.out.println(symb);
      
      if (symb.equals("f"))
      {
        // Calculate end position of the line
        int newPosX = Math.round(turtle.getX() + 
          (float) Math.sin(turtle.getAngle()) * turtle.getLineLength());
        int newPosY = Math.round(turtle.getY() - 
          (float) Math.cos(turtle.getAngle()) * turtle.getLineLength());

        // Store line points for later use in export methods
        int[] pnts = {(int) turtle.getX(), (int) turtle.getY(), newPosX, newPosY};
        //linePoints.add(pnts);

        // Draw the line
        g.drawLine(pnts[0], pnts[1], pnts[2], pnts[3]);

        turtle.setX(newPosX);
        turtle.setY(newPosY);
      }
      else if (symb.equals("g"))
      {
        int newPosX = Math.round(turtle.getX() + (float) Math.sin(turtle.getAngle()) * 25);
        int newPosY = Math.round(turtle.getY() - (float) Math.cos(turtle.getAngle()) * 25);
        turtle.setX(newPosX);
        turtle.setY(newPosY);
      }
      else if (symb.equals("+"))
      {
        turtle.rotateAngle(-DEFAULT_HEADING_INT);
      }
      else if (symb.equals("-"))
      {
        turtle.rotateAngle(+DEFAULT_HEADING_INT);
      }
      else if (symb.equals("("))
      {
        // Push current TurtleState onto stack
        turtleStack.push(turtle.clone());

        // Go down the recursion
        turtle.scaleDown();
      }
      else if (symb.equals(")"))
      {
        // Pop current TurtleState from stack
        turtle = turtleStack.pop();
      }
    }
  }
  
}
