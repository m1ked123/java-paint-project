package main;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import gui.PaintFrame;


/**
 * This program creates a window that open and runs an application
 * that allows the user to draw on a blank canvas
 * 
 * @version 0.4.1 [8/25/15]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- the brush size can be a max of 10px
 * 		-- minor cosmetic changes
 */
public class PaintAppClient {
	public static void main(String[] args) {
		Toolkit kit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = kit.getScreenSize();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				PaintFrame frame = new PaintFrame(screenSize.width / 2, 
						screenSize.height / 2, "Doodler");
				frame.start();
			}
		});
	}
}