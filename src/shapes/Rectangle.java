package shapes;
import java.awt.Graphics2D;

/**
 * Class <code>Rectangle</code> represents a rectangular shape which
 * specifies a an area in a coordinate space that is defined by this
 * object's upper-left had corner as well as it's height and width.
 *
 * <code>Rectangle</code> objects define how to paint themselves
 * making them ideal for use with a <code>PaintCanvas</code> because
 * they store the paint color they were painted with and the brush type
 * they were drawn with.
 * 
 * @version 0.4.1 [8/24/15]
 * 
 * @author Michael Davis
 * 
 */

/*
 * Version Notes:
 * 		-- Rectangles and ellipses now share the same abstract class
 * 		allowing them to share a lot of the same code.
 */
public class Rectangle extends AbstractRectangle {
	
	/**
	 * Constructs a new <code>Rectangle</code> with given x and y location
	 * as well as width and height.
	 * @param x the x-coordinate of the upper-left corner
	 * @param y the y-coordinate of the upper-left corner
	 * @param width the width of the Rectangle
	 * @param height the height of the Rectangle
	 */
	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void paintShape(Graphics2D g) {
		g.setStroke(brushType);
		g.setColor(paintColor);
		if (isFilled) {
			g.fillRect(x, y, width, height);
		} else {
			g.drawRect(x, y, width, height);
		}
	}
	
	/**
	 * Returns a string representation of this rectangle with the xy
	 * coordinates, height, then width in a comma separated list
	 * enclosed in parentheses.
	 */
	public String toString() {
		return ("rect (" + x + "," +  y + "," + height + "," + width +")");
	}
	
	@Override
	public int hashCode() {
		return x + y + height + width + paintColor.hashCode() + brushType.hashCode();
	}

	@Override
	public boolean equals(CanvasEntity other) {
		if (other != null && other.isActive()) {
			return this.hashCode() == other.hashCode() && this.getClass().equals(other.getClass());
		}
		return false;
	}
}