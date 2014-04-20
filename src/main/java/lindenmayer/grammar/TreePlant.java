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
package lindenmayer.grammar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Stack;
import javax.swing.JComponent;
import lindenmayer.Config;

/**
 * Graphical component showing a tree.
 * @author Christian Lins
 */
@SuppressWarnings("serial")
public class TreePlant extends JComponent {

	public static final float DEFAULT_HEADING = 30.0f; // 30°

	private float heading;
	private Node  root;

	public TreePlant(Node root) {
		this.heading = Config.inst().get(Config.TURTLE_ANGLE, DEFAULT_HEADING);
		this.heading = (float)(this.heading * Math.PI / 180);
		this.root = root;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 400);
	}

	@Override
	public void paintComponent(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;

		g.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		Stack<Node> nodeStack = new Stack<Node>();
		Stack<TurtleState> turtleStack = new Stack<TurtleState>();
		if(root != null) {
			nodeStack.push(root);
		} // if root is null, only the background is painted

		TurtleState turtle = new TurtleState();
		turtle.setColor(Color.decode("#004e00"));
		turtle.setX(getWidth() / 2); // Bottom center
		turtle.setY(getHeight());
		turtle.setAngle(0);       // Heading, 0 means at right angle

		// Main drawing loop; paints the whole tree starting with root to
		// the leaves of the tree
		while (!nodeStack.empty()) {
			Node node = nodeStack.pop();
			List<Node> children = node.getChildren();
			for (int n = children.size() - 1; n >= 0; n--) {
				nodeStack.push(children.get(n));
			}

			String symb = node.getSymbol().getText();

			g.setStroke(new BasicStroke(
					turtle.getStrokeWidth(), 
					BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
			g.setColor(turtle.getColor());

			if (symb.equals("f")) {
				// Calculate end position of the line
				int newPosX = Math.round(turtle.getX()
						+ (float) Math.sin(turtle.getAngle()) * turtle.getLineLength());
				int newPosY = Math.round(turtle.getY()
						- (float) Math.cos(turtle.getAngle()) * turtle.getLineLength());

				// Store line points for later use in export methods
				int[] pnts = {(int) turtle.getX(), (int) turtle.getY(), newPosX, newPosY};
				//linePoints.add(pnts);

				// Draw the line
				g.drawLine(pnts[0], pnts[1], pnts[2], pnts[3]);

				turtle.setX(newPosX);
				turtle.setY(newPosY);
			} else if (symb.equals("g")) {
				int newPosX = Math.round(turtle.getX() + (float) Math.sin(turtle.getAngle()) * 25);
				int newPosY = Math.round(turtle.getY() - (float) Math.cos(turtle.getAngle()) * 25);
				turtle.setX(newPosX);
				turtle.setY(newPosY);
			} else if (symb.equals("+")) {
				turtle.rotateAngle(-this.heading);
			} else if (symb.equals("-")) {
				turtle.rotateAngle(+this.heading);
			} else if (symb.equals("(")) {
				// Push current TurtleState onto stack
				turtleStack.push((TurtleState)turtle.clone());

				// Go down the recursion
				turtle.scaleDown();
			} else if (symb.equals(")")) {
				// Pop current TurtleState from stack
				turtle = turtleStack.pop();
			}
		}
	}
}
