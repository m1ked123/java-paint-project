package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Abstract class <code>AbstractRectangle</code> is an abstract
 * representation of a rectangular shape that specifies an area in a
 * coordinate space.
 * 
 * <code>AbstractRectangle</code>s have a location in the coordinate
 * space that is defined by the coordinates of their upper-left hand
 * corners. Their heights and widths are the distances from this corner
 * to their lower-right hand corners.
 * 
 * Classes utilizing this abstract class should be aware that this
 * particular rectangle only uses integer precision when storing
 * location and size data.
 * 
 * @version 0.4.1 [8/24/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- AbstractRectangles can no longer be instantiated and now
 * 		serve as the underlying class that represents Ellipses and
 * 		Rectangles that are drawn on the PaintCanvas.
 * 		-- Updated documentation and methods
 */
public abstract class AbstractRectangle implements CanvasEntity {
	public int x, y; // the x/y coordinate of the upper left corner
	public int width, height; // the height and width of this rectangle
	public Stroke brushType; // the type of brushed used to paint
	public Color paintColor; // the color of paint
	public boolean active; // whether this entity is active
	public boolean isFilled; // whether this entity should be filled
	
	/**
	 * Constructs a basic <code>AbstractRectangle<code> located at the
	 * origin. The resulting rectangle will have no height or width.
	 */
	public AbstractRectangle() {
		this(0, 0, 0, 0);
	}
	
	/**
	 * Constructs a new <code>AbstractRectangle</code> with the given
	 * dimensions. It initiates the paint color to black and the brush
	 * type to null.
	 * @param x the x-coordinate of the upper-left corner
	 * @param y the y-coordinate of the upper-left corner
	 * @param width the width of the Rectangle
	 * @param height the height of the Rectangle
	 */
	public AbstractRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		brushType = null;
		paintColor = Color.BLACK;
		active = true;
	}
	
	/**
	 * Gets the x coordinate of the upper-left corner of this rectangle
	 * @return x the x-coordinate of the upper-left hand corner of the 
	 * bounding rectangle
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the y-coordinate of the upper-left corner of this rectangle
	 * @return y the y-coordinate of the upper-left hand corner of the 
	 * bounding rectangle
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the width of this rectangle
	 * @return width the width of this rectangle
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height of this rectangle
	 * @return height the height of this rectangle
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the dimensions of the bounding rectangle for this shape
	 * to the given x, y, width, and height parameters.
	 * @param x the x-coordinate of the upper-left hand corner of the 
	 * bounding rectangle.
	 * @param y the y-coordinate of the upper-left hand corner of the
	 * bounding rectangle.
	 * @param width the width of the bounding rectangle
	 * @param height the height of the bounding rectangle
	 */
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets the size of this rectangle to the given width and height
	 * @param width the new width for this rectangle
	 * @param height the new height for this rectangle
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets the location of this rectangle to the given location in 
	 * the coordinate space
	 * @param x the new x coordinate for the upper-left hand corner
	 * of this rectangle
	 * @param y the new y coordinate of the upper-right hand corner 
	 * of this rectangle
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Color getColor() {
		return this.paintColor;
	}

	@Override
	public Stroke getBrushType() {
		return this.brushType;
	}

	@Override
	public void setColor(Color c) {
		this.paintColor = c;
	}

	@Override
	public void setBrushStyle(Stroke brushStyle) {
		this.brushType = brushStyle;
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
		return false;
	}

	@Override
	public void paintShape(Graphics2D g) {
		return; // the paint shape method must be implemented by class
	}

	/**
	 * Gets whether or not this entity should be rendered as a filled
	 * entity or as a regular entity transparent fill.
	 * @return true if the entity should be filled, false otherwise.
	 */
	public boolean isFilled() {
		return this.isFilled;
	}
	
	/**
	 * Sets whether or not this entity should be rendered as filled.
	 * @param filled whether or not this entity should be filled.
	 */
	public void setFilled(boolean filled) {
		this.isFilled = filled;
	}
}