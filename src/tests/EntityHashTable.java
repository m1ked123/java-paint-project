package tests;

import shapes.CanvasEntity;
import utilities.PrimesGenerator;

/**
 * Class <code>EntityHashTable</code> represents a simple hash table
 * implementation that uses quadratic probing in order to store
 * <code>CanvasEntity</code> objects. There are a few public functions
 * that allow the user to insert, remove, empty, and poll the hash
 * table for a particular entity. There is also a way to get a string
 * representation of the table and it's contents.
 * 
 * This class should not be used for instances where the order of the
 * <code>CanvasEntity</code> objects does not matter.
 * 
 * @version 0.1.3 [7/15/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- minor fixes to make code work properly
 * 		-- added isEmpty function
 * 		-- moved to 'tests' package
 */
public class EntityHashTable {
	public static final int DEFAULT_CAPACITY = 10;
	private CanvasEntity[] hashTable;
	private int size;
	private int[] primes;
	private int primeIndex;
	
	/**
	 * Constructs a new <code>EntityHashTable</code> with no elements
	 */
	public EntityHashTable() {
		primes = PrimesGenerator.getDoubles(PrimesGenerator.eratosthenesSieveRaw(PrimesGenerator.MAXIMUM_NUMBER), DEFAULT_CAPACITY);
		hashTable = new CanvasEntity[primes[primeIndex]];
		primeIndex++;
	}
	
	/**
	 * Inserts the given entity into the hash table
	 * @param entityToAdd the entity to insert into the table
	 */
	public void insert(CanvasEntity entityToAdd) {
		enforceLoadFactor();
		int hashValue = Math.abs(entityToAdd.hashCode()) % hashTable.length;
		int offset = 1;
		while (hashTable[hashValue] != null && hashTable[hashValue].isActive()) {
			hashValue = (Math.abs(entityToAdd.hashCode()) + (offset * offset)) % hashTable.length;
			offset++;
			if (hashValue >= hashTable.length) {
				hashValue -= hashTable.length;
			}
		}
		hashTable[hashValue] = entityToAdd;
		size++;
	}
	
	/**
	 * Removes the given entity from the hash table
	 * @param entityToRemove the entity to remove from the table
	 */
	public void remove(CanvasEntity entityToRemove) {
		if (contains(entityToRemove)) {
			hashTable[findPosition(entityToRemove)].setActive(false);
			size--;
		}
	}
	
	/**
	 * Empties all of the entries from this hash table.
	 */
	public void empty() {
		size = 0;
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = null;
		}
	}
	
	/**
	 * Returns whether or not the given entity is in this table
	 * @param entity the entity that is being searched for
	 * @return true if the entity is in this table, false otherwise
	 */
	public boolean contains(CanvasEntity entity) {
		CanvasEntity entityPosition = hashTable[findPosition(entity)];
		return entityPosition != null && entityPosition.isActive();
	}

	/**
	 * Gets all the entities that are in this hash table, trimming out
	 * any empty values.
	 * @return a list of the entities in this hash table
	 */
	public CanvasEntity[] getEntities() {
		CanvasEntity[] results = new CanvasEntity[size];
		int j = 0;
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null && hashTable[i].isActive()) {
				results[j] = hashTable[i];
				j++;
			}
		}
		return results;
	}
	
	/**
	 * Gets a string representation of this hash table with the
	 * contained <code>CanvasEntity</code> objects in a
	 * comma-separated list enclosed in curly braces ("{}").
	 */
	public String toString() {
		CanvasEntity[] entities = getEntities();
		String result = "{" + entities[0].toString();
		for (int i = 1; i < entities.length; i++) {
			result += ", " + entities[i].toString();
		}
		result += "}";
		return result;
	}
	
	/**
	 * Gets the amount of entities in this hash table
	 * @return the amount of elements in the table
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Gets whether or not this hash table is empty
	 * @return true if the table is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/****************************************************************
	 * HELPER FUNCTIONS
	 ****************************************************************/
	
	/*
	 * Enforces a load factor of 1/2 by expanding the hash table if
	 * it gets too full
	 */
	private void enforceLoadFactor() {
		if (size >= hashTable.length * .5) {
			CanvasEntity[] newTable = new CanvasEntity[primes[primeIndex]];
			primeIndex++;
			for (int i = 0; i < hashTable.length; i++) {
				if (hashTable[i] != null) {
					newTable[rehash(newTable, hashTable[i])] = hashTable[i];
				}
			}
			hashTable = newTable;
		}
	}
	
	/*
	 * Returns a new hash value for the given entity with the given
	 * new table's capacity in mind
	 */
	private int rehash(CanvasEntity[] newTable, CanvasEntity canvasEntity) {
		int hashValue = Math.abs(canvasEntity.hashCode()) % newTable.length;
		int offset = 1;
		while (newTable[hashValue] != null) {
			hashValue = (Math.abs(canvasEntity.hashCode()) + (offset * offset)) % newTable.length;
			offset++;
		}
		return hashValue;
	}
	
	/*
	 * Finds the current position of the given entity in the hashTable
	 */
	private int findPosition(CanvasEntity entity) {
		int offset = 1;
		int currPosition = Math.abs(entity.hashCode() % hashTable.length);
		while (hashTable[currPosition] != null && !hashTable[currPosition].equals(entity)) {
			currPosition += offset;
			offset += 2;
			if (currPosition >= hashTable.length) {
				currPosition -= hashTable.length;
			}
		}
		return currPosition;
	}
}
