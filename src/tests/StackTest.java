package tests;

import shapes.Rectangle;
import structures.ArrStack;

/*
 * a simple program to test array stack implementation functions
 */
public class StackTest {
	public static void main(String[] args) {
		ArrStack as = new ArrStack(200);
		for(int i = 0; i < 200; i++) {
			as.push(new Rectangle(0,0,i,i));
		}
		while (!as.isEmpty()) {
			System.out.println(as.pop());
		}
	}
}
