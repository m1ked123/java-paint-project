package tests;

import java.awt.BasicStroke;
import java.util.Random;

import shapes.CanvasEntity;
import shapes.Ellipse;
import shapes.Rectangle;

public class HashTableTest {
	public static void main(String[] args) {
//		CanvasEntity thisShape = new Rectangle(0, 0, 10, 10);
//		thisShape.setBrushStyle(new BasicStroke(5, BasicStroke.CAP_ROUND, 
//				BasicStroke.JOIN_ROUND));
//		CanvasEntity thatShape = new Ellipse(0, 0, 10, 10);
//		thatShape.setBrushStyle(new BasicStroke(5, BasicStroke.CAP_ROUND, 
//				BasicStroke.JOIN_ROUND));
//		testHashCode(thisShape, thatShape);
//		System.out.println("equal: " + thatShape.equals(thisShape));
		testHashTable();
		System.out.println("test complete...");
	}
	
	public static void testHashCode(CanvasEntity thisEntity, CanvasEntity thatEntity) {
		System.out.println(thisEntity.hashCode());
		System.out.println(thatEntity.hashCode());
	}
	
	public static void testHashTable() {
		int numShapes = 99;
//		System.out.println("adding " + numShapes + " to hash table");
		EntityHashTable eht = new EntityHashTable();
		CanvasEntity[] entitiesToRemember = new CanvasEntity[numShapes];
		int index = 0;
		Random r = new Random();
		for (int i = 0; i < numShapes; i++) {
			CanvasEntity newShape = null;
			int randInt = r.nextInt();
			if (i % 2 == 0) {
				newShape = new Rectangle(r.nextInt(2000), r.nextInt(2000), r.nextInt(2000), r.nextInt(2000));
				newShape.setBrushStyle(new BasicStroke(5, BasicStroke.CAP_ROUND, 
						BasicStroke.JOIN_ROUND));
			} else {
				newShape = new Ellipse(r.nextInt(2000), r.nextInt(2000), r.nextInt(2000), r.nextInt(2000));
				newShape.setBrushStyle(new BasicStroke(5, BasicStroke.CAP_ROUND, 
						BasicStroke.JOIN_ROUND));
			}
			if (randInt % 2 == 0) {
				entitiesToRemember[index] = newShape;
				index++;
			}
//			System.out.println("adding " + newShape + " to table");
			eht.insert(newShape);
//			System.out.println("table size-->" + eht.getSize());
		}
//		System.out.println("hash table-->" + eht);
//		System.out.println("------------------------------------------------------------------------------");
//		System.out.println("removing "+ index + " shapes");
		for (int i = 0; i < index; i++) {
//			System.out.println("removing " + entitiesToRemember[i] + " from table");
			eht.remove(entitiesToRemember[i]);
//			System.out.println("table size (after remove)-->" + eht.getSize());
		}
		System.out.println("hash table-->" + eht);
	}
}
