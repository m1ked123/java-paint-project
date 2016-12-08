package structures;

import java.util.EmptyStackException;

import shapes.CanvasEntity;

/**
 * Represents a LIFO structure which ensures that the last thing
 * pushed on to the top of the stack is the first thing that is removed
 * from it when a pop() operation is performed. This stack is a basic
 * structure and only offers some simple functionality such as push(),
 * pop(), peek(), and isEmpty(). It also returns an iterator which
 * allows for easier iteration over all of the elements that it stores
 * 
 * @version 0.1.1 [7/11/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- update version numbering scheme in documentation (see README for more info)
 * 		-- name changed from MStack to SimpleStack
 *  	-- moved to 'structures' package
 */
public interface SimpleStack {
	/**
	 * Pushes the given CanvasEntity object on to the top of the stack
	 * @param entity the CanvasEntity that is to be added to this stack
	 */
	public void push(CanvasEntity entity);
	
	/**
	 * Empties the current stack of everything that might be inside of
	 * it
	 */
	public void empty();
	
	/**
	 * Removes and returns the the entity at the top of the stack. If the
	 * stack is empty and this method is called, it will throw an
	 * EmptyStackException 
	 * @return the entity that is at the top of the stack which is the
	 * last thing that was drawn on the canvas
	 * @throws EmptyStackException if this method is called and the 
	 * stack is empty
	 */
	public CanvasEntity pop();
	
	/**
	 * Checks to see if this stack is empty
	 * @return true if this stack is not storing any entities
	 */
	public boolean isEmpty();
	
	/**
	 * Peeks at the top of the stack and returns what is there without
	 * removing it
	 * @return the entity that is at the top of the stack which is the
	 * last thing that was drawn on the PaintCanvas
	 */
	public CanvasEntity peek();
	
	/**
	 * Returns an Iterator object that iterates over the CanvasEntity
	 * objects that are stored in this stack
	 * @return an iterator for this particular stack
	 */
	public SimpleIterator iterator();
}
