package structures;

import java.util.NoSuchElementException;

/**
 * Class EntityIterator represents a SimpleIterator that iterates over
 * the elements in a list, returning each element. This type of iterator
 * is much simpler that Java's iterator, only having two basic
 * operations: next() and hasNext().
 * 
 * @version 0.1.1 [7/11/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- update version numbering scheme in documentation (see README for more info)
 * 		-- changed name from EntityIterator to LLEntityIterator
 * 		-- moved to 'structures' package
 */
public class LLEntityIterator implements SimpleIterator {
	private LLEntityStackNode currentPosition;

	/**
	 * Constructs a new EntityIterator with a reference to the given
	 * location in the list
	 * @param front the front of the list that this object is iterating
	 * over
	 */
	public LLEntityIterator(LLEntityStackNode front) {
		this.currentPosition = front;
	}

	@Override
	public boolean hasNext() {
		return currentPosition != null;
	}

	@Override
	public LLEntityStackNode next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		LLEntityStackNode result = currentPosition;
		currentPosition = currentPosition.next;
		return result;
	}
}
