package uk.ac.reading.xj008217.utilities;

import java.util.Map;
import java.util.EnumMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Boolean;

import javax.imageio.ImageIO;
/**
 * static class holding values that must be constant across the whole program.
 * 
 * @author xj008217
 *
 */
public class ProgramConstants {
	
	public final static int SCREEN_WIDTH = 1000;
	public final static int SCREEN_HEIGHT = 800;
	
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_WIDTH = 16;
	
	public final static int UPDATE_TIME = 25;
	
	public final static String FILE_PREFIX = "AW_";
	
	public final static boolean DEBUG_MODE = false;
	
	public final static long PAUSE_SLEEP_TIME = 50;
	
}
