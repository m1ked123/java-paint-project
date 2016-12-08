package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilities.FileUtilities;

/**
 * Class <code>PaintFrame</code> represents the frame of a paint
 * application which allows the user to paint on and make various
 * interactions with the canvas
 * 
 * @version 0.4.1 [8/25/2015]
 * 
 * @author Michael Davis
 *
 */

/*
 * Version Notes:
 * 		-- The size of the paint brush can't be larger than 10px
 */
public final class PaintFrame extends JFrame implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 8701478026870551725L;

	/**
	 * The default width of the application window
	 */
	public static final int DEFAULT_WIDTH = 500;

	/**
	 * The default height of the application window
	 */
	public static final int DEFAULT_HEIGHT = 400;

	/**
	 * The default title of the application window
	 */
	public static final String DEFAULT_TITLE = "Paint";

	// the menu bar that appears at the top of the application
	private JMenuBar menuBar;

	// menus for the program
	private JMenu file, edit, customize, help;

	// an item for clearing the drawings currently on the canvas
	private JMenuItem clear;

	// an item for saving the drawings to a file
	private JMenuItem save;

	// menu items for undoing and redoing edits to the canvas
	private JMenuItem undo, redo;

	// a menu item that exits the program
	private JMenuItem exit;
	
	// a menu item that allows the user to pick a new background color
	private JMenuItem changeBackground;

	// buttons for toggling shapes to draw
	private JRadioButton lines, rectangles, ovals, fillRects, fillOvals;

	// a toggle for anti-aliasing when rendering drawings
	private JCheckBox antialiasing;
	
	// a toggle for locking the toolbar
	private JCheckBox lockToolbar;

	// toggles for the type of cursor to be used as the pointer for
	// the canvas
	private JRadioButton cursor, pointer, crosshair;

	// a button for picking a paint color
	private JButton chooseColor;

	// a slider to chose the size of the brush for painting
	private JSlider sizePicker;

	// the status bar located at the bottom of the application that
	// displays a bunch of app information
	private JPanel statusBar;

	// the tool bar with tools for use when drawing
	private JToolBar toolBar;

	// a dialog for picking the color of the paint
	private JDialog colorDialog;

	// coordinates for the mouse's location on the canvas
	private JLabel mouseCoordinates;

	// a color chooser
	private JColorChooser colorChooser;

	// the main canvas for painting on
	private PaintCanvas canvas;

	// the icon for the color picker that displays the current selected
	// color.
	private PaintColorIcon pci;

	// the file chooser used in saving the file
	private JFileChooser chooser;
	
	// flags for if we're changing paint or background color
	private boolean changingPaint, changingBackground;

	/**
	 *  Alternate constructor that constructs a paint frame with 
	 *  default width, height, and title
	 */
	public PaintFrame() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_TITLE);
	}

	/**
	 * Constructs a paint frame with given width, height, and title.
	 * @param width the width of the application window
	 * @param height the height of the application window
	 * @param title the title of the application
	 * @throws IllegalArgumentException if width or height is negative.
	 */
	public PaintFrame(int width, int height, String title) {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("invalid dimensions: "
					+ "w-" + width + "h-" + height);
		}
		createMenu();
		chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(FileUtilities.PNG_FILTER);
		chooser.addChoosableFileFilter(FileUtilities.JPG_FILTER);
		chooser.addChoosableFileFilter(FileUtilities.JPEG_FILTER);
		createToolbar();

		// constructs the color chooser and creates the corresponding
		// dialog that houses it
		colorChooser = new JColorChooser();
		colorDialog = JColorChooser.createDialog(colorChooser, "Color Chooser", 
				true, colorChooser, this, null);

		// creates the label that houses the coordinates of the mouse on
		// the canvas
		mouseCoordinates = new JLabel("0 x 0");
		mouseCoordinates.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 12));

		statusBar = new JPanel(new BorderLayout());

		statusBar.add(mouseCoordinates, BorderLayout.SOUTH);

		// constructs the canvas and associates the coordinate label
		// with it
		canvas = new PaintCanvas(width, height);
		canvas.associateLabel(mouseCoordinates);
		
		// sets up the frame with the proper components
		setTitle(title);
		setResizable(false);
		setLayout(new BorderLayout());
		setLocationByPlatform(true);
		add(toolBar, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	/**
	 * Starts the program by showing the <code>PaintFrame</code>
	 */
	public void start() {
		setVisible(true);
	}
	
	/****************************************************************
	 * GUI SETUP FUNCTIONS
	 ***************************************************************/
	
	private void createMenu() {
		menuBar = new JMenuBar();
		
		createFileMenu();
		createEditMenu();
		createCustomizeMenu();
		help = new JMenu("Help");

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(customize);
		menuBar.add(help);
	}
	
	private void createFileMenu() {
		file = new JMenu("File");
		
		save = new JMenuItem("Save");
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		
		file.add(save);
		file.add(exit);
	}
	
	private void createEditMenu() {
		edit = new JMenu("Edit");
		
		clear = new JMenuItem("Clear");
		clear.addActionListener(this);
		
		undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		
		redo = new JMenuItem("Redo");
		redo.addActionListener(this);
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		
		antialiasing = new JCheckBox("Antialiasing");
		antialiasing.addChangeListener(this);
		
		edit.add(clear);
		edit.add(undo);
		edit.add(redo);
		edit.addSeparator();
		edit.add(antialiasing);
	}
	
	private void createCustomizeMenu() {
		customize = new JMenu("Customize");
		
		ButtonGroup group = new ButtonGroup();
		
		pointer = new JRadioButton("pointer");
		pointer.setSelected(true);
		pointer.addActionListener(this);
		
		crosshair = new JRadioButton("crosshair");
		crosshair.addActionListener(this);
		
		cursor = new JRadioButton("cursor");
		cursor.addActionListener(this);
		
		lockToolbar = new JCheckBox("lock toolbar");
		lockToolbar.addChangeListener(this);
		
		changeBackground = new JMenuItem("Change Background Color");
		changeBackground.addActionListener(this);
		
		group.add(pointer);
		group.add(cursor);
		group.add(crosshair);
		
		customize.add(cursor);
		customize.add(crosshair);
		customize.add(pointer);
		customize.addSeparator();
		customize.add(changeBackground);
		customize.addSeparator();
		customize.add(lockToolbar);
	}
	
	private void createToolbar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(true);
		
		ButtonGroup group = new ButtonGroup();
		
		lines = new JRadioButton("lines");
		lines.setSelected(true);
		lines.addChangeListener(this);
		
		rectangles = new JRadioButton("rectanlges");
		rectangles.addChangeListener(this);
		
		fillRects = new JRadioButton("filled rectangles");
		fillRects.addChangeListener(this);
		
		ovals = new JRadioButton("ovals");
		ovals.addChangeListener(this);
		
		fillOvals = new JRadioButton("filled ovals");
		fillOvals.addChangeListener(this);

		group.add(lines);
		group.add(rectangles);
		group.add(ovals);
		group.add(fillRects);
		group.add(fillOvals);
		
		chooseColor = new JButton();
		chooseColor.setToolTipText("choose paint color");
		chooseColor.setIcon(pci = new PaintColorIcon());
		chooseColor.addActionListener(this);
		
		toolBar.add(chooseColor);
		toolBar.addSeparator();
		toolBar.add(lines);
		toolBar.add(rectangles);
		toolBar.add(fillRects);
		toolBar.add(ovals);
		toolBar.add(fillOvals);
		toolBar.addSeparator();
		
		createSizeSlider();
		toolBar.add(sizePicker);
	}
	
	private void createSizeSlider() {
		sizePicker = new JSlider(1, 10, 3);
		sizePicker.setPaintTicks(true);
		sizePicker.setMajorTickSpacing(1);
		sizePicker.setPaintLabels(true);
		sizePicker.setSnapToTicks(true);
		sizePicker.setMinorTickSpacing(1);
		sizePicker.addChangeListener(this);
	}
	
	/****************************************************************
	 * EVENT HANDLERS
	 ***************************************************************/
	
	/** 
	 * Listens for different buttons to be pressed. Allows for clearing
	 * of the canvas, displays an about dialog, allows for changing
	 * the paint color, and allows for saving of the doodle to an image
	 * file
	 * @param action the action that is performed
	 */
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == clear) {
			canvas.clear();
			canvas.repaint();
		} else if (action.getSource() == chooseColor) {
			colorChooser.setColor(canvas.getPaintColor());
			changingPaint = true;
			colorDialog.setVisible(true);
			pci.setColor(canvas.getPaintColor());
		} else if(action.getSource() == save) {
			chooser.showSaveDialog(this);
			File saveFile = chooser.getSelectedFile();
			if (saveFile != null && chooser.getFileFilter().accept(saveFile)) {
				String fileExtension = 
						FileUtilities.getExtension(saveFile.getName());
				canvas.save(saveFile, fileExtension);
			} else {
				System.err.println("not an acceptable format");
			}
		} else if (action.getSource() == undo) {
			canvas.undo();
		} else if (action.getSource() == redo) {
			canvas.redo();
		} else if (action.getSource() == exit) {
			System.exit(0);
		} else if (action.getSource() == cursor) {
			if (cursor.isSelected()) {
				int c = Cursor.DEFAULT_CURSOR;
				canvas.setPointer(c);
			}
		} else if (action.getSource() == crosshair) {
			if (crosshair.isSelected()) {
				int c = Cursor.CROSSHAIR_CURSOR;
				canvas.setPointer(c);
			}
		} else if (action.getSource() == pointer) {
			if (pointer.isSelected()) {
				int c = Cursor.HAND_CURSOR;
				canvas.setPointer(c);
			}
		} else if (action.getSource() == changeBackground) {
			colorChooser.setColor(canvas.getBackground());
			changingBackground = true;
			colorDialog.setVisible(true);
		} else {
			if (changingPaint) {
				canvas.setPaintColor(colorChooser.getColor());
				changingPaint = false;
			} else if (changingBackground) {
				canvas.setBackgroundColor(colorChooser.getColor());
				changingBackground = false;
			}
		}
	}
	
	/**
	 * Sets the size of the brush to the value of the slider
	 * @param e the type of change that occurs
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == lines) {
			canvas.setDrawLines(lines.isSelected());
		} else if (e.getSource() == rectangles) {
			canvas.setDrawRectangles(rectangles.isSelected());
		} else if (e.getSource() == ovals) {
			canvas.setDrawOvals(ovals.isSelected());
		} else if (e.getSource() == antialiasing) {
			canvas.setPretty(antialiasing.isSelected());
			canvas.redrawImage();
		} else if (e.getSource() == sizePicker) {
			if (!sizePicker.getValueIsAdjusting()) {
				int brushSize = sizePicker.getValue();
				if (brushSize == 0) {
					brushSize = 1;
				}
				canvas.setBrushSize(brushSize);
			}
		} else if (e.getSource() == lockToolbar) {
			toolBar.setFloatable(!lockToolbar.isSelected());
		} else if (e.getSource() == fillRects) {
			canvas.setFilledRects(fillRects.isSelected());
		} else if (e.getSource() == fillOvals) {
			canvas.setFilledOvals(fillOvals.isSelected());
		}
	}
	
	/****************************************************************
	 * HELPER CLASSES
	 ***************************************************************/

	/**
	 * The "paint color icon" is an icon who's background color mirrors
	 * that of the current paint color for the canvas
	 */
	private class PaintColorIcon implements Icon {

		private int width, height;
		private Color iconColor;

		/**
		 * Constructs a paint color icon of default width and height
		 */
		public PaintColorIcon() {
			iconColor = Color.BLACK;
			width = 35;
			height = 35;
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(iconColor);
			g2d.fillRect(x, y, width, height);

			g2d.dispose();
		}

		/**
		 * Sets the color of the icon to the given color
		 * @param paintColor the current color of the paint being used on the canvas
		 */
		public void setColor(Color paintColor) {
			iconColor = paintColor;
		}
	}
}