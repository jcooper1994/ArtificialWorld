package uk.ac.reading.xj008217.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.border.EmptyBorder;

import uk.ac.reading.xj008217.utilities.Images;

/**
 * A menu panel - used to create generic menus.
 * 
 * @author xj008217
 *
 */
public class GUIPanel extends JPanel{
	
	private static final long serialVersionUID = 687686772464407573L;
	
	protected GUI gui = null;
	protected JPanel subPanel = null;

	/**
	 * Adds layouts, sub JPanels, a hint and repaints the panel. 
	 * @param gui The gui that this panel exists on.
	 */
	public GUIPanel(GUI gui){
		this.gui = gui;
		
		
		setBackground(new Color(255,255,255));
		setLayout(new BorderLayout());
		
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		addHeader();
		
		subPanel = new JPanel();
		
		add(subPanel,BorderLayout.CENTER);
		
		addHint();
		repaint();
	}
	/**
	 * Adds the banner header to the JPanel
	 */
	private void addHeader(){
		JLabel newLabel = new JLabel();
		newLabel.setIcon(new ImageIcon(Images.GUI_HOME_HEADER));
		newLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(newLabel,BorderLayout.PAGE_START);
	}
	
	/**
	 * Adds a hint JLabel to the JPanel.
	 */
	private void addHint(){
		GUI_HintLabel newLabel = new GUI_HintLabel();
		newLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(newLabel,BorderLayout.PAGE_END);
	}

	

	
}