package uk.ac.reading.xj008217;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import uk.ac.reading.xj008217.gui.GUI;
import uk.ac.reading.xj008217.utilities.Images;

/**
 * Displays buttons that play pause and return to the home screen.
 * 
 * @author xj008217
 *
 */
public class WorldToolbar extends JPanel implements ActionListener{

	private static final long serialVersionUID = -2765312099387854940L;
	
	private WorldPanel worldPanel = null;
	private GUI gui = null;
	
	/**
	 * Creates a new toolbar, including pause play and home buttons.
	 * @param worldPanel The worldpanel that the play and pause buttons refer to
	 * @param gui The gui that the home button refers to.
	 */
	public WorldToolbar(WorldPanel worldPanel,GUI gui){
		this.worldPanel = worldPanel;
		this.gui = gui;
		addButtons();
	}
	/**
	 * Adds all buttons to the toolbar
	 */
	private void addButtons(){
		add(createHomeButton());
		add(createPlayButton());
		add(createPauseButton());
	}

	/**
	 * Returns a correctly formatted play button
	 */
	public JButton createPlayButton(){
		ImageIcon playIcon = new ImageIcon(Images.TOOLBAR_PLAY);
		JButton newButton = new JButton();
		newButton.setIcon(playIcon);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("play");
		
		return newButton;
	}
	
	/**
	 * Returns a correctly formatted pause button
	 */
	public JButton createPauseButton(){
		ImageIcon pauseIcon = new ImageIcon(Images.TOOLBAR_PAUSE);
		JButton newButton = new JButton();
		newButton.setIcon(pauseIcon);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("pause");
		
		return newButton;
	}
	
	/**
	 * Returns a correctly formatted home button
	 */
	public JButton createHomeButton(){
		ImageIcon homeIcon = new ImageIcon(Images.TOOLBAR_HOME);
		JButton newButton = new JButton();
		newButton.setIcon(homeIcon);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("home");
		
		return newButton;
	}


	/**
	 * Handles events fired from buttons on this JPanel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("play")){
			worldPanel.getWorld().play();
		}else if(e.getActionCommand().equals("pause")){
			worldPanel.getWorld().pause();
		}else if(e.getActionCommand().equals("home")){
			gui.getContentPane().remove(this);
			gui.displayHomeMenu();
		}
		
	}
	
}
