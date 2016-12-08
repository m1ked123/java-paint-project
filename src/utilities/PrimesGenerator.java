package utilities;

/**
 * Class <code>PrimesGenerator</code> is a collection of static 
 * functions that create and return a list of prime numbers from
 * 2 to a given maximum number inclusive. Unless otherwise noted,
 * each function, when given the same parameters, returns the
 * exact same results.
 * 
 * @version 0.1.3 [7/13/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- changed name to "PrimesGenerator"
 * 		-- made class collection of static functions
 *		-- added function for finding primes using Sieve of Eratosthenes
 *		-- added function for getting raw prime bit array used in Sieve of Eratosthenes function
 *		-- added function for getting a set of primes that are double a given number
 */
public class PrimesGenerator {
	/**
	 * The maximum number of that can be used in the range of numbers
	 * in the set that is sifted for primes.
	 */
	public static final int MAXIMUM_NUMBER = 99999998;

	/**
	 * Enumerates primes from a set of numbers between 2 and n
	 * inclusive. This set of primes is the result that is returned.
	 * If n is negative or too large (arbitrarily chosen large number),
	 * an exception is thrown. This function may not be the best for
	 * producing large quantities of prime numbers not only because of
	 * issues with runtime, but also issues with space. The result is 
	 * in raw form meaning that it is the original list of bits used 
	 * rather than packaged integers. If the implementation calls for
	 * integer values, use the eratosthenesSieve function instead.
	 * It can be assumed that a bit of 1 means that the number
	 * corresponding to the index in the set is prime.
	 * @param n the max number in the set
	 * @return a list of prime numbers between 2 and n inclusive as
	 * a list of bits
	 * @throw IllegalArgumentException if n < 2 or n > 99999998
	 */
	public static boolean[] eratosthenesSieveRaw(int n) {
		/*
		 * create list of numbers from 2 to n inclusive
		 * let p = 2
		 * from p, count up to n by p increments and mark true
		 * first number greater than p not marked, if none stop, set p to number
		 * lather, rinse, repeat...
		 */
		if (n < 2 || n > MAXIMUM_NUMBER) {
			throw new IllegalArgumentException("problem with n");
		}
		boolean[] possiblePrimes = new boolean[n + 1];
		for (int i = 0; i < possiblePrimes.length; i++) {
			possiblePrimes[i] = true;
		}
		int p = 2;
		while (p < possiblePrimes.length) {
			//			System.out.print(Arrays.toString(possiblePrimes));
			for (int i = p + p; i < possiblePrimes.length; i += p) {
				possiblePrimes[i] = false;
			}
			int oldP = p + 1;
			while (oldP < possiblePrimes.length && !possiblePrimes[oldP]) {
				oldP++;
			}
			p = oldP;
		}
		return possiblePrimes;
	}

	/**
	 * Enumerates primes from a set of numbers between 2 and n
	 * inclusive. This set of primes is the result that is returned.
	 * If n is negative or too large (arbitrarily chosen large number),
	 * an exception is thrown. This function may not be the best for
	 * producing large quantities of prime numbers not only because of
	 * issues with runtime, but also issues with space. The result is 
	 * a set of integers rather than a list of bits and is suitable for
	 * direct manipulation for those needing prime numbers.
	 * corresponding to the index in the set is prime.
	 * @param n the max number in the set
	 * @return a list of prime numbers between 2 and n inclusive as
	 * a list of integers
	 * @throw IllegalArgumentException if n < 2 or n > 99999998
	 */
	public static int[] eratosthenesSieve(int n) {
		/*
		 * create list of numbers from 2 to n inclusive
		 * let p = 2
		 * from p, count up to n by p increments and mark true
		 * first number greater than p not marked, if none stop, set p to number
		 * lather, rinse, repeat...
		 */
		if (n < 2 || n > MAXIMUM_NUMBER) {
			throw new IllegalArgumentException("problem with n");
		}
		int numPrimes = 1;
		boolean[] possiblePrimes = new boolean[n + 1];
		for (int i = 0; i < possiblePrimes.length; i++) {
			possiblePrimes[i] = true;
		}
		int p = 2;
		while (p < possiblePrimes.length) {
			//			System.out.print(Arrays.toString(possiblePrimes));
			//			System.out.println("\tnum: " + numPrimes);
			for (int i = p + p; i < possiblePrimes.length; i += p) {
				possiblePrimes[i] = false;
			}
			int oldP = p + 1;
			while (oldP < possiblePrimes.length && !possiblePrimes[oldP]) {
				oldP++;
			}
			p = oldP;
			numPrimes++;
		}
		int[] result = createPrimeSet(possiblePrimes, numPrimes);
		return result;
	}

	// takes in a set of possible primes (as a list of bits) and
	// creates a properly formed set of integers with just the 
	// prime numbers that have been enumerated in it.
	private static int[] createPrimeSet(boolean[] possiblePrimes, int numPrimes) {
		int[] result = new int[numPrimes - 1];
		int j = 0;
		for (int i = 2; i < possiblePrimes.length; i++) {
			if (possiblePrimes[i]) {
				result[j] = i;
				j++;
			}
		}
		return result;
	}

	/**
	 * This is an implementation specific function that gets a set of
	 * primes that are double a given starting number. It will return
	 * a set of integer primes from the given bit array of primes.
	 * The starting value given must be larger than 2 and smaller than
	 * the size of the bit array of primes.
	 * @param possiblePrimes the bit array representation of prime numbers
	 * between 2 and n
	 * @param startingNum the starting number to double
	 * @return a list of prime numbers that are double the given starting
	 * number
	 */
	public static int[] getDoubles(boolean[] possiblePrimes, int startingNum) {
		if (startingNum < 2 || startingNum >= possiblePrimes.length) {
			throw new IllegalArgumentException("incorrect starting number");
		}
		int[] temp = new int[getNumPrimes(possiblePrimes)];
		int i = startingNum;
		int size = 0;
		for (int j = 0; j < temp.length && i < possiblePrimes.length; j++) {
			while (i < possiblePrimes.length && !possiblePrimes[i]) {
				i++;
			}
			temp[j] = i;
			i *= 2;
			size++;
		}
		int[] results = new int[size];
		for (int j = 0;  j < results.length; j++) {
			results[j] = temp[j];
		}
		return results;
	}
	
	/*
	 * Gets the number of primes in the given bit array. O(n)
	 * operation where n = possiblePrimes.length
	 */
	private static int getNumPrimes(boolean[] possiblePrimes) {
		int numPrimes = 0;
		for (int i = 2; i < possiblePrimes.length; i++) {
			if (possiblePrimes[i]) {
				numPrimes++;
			}
		}
		return numPrimes;
	}
}
