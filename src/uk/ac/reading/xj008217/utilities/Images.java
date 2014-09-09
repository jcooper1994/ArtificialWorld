package uk.ac.reading.xj008217.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Loads and stores static variables for all images used by the program
 * Loading the images into memory in one place prevents repeatedly loading images from the computer.
 */
public class Images { 
	
	// image variable definitions:
	public static BufferedImage GRASS_POISONED;
	public static BufferedImage GRASS_LOW;
	public static BufferedImage GRASS;
	public static BufferedImage GRASS_HIGH;
	public static BufferedImage GRASS_VERY_HIGH;
	
	public static BufferedImage FOREST;
	public static BufferedImage FOREST_VERY_THIN;
	public static BufferedImage FOREST_THIN;
	public static BufferedImage FOREST_MEDIUM;
	public static BufferedImage FOREST_DENSE;
	
	public static BufferedImage MOUNTAIN;
	
	public static BufferedImage RIVER;
	public static BufferedImage RIVER_VERTICAL;
	public static BufferedImage RIVER_HORIZONTAL;
	public static BufferedImage RIVER_N;
	public static BufferedImage RIVER_E;
	public static BufferedImage RIVER_S;
	public static BufferedImage RIVER_W;
	public static BufferedImage RIVER_N_E;
	public static BufferedImage RIVER_E_S;
	public static BufferedImage RIVER_S_W;
	public static BufferedImage RIVER_W_N;
	public static BufferedImage RIVER_N_E_S;
	public static BufferedImage RIVER_E_S_W;
	public static BufferedImage RIVER_S_W_N;
	public static BufferedImage RIVER_W_N_E;
	public static BufferedImage RIVER_N_E_S_W;
	
	public static BufferedImage BORDER;

	public static BufferedImage DEBUG_SQUARE;
	
	//life
	public static BufferedImage[] SHEEP = {null,null,null,null,null,null,null,null};
	public static BufferedImage SHEEP_DEAD;
		
	public static BufferedImage[] WOLF = {null,null,null,null,null,null,null,null};
	public static BufferedImage WOLF_DEAD;
	
	//animated effects
	public static BufferedImage[] BLOOD_SPLASH = {null,null,null,null,null,null,null,null,null};
	
	
	//gui images
	public static BufferedImage GUI_HOME_HEADER;
	public static BufferedImage GUI_BUTTON_BACK;
	
	//world toolbar images
	public static BufferedImage TOOLBAR_HOME;
	public static BufferedImage TOOLBAR_PLAY;
	public static BufferedImage TOOLBAR_PAUSE;
	
	// static block allows variables to be manually loaded
	public static final void loadImages(){
		try {
			//tiles
			GRASS_POISONED = ImageIO.read(Images.class.getResource("img/world/grass_poisoned.png"));
			GRASS_LOW = ImageIO.read(Images.class.getResource("img/world/grass_low.png"));
			GRASS = ImageIO.read(Images.class.getResource("img/world/grass.png"));
			GRASS_HIGH = ImageIO.read(Images.class.getResource("img/world/grass_high.png"));
			GRASS_VERY_HIGH = ImageIO.read(Images.class.getResource("img/world/grass_very_high.png"));
			
			FOREST = ImageIO.read(Images.class.getResource("img/world/forest.png"));
			FOREST_VERY_THIN = ImageIO.read(Images.class.getResource("img/world/forest_very_thin.png"));
			FOREST_THIN = ImageIO.read(Images.class.getResource("img/world/forest_thin.png"));
			FOREST_MEDIUM = ImageIO.read(Images.class.getResource("img/world/forest_medium.png"));
			FOREST_DENSE = ImageIO.read(Images.class.getResource("img/world/forest_dense.png"));
			
			MOUNTAIN = ImageIO.read(Images.class.getResource("img/world/mountain.png"));
			
			RIVER = ImageIO.read(Images.class.getResource("img/world/river.png"));
			RIVER_VERTICAL = ImageIO.read(Images.class.getResource("img/world/river_vertical.png"));
			RIVER_HORIZONTAL = ImageIO.read(Images.class.getResource("img/world/river_horizontal.png"));
			RIVER_N = ImageIO.read(Images.class.getResource("img/world/river_N.png"));
			RIVER_E = ImageIO.read(Images.class.getResource("img/world/river_E.png"));
			RIVER_S = ImageIO.read(Images.class.getResource("img/world/river_S.png"));
			RIVER_W = ImageIO.read(Images.class.getResource("img/world/river_W.png"));
			RIVER_N_E = ImageIO.read(Images.class.getResource("img/world/river_N_E.png"));
			RIVER_E_S = ImageIO.read(Images.class.getResource("img/world/river_E_S.png"));
			RIVER_S_W = ImageIO.read(Images.class.getResource("img/world/river_S_W.png"));
			RIVER_W_N = ImageIO.read(Images.class.getResource("img/world/river_W_N.png"));
			RIVER_N_E_S = ImageIO.read(Images.class.getResource("img/world/river_N_E_S.png"));
			RIVER_E_S_W = ImageIO.read(Images.class.getResource("img/world/river_E_S_W.png"));
			RIVER_S_W_N = ImageIO.read(Images.class.getResource("img/world/river_S_W_N.png"));
			RIVER_W_N_E = ImageIO.read(Images.class.getResource("img/world/river_W_N_E.png"));
			RIVER_N_E_S_W = ImageIO.read(Images.class.getResource("img/world/river_N_E_S_W.png"));
			
			BORDER = ImageIO.read(Images.class.getResource("img/world/border.png"));
	
			//entities
			//life forms can move and so are arrays of images (for different directions).
			SHEEP[0] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_N.png"));
			SHEEP[1] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_N_E.png"));
			SHEEP[2] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_E.png"));
			SHEEP[3] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_E_S.png"));
			SHEEP[4] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_S.png"));
			SHEEP[5] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_S_W.png"));
			SHEEP[6] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_W.png"));
			SHEEP[7] = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_W_N.png"));
			SHEEP_DEAD = ImageIO.read(Images.class.getResource("img/life/sheep/sheep_dead.png"));
			
			WOLF[0] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_N.png"));
			WOLF[1] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_N_E.png"));
			WOLF[2] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_S_W.png"));
			WOLF[3] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_E_S.png"));
			WOLF[4] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_S_W.png"));
			WOLF[5] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_S_W.png"));
			WOLF[6] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_W.png"));
			WOLF[7] = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_W_N.png"));
			
			WOLF_DEAD = ImageIO.read(Images.class.getResource("img/life/wolf/wolf_dead.png"));
			
			
			//Animation images:
			BLOOD_SPLASH[0] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_0.png"));
			BLOOD_SPLASH[1] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_1.png"));
			BLOOD_SPLASH[2] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_2.png"));
			BLOOD_SPLASH[3] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_3.png"));
			BLOOD_SPLASH[4] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_4.png"));
			BLOOD_SPLASH[5] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_5.png"));
			BLOOD_SPLASH[6] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_6.png"));
			BLOOD_SPLASH[7] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_7.png"));
			BLOOD_SPLASH[8] = ImageIO.read(Images.class.getResource("img/effects/blood_splash/blood_8.png"));

			
			
			//GUI images:
			GUI_BUTTON_BACK = ImageIO.read(Images.class.getResource("img/GUI/gui_button_back.png"));
			GUI_HOME_HEADER = ImageIO.read(Images.class.getResource("img/GUI/gui_home_header.png"));
			
			//toolbar images:
			TOOLBAR_HOME = ImageIO.read(Images.class.getResource("img/GUI/toolbar/home.png"));
			TOOLBAR_PLAY = ImageIO.read(Images.class.getResource("img/GUI/toolbar/play.png"));
			TOOLBAR_PAUSE = ImageIO.read(Images.class.getResource("img/GUI/toolbar/pause.png"));
			
			//debug images:
			DEBUG_SQUARE = ImageIO.read(Images.class.getResource("img/world/debugSquare.png"));
			
		}	catch (IOException e) {
			System.out.print("Image did not load correctly");
			e.printStackTrace();
		}
	}
}
