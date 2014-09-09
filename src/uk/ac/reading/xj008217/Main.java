package uk.ac.reading.xj008217;

import uk.ac.reading.xj008217.gui.GUI;
import uk.ac.reading.xj008217.utilities.Images;
/**
 * Main class - entry point for the application.
 * @author xj008217
 *
 */
public class Main {

	/**
	 * Main function - loads images and then creates a new GUI object that handles
	 * the rest of the application.
	 * @param args
	 */
	public static void main(String [] args){
		Images.loadImages();
		GUI gui = new GUI();
	}
 }