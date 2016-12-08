package utilities;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Class <code>FileUtilities</code> contains a set of static methods
 * that are used for easily manipulating files for the painting
 * application--specifically for saving.
 * 
 * @version 0.1.1 [7/17/15]
 * 
 * @author Michael
 *
 */

/*
 * VERSION NOTES:
 * 		-- first created :)
 */
public class FileUtilities {
	public static final String PNG = "png";
	public static final String JPEG = "jpeg";
	public static final String JPG = "jpg";
	public static final PngFilter PNG_FILTER = new PngFilter();
	public static final JpgFilter JPG_FILTER = new JpgFilter();
	public static final JpegFilter JPEG_FILTER = new JpegFilter();
	
	/**
	 * Gets a list of all of the accepted extensions for this file
	 * filter.
	 * @return a list of all accepted file extensions
	 */
	public static String[] getExtensions() {
		String[] extensions = {PNG, JPEG, JPG};
		return extensions;
	}
	
	/**
	 * Gets the extension of the given file
	 * @param fileName the name of the file
	 * @return the extension of the given file or and empty string if
	 * no extension is in the file name
	 */
	public static String getExtension(String fileName) {
		String extension = "";
		int index = fileName.lastIndexOf('.');
		if (index > 0 && index < fileName.length() - 1) {
			extension = fileName.substring(index + 1).toLowerCase();
		}
		return extension;
	}
	
	// a filter for png file types
	private static class PngFilter extends FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			String ext = FileUtilities.getExtension(file.getName());
			return ext.equals(PNG);
		}

		@Override
		public String getDescription() {
			return "*." + PNG;
		}
		
	}
	
	// a filter for jpg file types
	private static class JpgFilter extends FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			String ext = FileUtilities.getExtension(file.getName());
			return ext.equals(JPG);
		}

		@Override
		public String getDescription() {
			return "*." + JPG;
		}
		
	}
	
	// a filter for jpeg files types
	private static class JpegFilter extends FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			String ext = FileUtilities.getExtension(file.getName());
			return ext.equals(JPEG);
		}

		@Override
		public String getDescription() {
			return "*." + JPEG;
		}
		
	}
}
