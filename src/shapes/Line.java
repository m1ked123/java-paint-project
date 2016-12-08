package shapes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Class <code>Line</code> represents a line that is drawn on a
 * <code>PaintCanvas</code>. It is represented by lists of xy coordinates
 * that make it up, the color of the paint that was used to draw it,
 * and the type of brush that was used to draw it. Lines determine how
 * to draw themselves on the canvas and are thusly considered
 * canvas entities
 * @version 0.1.3 [7/15/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- added equals and hashCode functions
 */
public class Line implements CanvasEntity {
	// The default capacity for the internal array of points
	private static final int DEFAULT_CAPACITY = 100;
	
	// the color of this particular line
	private Color lineColor;
	
	// the type of brush used to paint this line
	private Stroke brushType;
	
	// the set of xy coordinate points that compose this line
	private int[] xCoordinates, yCoordinates;
	
	// the amount of coordinates in the internal array
	private int size;
	
	// the current capacity of the internal array
	private int capacity;
	
	// whether or not this entity is active
	private boolean active;
	
	/**
	 * Constructs a new <code>Line</code>. It does not associate a paint
	 * color or a brush type to the line and does not add any coordinates
	 * to the lines internal lists.
	 */
	public Line() {
		this.lineColor = null;
		this.brushType = null;
		xCoordinates = new int[DEFAULT_CAPACITY];
		yCoordinates = new int[DEFAULT_CAPACITY];
		capacity = DEFAULT_CAPACITY;
		active = true;
	}
	
	/**
	 * Adds the given x and y coordinates to their respective coordinate
	 * lists. Both of these coordinates usually correspond to the 
	 * location of the brush as it is drawing on the panel.
	 * @param x the x-coordinate of the line at this instance
	 * @param y the y-coordinate of the line at this instance
	 */
	public void add(int x, int y) {
		ensureCapacity();
		xCoordinates[size] = x;
		yCoordinates[size] = y;
		size++;
	}
	
	/*
	 * Ensures the capacity of the lists that keep track of the line's
	 * x and y coordinates by doubling the size of the list whenever 
	 * it's size is close to its current capacity.
	 */
	private void ensureCapacity() {
		if (size == capacity / 2) {
			capacity *= 2;
			int[] tempX = new int[capacity];
			int[] tempY = new int[capacity];
			for (int i = 0; i < size; i++) {
				tempX[i] = xCoordinates[i];
				tempY[i] = yCoordinates[i];
			}
			xCoordinates = tempX;
			yCoordinates = tempY;
		}
	}
		
	/**
	 * Sets the color of this particular line to the given color
	 * @param color the color of the paint that is used to draw this
	 * line
	 */
	public void setColor(Color color) {
		lineColor = color;
	}
	
	/**
	 * Sets the style of the line to the given brush style
	 * @param brushStyle the <code>Stroke</code> object used to draw this 
	 * line
	 */
	public void setBrushStyle(Stroke brushStyle) {
		brushType = brushStyle;
	}
	
	/**
	 * Gets all of the x-coordinate values that make up this line
	 * @return the x-coordinates of this line
	 */
	public int[] getXCoordinates() {
		return xCoordinates;
	}
	
	/**
	 * Gets all of the y-coordinate values that make up this line
	 * @return the y-coordinates of this line
	 */
	public int[] getYCoordinates() {
		return yCoordinates;
	}
	
	/**
	 * Gets the color of the paint that was used to draw this line
	 * @return the paint color used when drawing this line
	 */
	public Color getColor() {
		return lineColor;
	}
	
	/**
	 * Gets the brush type that was used to draw this line
	 * @return the brush style used when drawing this line
	 */
	public Stroke getBrushType() {
		return brushType;
	}

	@Override
	public void paintShape(Graphics2D g) {
		g.setStroke(brushType);
		g.setColor(lineColor);
		g.drawPolyline(xCoordinates, yCoordinates, size);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
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
		int coordinateValues = 0;
		for (int i = 0; i < size / 2; i++) {
			coordinateValues += (xCoordinates[i] + yCoordinates[i]);
		}
		return lineColor.hashCode() + brushType.hashCode() + coordinateValues + size;
	}
}