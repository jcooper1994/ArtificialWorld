package uk.ac.reading.xj008217.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import uk.ac.reading.xj008217.WorldConfig;
import uk.ac.reading.xj008217.utilities.Images;

/**
 * The configuration menu
 * @author Jon
 *
 */
public class GUIPanel_Configuration extends GUIPanel implements ActionListener{
	
	private static final long serialVersionUID = 687686772464407573L;
	
	protected GUI gui = null;
	private WorldConfig worldConfig = new WorldConfig();
	
	
	private JSlider worldWidthSlider = new JSlider(100,200,150);
	private JSlider worldHeightSlider = new JSlider(100,200,150);
	private JSpinner riverCountSpinner = new JSpinner();
	private JSlider riverVarianceSlider = new JSlider(0,60,30);
	private JSlider forestDensitySlider = new JSlider(0,3,2);
	private JSlider forestThicknessSlider = new JSlider(20,60,50);
	private JSlider forestSizeSlider = new JSlider(3,7,5);
	private JCheckBox sheepCheckbox = new JCheckBox();
	private JSlider sheepHerdCountSlider = new JSlider(4,25,15);
	private JSlider sheepHerdSizeSlider = new JSlider(4,15,8);
	private JCheckBox wolfCheckbox = new JCheckBox();
	private JSlider wolfCountSlider = new JSlider(5,30,15);
	
	
	
	private JTextField fileNameField = new JTextField();
	
	/**
	 * Adds buttons,labels,sliders as necessary. Uses a gridbaglayout to correctly position elements.
	 * @param gui The GUI that this panel exists on.
	 */
	public GUIPanel_Configuration(GUI gui){
		super(gui);
		
		subPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		subPanel.setBorder(new EmptyBorder(25,25,25,25));
		//set defaults
		sheepCheckbox.setSelected(true);
		wolfCheckbox.setSelected(true);
		riverCountSpinner.setValue(1);
		
		
		//River section//////////////////////////////////////////////////////////////////
		JLabel riverImageVertical = new JLabel(new ImageIcon(Images.RIVER_VERTICAL));
		JLabel riverImageHorizontal = new JLabel(new ImageIcon(Images.RIVER_HORIZONTAL));
		JLabel riverTitle = new JLabel("River Information");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		subPanel.add(riverImageVertical,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		
		subPanel.add(riverTitle,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		
		subPanel.add(riverImageHorizontal,c);
		
		
		
		//river count
		JLabel riverCountLabel = new JLabel("How many rivers are there?");
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		
		subPanel.add(riverCountLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		
		subPanel.add(riverCountSpinner,c);
		
		//river variance
		JLabel riverVarianceLabel = new JLabel("How bendy are the rivers?");
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		
		subPanel.add(riverVarianceLabel,c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		
		subPanel.add(riverVarianceSlider,c);
		
		///////////////////////////////////////////////////////////////////////////////
		
		//Forest Section///////////////////////////////////////////////////////////////
		
		//forest title panel
		JLabel forestImageLeft = new JLabel(new ImageIcon(Images.FOREST_DENSE));
		JLabel forestImageRight = new JLabel(new ImageIcon(Images.FOREST_DENSE));
		JLabel forestTitle = new JLabel("Forest Information");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		
		subPanel.add(forestImageLeft,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		
		subPanel.add(forestTitle,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		
		subPanel.add(forestImageRight,c);
		
		//forest density
		JLabel forestCountLabel = new JLabel("How many forests are there?");
		forestCountLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		
		subPanel.add(forestCountLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		
		subPanel.add(forestDensitySlider,c);
		
		//forest thickness
		JLabel forestThicknessLabel = new JLabel("How thick are the forests?");
		forestThicknessLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		
		subPanel.add(forestThicknessLabel,c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		
		subPanel.add(forestThicknessSlider,c);
			
		
		//forest size
		
		
		JLabel forestSizeLabel = new JLabel("How large are the forests?");
		forestSizeLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		
		subPanel.add(forestSizeLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		
		subPanel.add(forestSizeSlider,c);
		////////////////////////////////////////////////////////////////////////////////

		//Life Section ////////////////////////////////////////////////////////////////
		
		//sheep
		JLabel sheepLabel = new JLabel("Do sheep exist?");
		sheepLabel.setIcon(new ImageIcon(Images.SHEEP[1]));
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		subPanel.add(sheepLabel,c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 7;
		subPanel.add(sheepCheckbox,c);
		
		
		JLabel herdSizeLabel = new JLabel("How big are the herds?");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 8;
		subPanel.add(herdSizeLabel,c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 8;
		subPanel.add(sheepHerdSizeSlider,c);
		
		
		JLabel herdCountLabel = new JLabel("How many herds are there?");
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 9;
		subPanel.add(herdCountLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 9;
		subPanel.add(sheepHerdCountSlider,c);
		
		
		JLabel wolfLabel = new JLabel("Do wolves exist?");
		wolfLabel.setIcon(new ImageIcon(Images.WOLF[1]));
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		subPanel.add(wolfLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 10;
		subPanel.add(wolfCheckbox,c);
		JLabel wolfCountLabel = new JLabel("How many wolves are there?");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 11;
		subPanel.add(wolfCountLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 11;
		subPanel.add(wolfCountSlider,c);
		
		JLabel fileNameLabel = new JLabel("File Name:");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 12;
		subPanel.add(fileNameLabel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 12;
		subPanel.add(fileNameField,c);
		
		JButton saveButton = createSaveButton();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; 
		c.gridy = 13;
		subPanel.add(saveButton,c);
		
		JButton loadButton = createLoadButton();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 14;
		
		subPanel.add(loadButton,c);
		
		JButton homeButton = gui.createHomeButton();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 15;
		subPanel.add(homeButton,c);

		
		repaint();
	}
	
	
	public void saveWorldConfig(String fileName){
		//if file name is null or empty
		if(fileName == null || fileName.equals("")){
			//throw the user a message on the event dispatch thread
			
			SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		                JOptionPane.showMessageDialog(null,"Please enter a name for the file.");
		        }
		    });
			
		}else{
			//set all config values
			worldConfig.setWidth(worldWidthSlider.getValue());
			worldConfig.setHeight(worldHeightSlider.getValue());
			
			int riverCountVal = (int) riverCountSpinner.getValue();
			
			if(riverCountVal < 0){
				SwingUtilities.invokeLater(new Runnable() {
			        @Override
			        public void run() {
			                JOptionPane.showMessageDialog(null,"River count cannot be negative.");
			        }
			    });
				return;
			}else{
				worldConfig.setRiverCount(riverCountVal);
			}

			worldConfig.setRiverVariance(riverVarianceSlider.getValue());
			
			worldConfig.setForestDensity(forestDensitySlider.getValue());
			worldConfig.setForestThickness(forestThicknessSlider.getValue());
			worldConfig.setForestSize(forestSizeSlider.getValue());
			
			worldConfig.setHasSheep(sheepCheckbox.isSelected());
			worldConfig.setSheepHerds(sheepHerdCountSlider.getValue());
			worldConfig.setSheepHerdSize(sheepHerdSizeSlider.getValue());
			
			worldConfig.setHasWolves(wolfCheckbox.isSelected());
			worldConfig.setWolfCount(wolfCountSlider.getValue());
			
			if(worldConfig.saveWorldConfig(fileName)){
				
				SwingUtilities.invokeLater(new Runnable() {
			        @Override
			        public void run() {
			                JOptionPane.showMessageDialog(null,"File saved as \""+ fileNameField.getText() +"\"");
			        }
			    });
				
			}else{
				SwingUtilities.invokeLater(new Runnable() {
			        @Override
			        public void run() {
			                JOptionPane.showMessageDialog(null,"Something unexpected happened! Save failed.");
			        }
			    });
			}
			
		}
	}
	
	public void loadWorldConfig(String fileName){
		if(!worldConfig.loadWorldConfig(fileName)){
			
			SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		                JOptionPane.showMessageDialog(null,"The file did not load correctly. Please check that it exists");
		        }
		    });
			
			return;
			
		}
	
		
		worldWidthSlider.setValue(worldConfig.getWidth());
		worldHeightSlider.setValue(worldConfig.getHeight());
		
		riverCountSpinner.setValue(worldConfig.getRiverCount());
		riverVarianceSlider.setValue(worldConfig.getRiverVariance());
		
		forestDensitySlider.setValue(worldConfig.getForestDensity());
		forestThicknessSlider.setValue(worldConfig.getForestThickness());
		forestSizeSlider.setValue(worldConfig.getForestSize());
		
		sheepCheckbox.setSelected(worldConfig.hasSheep());
		sheepHerdCountSlider.setValue(worldConfig.getSheepHerds());
		sheepHerdSizeSlider.setValue(worldConfig.getSheepHerdSize());
		
		wolfCheckbox.setSelected(worldConfig.hasWolves());
		wolfCountSlider.setValue(worldConfig.getWolfCount());
	}


	public JButton createSaveButton(){
		JButton newButton = new JButton();
		newButton.setText("SAVE");
		
		newButton.addActionListener(this);
		newButton.setActionCommand("save");
		
		return newButton;
	}
	
	public JButton createLoadButton(){
		JButton newButton = new JButton();
		newButton.setText("LOAD");
		
		newButton.addActionListener(this);
		newButton.setActionCommand("load");
		
		return newButton;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("save")){
			saveWorldConfig(fileNameField.getText());
		}else if(e.getActionCommand().equals("load")){
			loadWorldConfig(fileNameField.getText());
		}
		
	}

	
}