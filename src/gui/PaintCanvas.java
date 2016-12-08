package gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shapes.CanvasEntity;
import shapes.Ellipse;
import shapes.Line;
import shapes.Rectangle;
import structures.ArrStack;
import structures.LLEntityStack;
import structures.SimpleStack;

/**
 * Class <code>PaintCanvas</code> represents a blank rectangular area
 * on which the user can create simple doodles in different color
 * paint using varying brush sizes. There are multiple brush sizes to
 * choose from. There quite a few colors to choose from because the RGB
 * spectrum and hexadecimal codes are supported.
 * 
 * This canvas supports anti-aliasing, but this features is not enabled
 * by default. The user must choose to enable it because anti-aliasing
 * can cause performance issues on slower machines.
 * 
 * @version 0.4.1 [8/24/15]
 * 
 * @author Michael Davis
 */

/*
 * Version Notes:
 * 		-- Consolidated all painting code into one method that
 * 		paints the appropriate entity
 */
public final class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 6815403541551122174L;
	
	// the width and height of the canvas and its image
	private int width, height;
	
	// the old x and y value
	private int oldX, oldY;
	
	// the color of the paint being used
	private Color paintColor;
	
	// the label where the mouses's coordinates will be displayed
	private JLabel mouseCoordinates;
	
	// the type of brush being used
	private Stroke brushType;
	
	// the line that is currently being drawn on the canvas
	private Line currentLine;
	
	// the rectangle currently being drawn on the canvas
	private Rectangle currentRect;
	
	// the ellipse currently being drawn on the canvas
	private Ellipse currentEllipse;
	
	// storage for the entities that have been added to or removed from
	// the canvas
	private SimpleStack garbageEntities, entities;
	
	// flags to tell which entity is currently being drawn
	private boolean drawRectangles, drawLines, drawOvals;
	private boolean filledRects, filledOvals;
	
	// flag for anti-aliasing
	private boolean isPretty;
	
	// the canvas image
	private BufferedImage doodle;
	
	/**
	 * Creates a new blank paint canvas of given width and height
	 * with a white background
	 * @param width the preferred width of the canvas
	 * @param height the preferred height of the canvas
	 */
	public PaintCanvas(int width, int height) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.height = height;
		this.width = width;
		setBackground(Color.WHITE);
		entities = new LLEntityStack();
		garbageEntities = new ArrStack();
		
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		drawRectangles = false;
		drawLines = true;
		drawOvals = false;
			
		paintColor = Color.BLACK;
		brushType = new BasicStroke(3, BasicStroke.CAP_ROUND, 
				BasicStroke.JOIN_ROUND);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	/**
	 * Clears all of the entities on the canvas by clearing the lists
	 * that store them. Also eliminates any shapes that are currently
	 * being draw.
	 */
	public void clear() {
		entities.empty();
		garbageEntities.empty();
		currentRect = null;
		currentEllipse = null;
		currentLine = null;
		doodle = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		repaint();
	}

	/**
	 * Associates a label with this canvas that will show a representation
	 * of the mouse cursor's location on the  canvas
	 * @param mouseCoordinates the label that will be associated to this canvas
	 */
	public void associateLabel(JLabel mouseCoordinates) {
		this.mouseCoordinates = mouseCoordinates;
	}
	
	// updates the label associated with the coordinates of the mouse
	// to the coordinates of the mouse at the given event location
	private void updateLabel(MouseEvent e) {
		mouseCoordinates.setText(e.getX() + " x " + e.getY());		
	}
	
	/**
	 * Sets the anti-aliasing property for this PaintCanvas to the given
	 * property. The panel does not automatically draw with anti-aliasing
	 * enabled, but can be turned on after the application is running.
	 * @param isPretty whether or not anti-aliasing is enabled
	 */
	public void setPretty(boolean isPretty) {
		this.isPretty = isPretty;
	}
	
	/**
	 * Sets the background to the given color.
	 * @param c the color that the background should will
	 */
	public void setBackgroundColor(Color c) {
		super.setBackground(c);
		repaint();
	}
	
	/**
	 * Saves the current canvas and its contents as the given file. 
	 * If there is no file extension given, then the file will be 
	 * saved as the given file type as defined by the current filter
	 * that is selected
	 * @param saveFile the file to which this image is to be saved
	 * @param fileType the file extension that defines the type of
	 * the file that is to be saved
	 */
	public void save(File saveFile, String fileType) {
		try {
			BufferedImage savedDoodle = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			paintComponent(savedDoodle.getGraphics());
			ImageIO.write(savedDoodle, fileType, saveFile);
		} catch (IOException ex) {
			System.err.println("the file could not be saved");
		}
	}
	
	/**
	 * Removes the last painted line from the canvas. This removes it
	 * from the list of lines this canvas is keeping and places it in
	 * another list for safe keeping, just in case
	 */
	public void undo() {
		if (!entities.isEmpty()) {
			garbageEntities.push(entities.pop());
			redrawImage();
		}
	}
	
	/**
	 * Adds the last thing that was just removed from the canvas. This
	 * can only be done if there is something in the garbage entities list
	 * to begin with.
	 */
	public void redo() {
		if (!garbageEntities.isEmpty()) {
			entities.push(garbageEntities.pop());
			redrawImage();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (isPretty) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		if (doodle == null) {
			width = this.getWidth();
			height = this.getHeight();
			doodle = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		}
		g2.drawImage(doodle, null, 0, 0);
		if (currentLine != null) {
			currentLine.paintShape(g2);
		} else if (currentRect != null) {
			currentRect.paintShape(g2);
		} else if (currentEllipse != null) {
			currentEllipse.paintShape(g2);
		}
	}
	
	/****************************************************************
	 * BRUSH METHODS
	 ***************************************************************/
	
	/**
	 * Gets the current color of the paint being used on this canvas
	 * @return <code>paintColor</code> - the current paint color
	 */
	public Color getPaintColor() {
		return paintColor;
	}
	
	/**
	 * Sets the color of the paint brush to the given color.
	 * @param c the current color of the paint being used
	 */
	public void setPaintColor(Color c) {
		paintColor = c;
	}

	/**
	 * Takes a given value and sets the style of the brush to a new
	 * style with the given size as the size of the new <code>brushType</code>
	 * @param size the width of the brush being used
	 */
	public void setBrushSize(int size) {
		brushType = new BasicStroke(size, BasicStroke.CAP_ROUND, 
				BasicStroke.JOIN_ROUND);
	}
	
	/**
	 * Sets the pointer for the canvas to the given pointer type
	 * @param cursorType the cursor to change to
	 */
	public void setPointer(int cursorType) {
		this.setCursor(new Cursor(cursorType));
	}
	
	/****************************************************************
	 * IMAGE CHANGEING METHODS
	 ***************************************************************/

	/**
	 * Used to redraw the image if the anti aliasing property has been
	 * changed.
	 */
	public void redrawImage() {
		LLEntityStack temp = new LLEntityStack();
		while (!entities.isEmpty()) {
			temp.push(entities.pop());
		}
		doodle = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		while (!temp.isEmpty()) {
			CanvasEntity tempEntity = temp.pop();
			drawToDoodle(tempEntity);
			entities.push(tempEntity);
		}
		repaint();
	}
	
	// draws the given entity to the canvas
	private void drawToDoodle(CanvasEntity entity) {
		Graphics2D g2 = (Graphics2D)doodle.getGraphics();
		if (isPretty) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		entity.paintShape(g2);
	}
	
	/****************************************************************
	 * DRAWING METHODS
	 ***************************************************************/
	
	// Draws the appropriate entity on the canvas based on which entity
	// is selected to be painted. All entities draw where the user's
	// mouse triggers the event to occur.
	private void drawEntity(MouseEvent evt) {
		int currX = evt.getX();
		int currY = evt.getY();
		if (this.currentLine != null) {
			repaint();
			currentLine.add(currX, currY);
			oldX = currX;
			oldY = currY;
		} else {
			int entityWidth = 0;
			int entityHeight = 0;
			if (this.currentRect != null) {
				currentRect.setSize(currX - oldX, currY - oldY);
				entityWidth = currentRect.getWidth();
				entityHeight = currentRect.getHeight();
			} else {
				currentEllipse.setSize(currX - oldX, currY - oldY);
				entityWidth = currentEllipse.getWidth();
				entityHeight = currentEllipse.getHeight();
			}
			int[] xWidth = transform(oldX, entityWidth);
			int[] yHeight = transform(oldY, entityHeight);
	        
	        if (this.currentRect != null) {
		        currentRect.setBounds(xWidth[0], yHeight[0], xWidth[1], yHeight[1]);
			} else {
		        currentEllipse.setBounds(xWidth[0], yHeight[0], xWidth[1], yHeight[1]);
			}
	        repaint();
		}
	}
	
	// Returns the result of transforming the given coordinate and
	// corresponding dimension. Essentially, the dimension that 
	// corresponds to the x coordinate is a shape's width, and the one
	// that corresponds to the y coordinate is the height. The
	// resulting transform represents a flip of the shape across the
	// appropriate axis.
	private int[] transform(int coordinate, int dimension) {
		if (dimension < 0) {
			dimension = 0 - dimension;
			coordinate = coordinate - dimension + 1; 
            if (coordinate < 0) {
            	dimension += coordinate; 
            	coordinate = 0;
            }
        }
		return new int[] {coordinate, dimension};
	}
	
	/**
	 * Sets whether or not the type of shape to be drawn is a rectangle
	 * @param bool whether or not the brush is set to draw a rectangle
	 */
	public void setDrawRectangles(boolean bool) {
		drawRectangles = bool;
	}
	
	/**
	 * Sets whether or not the type of shape to be drawn is a line
	 * @param bool whether or not the brush is set to draw a line
	 */
	public void setDrawLines(boolean bool) {
		drawLines = bool;
	}
	
	/**
	 * Sets whether or not the type of shape to be drawn is an oval
	 * @param bool whether or not the brush is set to draw an oval
	 */
	public void setDrawOvals(boolean bool) {
		drawOvals = bool;
	}
	
	/**
	 * Sets whether or not the type of shape to be drawn is a filled
	 * rectangle.
	 * @param bool true if a filled rectangle should be drawn, false
	 * otherwise
	 */
	public void setFilledRects(boolean bool) {
		setDrawRectangles(bool);
		filledRects = bool;
	}
	
	/**
	 * Sets whether or not the type of shape to be drawn is a filled
	 * oval.
	 * @param bool true if a filled oval should be drawn, false
	 * otherwise
	 */
	public void setFilledOvals(boolean bool) {
		setDrawOvals(bool);
		filledOvals = bool;
	}
	
	/****************************************************************
	 * MOUSE EVENT LISTENERS
	 ***************************************************************/

	@Override
	public void mouseEntered(MouseEvent evt) {}

	@Override
	public void mouseExited(MouseEvent evt){}

	@Override
	public void mouseClicked(MouseEvent evt) {
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		oldX = evt.getX();
		oldY = evt.getY();
		if (drawLines) {
			currentLine = new Line();
			currentLine.add(oldX, oldY);
			currentLine.setBrushStyle(brushType);
			currentLine.setColor(paintColor);
		} else if (drawRectangles) {
			currentRect = new Rectangle(oldX, oldY, 0, 0);
			currentRect.setFilled(filledRects);
			currentRect.setColor(paintColor);
			currentRect.setBrushStyle(brushType);
		} else if (drawOvals) {
			currentEllipse = new Ellipse(oldX, oldY, 0, 0);
			currentEllipse.setFilled(filledOvals);
			currentEllipse.setColor(paintColor);
			currentEllipse.setBrushStyle(brushType);
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (drawLines) {
			drawToDoodle(currentLine);
			entities.push(currentLine);
			currentLine = null;
		} else if (drawRectangles) {
			drawToDoodle(currentRect);
			entities.push(currentRect);
			currentRect = null;
		} else if (drawOvals) {
			drawToDoodle(currentEllipse);
			entities.push(currentEllipse);
			currentEllipse = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		drawEntity(evt);
		updateLabel(evt);
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		updateLabel(evt);
	}
}