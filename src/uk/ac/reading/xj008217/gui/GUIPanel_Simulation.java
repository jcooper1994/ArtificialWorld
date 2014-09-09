package uk.ac.reading.xj008217.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import uk.ac.reading.xj008217.WorldConfig;

/**
 * The simulation menu.
 * @author Jon
 *
 */
public class GUIPanel_Simulation extends GUIPanel implements ActionListener{
	
	private static final long serialVersionUID = 687686772464407573L;
	
	protected GUI gui = null;

	private WorldConfig config = new WorldConfig();
	private List<String> configFiles = new ArrayList<String>();
	
	private JComboBox<String> configDropDown;
	
	/**
	 * Adds buttons, combo boxes as necessary.
	 * @param gui The GUI that this panel exists on.
	 */
	public GUIPanel_Simulation(GUI gui){
		super(gui);
		this.gui = gui;
		JLabel title = new JLabel("Simulation<br/>create new configurations in the configuration menu!");
		
		subPanel.setLayout(new GridBagLayout());

		configFiles = config.findValidConfigFiles();
		//trim the file extension from the properties files
		for(int i = 0; i < configFiles.size(); ++i){
			configFiles.set(i, configFiles.get(i).substring(0, configFiles.get(i).lastIndexOf('.')));
		}
		
		if(configFiles.size() > 0){
		configDropDown = new JComboBox(configFiles.toArray());
		configDropDown.setSize(100,100);
		
		subPanel.add(configDropDown);
		subPanel.add(createStartWorldButton());
		}else{
			JLabel message = new JLabel("No world configurations exist! Please go to the configuration menu and create one.");
			subPanel.add(message);
		}
		subPanel.add(gui.createHomeButton());

		repaint();
	}

	/**
	 * Starts the world with the currently selected world configuration properties file.
	 */
	public void startWorld(){
		String fileName = String.valueOf(configDropDown.getSelectedItem());
		
		if(config.loadWorldConfig(fileName)){
			SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		                JOptionPane.showMessageDialog(null,"Remember that you can click and drag on the world to pan!");
		        }
		    });
			gui.displayWorld(config);
			
		}else{
			SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		                JOptionPane.showMessageDialog(null,"The configuration file " + String.valueOf(configDropDown.getSelectedItem()) + " did not load correctly. Please check that it exists.");
		        }
		    });
		}

	}
	
	/**
	 * Creates and returns a JButton that starts the world.
	 * @return JButton that starts the world when clicked.
	 */
	public JButton createStartWorldButton(){
		JButton newButton = new JButton();
		newButton.setText("Start World");
		
		newButton.addActionListener(this);
		newButton.setActionCommand("start");
		
		return newButton;
	}


	/**
	 * Listen for events fired by buttons on this panel.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("start")){
			startWorld();
		}
		
	}
	

	
}