package tests;

import java.util.Arrays;

import utilities.PrimesGenerator;

/*
 * simple test program for EratosthenesSieve class
 */
public class PossiblePrimesTest {
	public static void main(String[] args) {
		int[] primes = PrimesGenerator.eratosthenesSieve(PrimesGenerator.MAXIMUM_NUMBER);
		System.out.println(Arrays.toString(primes));
	}
}
