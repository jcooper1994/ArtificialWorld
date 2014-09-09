package uk.ac.reading.xj008217.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import uk.ac.reading.xj008217.WorldConfig;
import uk.ac.reading.xj008217.WorldPanel;
import uk.ac.reading.xj008217.WorldToolbar;
import uk.ac.reading.xj008217.utilities.ProgramConstants;

/**
 * Controls the top level of the program and displays panels.
 * @author Jon
 *
 */
public class GUI extends JFrame implements ActionListener{

	JPanel currentPanel = null;
	
	
	/**
	 * Initialise GUI and start main menu
	 */
	public GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setMinimumSize(new Dimension(ProgramConstants.SCREEN_WIDTH,ProgramConstants.SCREEN_HEIGHT));
		setVisible(true);
		displayHomeMenu();
	}
	
	/**
	 * Remove other panels and display the home menu.
	 */
	public void displayHomeMenu(){
		if(currentPanel != null)
			remove(currentPanel);
		
		currentPanel = new GUIPanel_Home(this);
		getContentPane().add(currentPanel,BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}
	/**
	 * Remove other panels and display the simulation menu.
	 */
	public void displaySimulationMenu(){
		if(currentPanel != null)
			remove(currentPanel);
		
		currentPanel = new GUIPanel_Simulation(this);//change
		getContentPane().add(currentPanel,BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}
	
	/**
	 * Remove other panels and display the configuration menu.
	 */
	public void displayConfigurationMenu(){
		if(currentPanel != null)
			remove(currentPanel);
		
		currentPanel = new GUIPanel_Configuration(this);//change
		getContentPane().add(currentPanel,BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}
	
	/**
	 * Remove other panels and display the about menu.
	 */
	public void displayAboutMenu(){
		if(currentPanel != null)
			remove(currentPanel);
		
		currentPanel = new GUIPanel_About(this);//change
		getContentPane().add(currentPanel,BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}
	/**
	 * Remove other panels and display the WorldPanel and run a simulation.
	 * @param config The WorldConfig object that contains the World parameters.
	 */
	public void displayWorld(WorldConfig config){
		getContentPane().removeAll();
		WorldPanel worldPanel = new WorldPanel(config);
		currentPanel = worldPanel;
		getContentPane().add(currentPanel,BorderLayout.CENTER);
		
		getContentPane().add(new WorldToolbar(worldPanel,this),BorderLayout.PAGE_START);
		
		getContentPane().repaint();
        validate();
	}
	
	/**
	 * Closes the program.
	 */
	public void exit(){
		dispose();
	}
	
	
	///////////standardised buttons//////////////
	
	/**
	 * Function that returns a JButton that will cause the gui to open the simulation menu.
	 * @return JButton that opens simulation menu when clicked.
	 */
	public JButton createSimulationButton(){
		JButton newButton = new JButton();
		newButton.setText("SIMULATION");
		newButton.setAlignmentX(CENTER_ALIGNMENT);

		
		newButton.addActionListener(this);
		newButton.setActionCommand("simulation");
		
		return newButton;
	}
	
	/**
	 * Function that returns a JButton that will cause the gui to open the configuration menu.
	 * @return JButton that opens configuration menu when clicked.
	 */
	public JButton createConfigButton(){
		JButton newButton = new JButton();
		newButton.setText("WORLD CONFIGURATIONS");
		newButton.setAlignmentX(CENTER_ALIGNMENT);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("configuration");
		
		return newButton;
	}
	
	/**
	 * Function that returns a JButton that will cause the gui to open the about menu.
	 * @return JButton that opens about menu when clicked.
	 */
	public JButton createAboutButton(){
		JButton newButton = new JButton();
		newButton.setText("HELP");
		newButton.setAlignmentX(CENTER_ALIGNMENT);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("help");
		
		return newButton;
	}
	
	/**
	 * Function that returns a JButton that will cause the gui to open the home menu.
	 * @return JButton that opens home menu when clicked.
	 */
	public JButton createHomeButton(){
		JButton newButton = new JButton();
		newButton.setText("HOME");
		newButton.setAlignmentX(CENTER_ALIGNMENT);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("home");
		
		return newButton;
	}
	
	/**
	 * Function that returns a JButton that will cause the program to exit.
	 * @return JButton that exits the program when clicked.
	 */
	public JButton createExitButton(){
		JButton newButton = new JButton();
		newButton.setText("EXIT");
		newButton.setAlignmentX(CENTER_ALIGNMENT);
		
		newButton.addActionListener(this);
		newButton.setActionCommand("exit");
		
		return newButton;
	}

	/**
	 * Listens for actions performed by buttons on the GUI.
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if("simulation".equals(e.getActionCommand())){
			displaySimulationMenu();
		}else if("configuration".equals(e.getActionCommand())){
			displayConfigurationMenu();
		}else if("help".equals(e.getActionCommand())){
			displayAboutMenu();
		}else if("home".equals(e.getActionCommand())){
			displayHomeMenu();
		}else if("exit".equals(e.getActionCommand())){
			exit();
		}
	}
	
}
