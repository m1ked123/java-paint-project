package shapes;
import java.awt.Graphics2D;

/**
 * Class <code>Ellipse</code> represents an elliptical shape in a
 * coordinate system who is defined by the dimensions of its bounding
 * rectangle.
 * 
 * <code>Ellipse</code> objects are defined by a bounding rectangle
 * whose upper-left hand corner defines its location in the
 * coordinate space. Essentially the resulting ellipse is the largest
 * that can fit into the objects bounding rectangle
 * 
 * @version 0.4.1 [8/24/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- Ellipses and Rectangles now share the same abstract class
 * 		which allows for a lot of the code to be shared as well.
 */
public class Ellipse extends AbstractRectangle {
	
	/**
	 * Constructs a new <code>Ellipse</code> whose bounding rectangle
	 * is defined by the given xy coordinates and the given height and
	 * width
	 * @param x the x coordinate of the left corner of this object's 
	 * bounding rectangle
	 * @param y the y coordinate of the left corner of this object's
	 * bounding rectangle
	 * @param width the height of the bounding rectangle
	 * @param height the width of the bounding rectangle
	 */
	public Ellipse(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void paintShape(Graphics2D g) {
		g.setColor(paintColor);
		g.setStroke(brushType);
		if (isFilled) {
			g.fillOval(x, y, width, height);
		} else {
			g.drawOval(x, y, width, height);
		}
	}
	
	@Override
	public boolean equals(CanvasEntity other) {
		if (other != null && other.isActive()) {
			return this.hashCode() == other.hashCode() && this.getClass().equals(other.getClass());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return x + y + height + width + paintColor.hashCode() + brushType.hashCode();
	}
	
	/**
	 * Returns a String representation of this Ellipse with the xy
	 * coordinates, height, and width of the bounding rectangle in a
	 * comma separated list enclosed in parentheses.
	 */
	public String toString() {
		return ("ellipse (" + x + "," +  y + "," + height + "," + width +")");
	}
}