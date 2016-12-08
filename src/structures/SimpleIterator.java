package structures;


import java.util.NoSuchElementException;

/**
 * Defines the behavior for a simple iterator that only allows for a
 * check to see if there is more to iterate over and gets the next
 * thing in the list that the iterator is going over.
 * 
 * @version 0.1.1 [7/11/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- update version numbering scheme in documentation (see README for more info)
 *  	-- moved to 'structures' package
 */
public interface SimpleIterator {
	/**
	 * Checks to see if there is more for this iterator to iterate
	 * over.
	 * @return true if there is still more in the list to iterate over
	 */
	public boolean hasNext();
	
	/**
	 * Returns the next thing in the iteration and then moves the 
	 * iterator forward.
	 * @return the EntityStackNode that is next in the iteration
	 * @throws NoSuchElementException if there is no more left in the
	 * list to iterate over.
	 */
	public LLEntityStackNode next();
}
