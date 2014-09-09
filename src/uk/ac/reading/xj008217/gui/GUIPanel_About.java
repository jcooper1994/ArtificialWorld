package uk.ac.reading.xj008217.gui;

import javax.swing.JLabel;

/**
 * The about menu.
 * @author Jon
 *
 */
public class GUIPanel_About extends GUIPanel{
	
	private static final long serialVersionUID = 687686772464407573L;
	
	protected GUI gui = null;

	String aboutText = "<html>"
			+ "<body>"
			+ "This program has been created by Jonathan Cooper as part of a university project.<br/>"
			+ "This build is intended for download from www.jcgamedev.com<br/><br/><br/>"
			+ "NOTE:<br/>"
			+ "This project was finished under tight time constraints and as such there will probably<br/>"
			+ "be bugs and ways to break the program.<br/>"
			+ "</body>"
			+ "</html>";
	
	/**
	 * Adds buttons and labels as necessary. 
	 * @param gui The gui that this panel exists on.
	 */
	public GUIPanel_About(GUI gui){
		super(gui);

		
		JLabel aboutLabel = new JLabel(aboutText);
		aboutLabel.setAlignmentX(CENTER_ALIGNMENT);
		subPanel.add(aboutLabel);
		
		
		subPanel.add(gui.createHomeButton());

		repaint();
	}


	

	
}