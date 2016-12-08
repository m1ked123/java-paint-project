package structures;

import shapes.CanvasEntity;

/**
 * Class EntityStack represents a LIFO stack that stores a collection
 * of CanvasEntity objects with the entity at the top being the last
 * thing that was drawn on the canvas
 * 
 * @version 0.1.2 [7/15/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- fixed a small bug in the push function
 */
public class LLEntityStack implements SimpleStack {
	private LLEntityStackNode front;

	/**
	 * Constructs a new empty EntityStack
	 */
	public LLEntityStack() {
		front = null;
	}

	@Override
	public void push(CanvasEntity entity) {
		if (front == null) {
			front = new LLEntityStackNode(entity);
		} else {
			front = new LLEntityStackNode(entity, front);
		}
	}

	@Override
	public void empty() {
		front = null;
	}

	@Override
	public CanvasEntity pop() {
		LLEntityStackNode curr = front;
		front = front.next;
		return curr.data;
	}

	@Override
	public boolean isEmpty() {
		return front == null;
	}

	@Override
	public CanvasEntity peek() {
		return front.data;
	}

	@Override
	public LLEntityIterator iterator() {
		return new LLEntityIterator(front);
	}
}