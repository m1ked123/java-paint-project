package shapes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * Defines the behavior of all things that are drawn on the 
 * <code>PaintCanvas</code>. These things are called "entities" and
 * represent sprites that are draw on the canvas. Entities define how
 * they are drawn on the canvas and have 5 behaviors that must be
 * implemented: getColor, getBrushType, paintShape, setColor, and
 * setBrushStyle. These methods are used in order to help the canvas
 * know how the entity will display when it is rendered.
 * 
 * @version 0.1.5 [7/15/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- added equals function
 */
public interface CanvasEntity {
	/**
	 * Gets the <code>Color</code> used to draw this entity 
	 * @return the <code>Color</code> object that was used when painting
	 * this entity
	 */
	public Color getColor();
	
	/**
	 * Gets the <code>Stroke</code> used to draw this entity 
	 * @return the <code>Stroke</code> object that was used when painting
	 * this entity
	 */
	public Stroke getBrushType();
	
	/**
	 * Defines how this entity is to appear on the <code>PaintCanvas</code>
	 * @param g the <code>Graphics2D</code> object that will draw the
	 * entity in the proper location
	 */
	public void paintShape(Graphics2D g);
	
	/**
	 * Sets the color of the paint used to draw this entity to the 
	 * given color
	 * @param c the color of the paint used to draw this entity
	 */
	public void setColor(Color c);
	
	/**
	 * Sets the type of brush used to paint this entity to the given 
	 * <code>Stroke</code> object
	 * @param brushStyle the style of brush used to paint this entity
	 */
	public void setBrushStyle(Stroke brushStyle);
	
	@Override
	public int hashCode();
	
	/**
	 * Gets whether or not this entity is active
	 * @return true if the entity is active, false otherwise.
	 */
	public boolean isActive();
	
	/**
	 * Sets the activation status of this entity to the given value
	 * @param active whether or not this entity is active
	 */
	public void setActive(boolean active);

	/**
	 * Indicates whether or not this CanvasEntity is equal to the
	 * given entity. 
	 * 
	 * Object equality is the same as what is defined by Java meaning
	 * the following...
	 * 
	 * <ul>
	 * 		<li>
	 * 			Reflexive: for a non-null object x, x.equals(x) returns
	 * 			true. x = x
	 * 		</li>
	 * 		<li>
	 * 			Symmetric: for any non-null objects x and y, if 
	 * 			x.equals(y) returns true, y.equals(x) should also
	 * 			return true. If x = y, y = x
	 * 		</li>
	 * 		<li>
	 * 			Transitive: for any non-null objects x, y, and z, if
	 * 			x.equals(y) returns true, y.equals(z) returns true, 
	 * 			x.equals(z) should also return true. If x = y, and 
	 * 			y = z, then x = z
	 * 		</li>
	 * 		<li>
	 * 			Consistent: for any non-null objects x and y, 
	 * 			x.equals(y) should always return true or false unless the
	 * 			objects x and y are in some way modified between calls.
	 * 		</li>
	 * 		<li>
	 * 			If an object x is non-null, then x.equals(null) will
	 * 			always be false.
	 * 		</li>
	 * </ul>
	 * 
	 * For <code>CanvasEntity</code> objects, equivalence is dependent on
	 * a number of aspects including dimensionality, brush stroke type,
	 * and entity color.
	 * 
	 * @param other the object to which we are comparing
	 * @return true if this entity is the equivalent of the given 
	 * entity, false otherwise
	 */
	public boolean equals(CanvasEntity other);
}