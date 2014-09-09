package uk.ac.reading.xj008217.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * The home menu
 * @author Jon
 *
 */
public class GUIPanel_Home extends GUIPanel{
	
	private static final long serialVersionUID = 687686772464407573L;
	
	protected GUI gui = null;

	/**
	 * Adds labels and buttons as neccesary
	 * @param gui The gui that this panel exists on.
	 */
	public GUIPanel_Home(GUI gui){
		super(gui);
		JLabel title = new JLabel("Home");
		title.setAlignmentX(CENTER_ALIGNMENT);
		subPanel.add(title);
		subPanel.add(gui.createSimulationButton());
		subPanel.add(gui.createConfigButton());
		subPanel.add(gui.createAboutButton());
		subPanel.add(gui.createExitButton());

		repaint();
	}
}