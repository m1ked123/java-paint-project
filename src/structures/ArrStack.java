package structures;

import java.util.EmptyStackException;

import shapes.CanvasEntity;

/**
 * Class <code>ArrStack</code> represents a bounded LIFO buffer of
 * <code>CanvasEntity</code> objects. When the buffer becomes full,
 * the oldest element is overwritten.
 * 
 * @version 0.1.1 [7/11/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- first created :)
 * 		-- no iterator yet, so code using this might be a bit messy
 */
public class ArrStack implements SimpleStack {
	public static final int DEFAULT_SIZE = 10;
	private CanvasEntity[] buffer;
	private int size;
	private int top, bottom; // pointers to top and bottom of stack
	
	/**
	 * The default constructor that initiates the buffer with a
	 * default size of 10 entities.
	 */
	public ArrStack() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * A constructor that sets the buffer size to the given amount.
	 * It would be best to keep this kind of small in order to save on
	 *  space.
	 * @param size the size of the buffer
	 */
	public ArrStack(int size) {
		buffer = new CanvasEntity[size];
		top = 0;
		bottom = 0;
	}
	
	@Override
	public void push(CanvasEntity entity) {
		if (top > (buffer.length - 1)) {
			top = 0;
		}
		buffer[top] = entity;
		top++;
		if (size < buffer.length) {
			size++;
		}
	}

	@Override
	public void empty() {
		top = bottom;
	}

	@Override
	public CanvasEntity pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		top--;
		if (top < 0 && size > 0) {
			top = buffer.length - 1;
		}
		CanvasEntity result = buffer[top];
		size--;
		return result;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public CanvasEntity peek() {
		return buffer[top--];
	}

	@Override
	public SimpleIterator iterator() {
		throw new UnsupportedOperationException();
	}
}
