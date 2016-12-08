package structures;

import shapes.CanvasEntity;

/**
 * Class EntityStackNode represents a node of a LinkedList that 
 * stores a CanvasEntity and a reference to the next node in the
 * list
 * 
 * @version 0.1.1 [7/11/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- update version numbering scheme in documentation (see README for more info)
 * 		-- changed name from EntityStackNode to LLEntityStackNode
 * 		-- moved to 'structures' package
 */
public class LLEntityStackNode {
	public CanvasEntity data;
	public LLEntityStackNode next;

	/**
	 * Creates a new EntityStackNode that stores the given entity
	 * and exists at the end of the LinkedList
	 * @param entity the CanvasEntity that is to be stored by this
	 * node
	 */
	public LLEntityStackNode(CanvasEntity entity) {
		this(entity, null);
	}

	/**
	 * Constructs a new EntityStackNode that stores the given entity
	 * and reference to the next node in the LinkedList
	 * @param entity the CanvasEntity that is to be stored by this
	 * node
	 * @param next the reference to the next item in the stack
	 */
	public LLEntityStackNode(CanvasEntity entity, LLEntityStackNode next) {
		this.data = entity;
		this.next = next;
	}
}