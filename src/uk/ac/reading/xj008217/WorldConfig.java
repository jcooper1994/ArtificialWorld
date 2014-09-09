/**
 * 
 */
package uk.ac.reading.xj008217;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import uk.ac.reading.xj008217.utilities.ProgramConstants;

/**
 * Represents a world configuration. Stores attributes related to the way the world generates.
 * Responsible for loading and saving configuration (properties) files.
 * @author xj008217
 */
public class WorldConfig {
		// world dimensions
		private int width = 150;
		private int height = 150;
		
		private int grassPoisonChance = 50;
		
		//values that affect world generation.
		//number of rivers
		private int riverCount = 1;
		//how erratic the rivers are from 0 to 100. Higher number means more twists/turns.
		private int riverVariance = 30;

		//forest density from 0-1000. Higher number means more forests NUMBERS FROMser 0-5 RECOMMENDED
		private int forestDensity = 2;
		//relative thickness of forests from 0-100. A higher number means thicker/more uniform forests.
		private int forestThickness = 50;
		//number of iterations to forest size. A higher number means larger 
		private int forestSize = 5;
		
		private int forestThicknessSquareSize = 1;
	    private int[] forestThicknessThreshold = {0,1,2,3};
		
		//life form information
		private boolean hasSheep = true;
		private int sheepHerds = 15; //number of herds
		private int sheepHerdSize = 8; //average herd size
		private int sheepHerdVariance = 4; // maximum difference between average and actual herd size
		
		private int sheepHerdSpacing = 4;

		private boolean hasWolves = true;
		private int wolfCount = 15;
		
		private List<String> validConfigFiles;
		
		/**
		 * Creates a new WorldConfig object with default values.
		 * Finds all valid config files in the user space.
		 */
		public WorldConfig(){
			validConfigFiles = findValidConfigFiles();
		}
		
		/**
		 * Loads a world config as specified by file name.
		 * @param fileName The name of the file to be loaded.
		 * @return true if the file loads succesfully, false otherwise.
		 */
		public boolean loadWorldConfig(String fileName){
			Properties properties = new Properties();
			InputStream input = null;
			
			try {
				input = new FileInputStream(fileName + ".properties");
				properties.load(input);
					
				width = new Integer(properties.getProperty("width"));
				height = new Integer(properties.getProperty("height"));
				riverCount = new Integer(properties.getProperty("riverCount"));
				riverVariance = new Integer(properties.getProperty("riverVariance"));
				forestDensity = new Integer(properties.getProperty("forestDensity"));
				forestThickness = new Integer(properties.getProperty("forestThickness"));
				forestSize = new Integer(properties.getProperty("forestSize"));
				
				hasSheep = new Boolean(properties.getProperty("hasSheep"));
				sheepHerds = new Integer(properties.getProperty("sheepHerds"));
				sheepHerdSize = new Integer(properties.getProperty("sheepHerdSize"));
				
				hasWolves = new Boolean(properties.getProperty("hasWolves"));
				wolfCount = new Integer(properties.getProperty("wolfCount"));
				return true;
				
			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
		}
		
		/**
		 * Saves the current config values to a file.
		 * @param fileName The name of the file to be saved.
		 * @return true if the save is succesful, false otherwise.
		 */
		public boolean saveWorldConfig(String fileName){
			Properties properties = new Properties();
			
			try {
				
				properties.setProperty("width",""+width);
				properties.setProperty("height",""+height); 
				
				properties.setProperty("riverCount",""+riverCount);
				properties.setProperty("riverVariance",""+riverVariance);
				
				properties.setProperty("forestDensity",""+forestDensity);
				properties.setProperty("forestThickness",""+forestThickness);
				properties.setProperty("forestSize",""+forestSize);
				
				properties.setProperty("hasSheep",""+hasSheep);
				properties.setProperty("sheepHerds",""+sheepHerds);
				properties.setProperty("sheepHerdSize",""+sheepHerdSize);

				properties.setProperty("hasWolves",""+hasWolves);
				properties.setProperty("wolfCount",""+wolfCount);
				
				properties.store(new FileOutputStream(fileName + ".properties"), null);
				return true;
			} catch (IOException e) {
				return false;	
			}
		}
		
		/**
		 * Searches the users directory for valid configuration files.
		 * @return A list of strings representing the config file names.
		 */
		public List<String> findValidConfigFiles(){
			
			List<String> files = new ArrayList<String>();
			
			File file = new File(System.getProperty("user.dir"));
			
			String[] filesAndPaths = file.list();
			
			for(String path:filesAndPaths)
	         {
				if(path.endsWith(".properties")){
					//if the file is a .properties file then add it to the files list.
					files.add(path);
				}
	            
	         }
			
			return files;
		}
		
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public int getRiverCount() {
			return riverCount;
		}
		public void setRiverCount(int riverCount) {
			this.riverCount = riverCount;
		}
		public int getRiverVariance() {
			return riverVariance;
		}
		public void setRiverVariance(int riverVariance) {
			this.riverVariance = riverVariance;
		}
		public int getForestDensity() {
			return forestDensity;
		}
		public void setForestDensity(int forestDensity) {
			this.forestDensity = forestDensity;
		}
		public int getForestThickness() {
			return forestThickness;
		}
		public void setForestThickness(int forestThickness) {
			this.forestThickness = forestThickness;
		}
		public int getForestSize() {
			return forestSize;
		}
		public void setForestSize(int forestSize) {
			this.forestSize = forestSize;
		}
		public boolean hasSheep() {
			return hasSheep;
		}
		public void setHasSheep(boolean hasSheep) {
			this.hasSheep = hasSheep;
		}
		public int getSheepHerds() {
			return sheepHerds;
		}
		public void setSheepHerds(int sheepHerds) {
			this.sheepHerds = sheepHerds;
		}
		public int getSheepHerdSize() {
			return sheepHerdSize;
		}
		public void setSheepHerdSize(int sheepHerdSize) {
			this.sheepHerdSize = sheepHerdSize;
		}
		public int getSheepHerdVariance() {
			return sheepHerdVariance;
		}
		public void setSheepHerdVariance(int sheepHerdVariance) {
			this.sheepHerdVariance = sheepHerdVariance;
		}
		public boolean hasWolves() {
			return hasWolves;
		}
		public void setHasWolves(boolean hasWolves) {
			this.hasWolves = hasWolves;
		}
		public int getWolfCount() {
			return wolfCount;
		}
		public void setWolfCount(int wolfCount) {
			this.wolfCount = wolfCount;
		}
		public int getGrassPoisonChance() {
			return grassPoisonChance;
		}
		public void setGrassPoisonChance(int grassPoisonChance) {
			this.grassPoisonChance = grassPoisonChance;
		}
		public int getSheepHerdSpacing() {
			return sheepHerdSpacing;
		}
		public void setSheepHerdSpacing(int sheepHerdSpacing) {
			this.sheepHerdSpacing = sheepHerdSpacing;
		}
		public int getForestThicknessSquareSize() {
			return forestThicknessSquareSize;
		}
		public void setForestThicknessSquareSize(int forestThicknessSquareSize) {
			this.forestThicknessSquareSize = forestThicknessSquareSize;
		}
		public int[] getForestThicknessThreshold() {
			return forestThicknessThreshold;
		}
		public void setForestThicknessThreshold(int[] forestThicknessThreshold) {
			this.forestThicknessThreshold = forestThicknessThreshold;
		}
}
