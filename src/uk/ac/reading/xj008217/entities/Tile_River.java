package uk.ac.reading.xj008217.entities;

import java.awt.image.BufferedImage;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.Images;


/**
 * The river tile creates impassable barriers between sections of the simulation, and is generated in a 
 * way that looks and acts like a real river. The rivers also cause grass nearby to grow faster.
 * 
 * @author Jon
 *
 */
public class Tile_River extends Tile{

	/**
	 * Initialises a new river tile.
	 * @param world The world that the tile exists in.
	 * @param x The tile's X Coordinate
	 * @param y The tile's Y Coordinate
	 */
	public Tile_River(World world, int x, int y){
		super(world,x,y);
		setId("RIVER");
		setImage(Images.RIVER);
		setWalkable(false);
	}
	
	/**
	 * Selects an image for the river. This is a complex function that makes rivers connect up in lines using a total of 15 different
	 * images of the river facing different directions.
	 */
	public void calculateImage(){
		
			//Boolean values that indicate whether a river currently exists in each cardinal direction.
			boolean n = (gridPosition.y-1 > -1 && world.getGridPosition(gridPosition.x, gridPosition.y - 1).getId() == "RIVER");
			boolean e = (gridPosition.x+1 < world.getConfig().getWidth() && world.getGridPosition(gridPosition.x + 1, gridPosition.y).getId() == "RIVER");
			boolean s = (gridPosition.y+1 < world.getConfig().getHeight() && world.getGridPosition(gridPosition.x, gridPosition.y + 1).getId() == "RIVER");
			boolean w = (gridPosition.x-1 > -1 && world.getGridPosition(gridPosition.x - 1, gridPosition.y).getId() == "RIVER");

			
			//get the correct river tile.
			setImage(selectRiverImage(n, e, s, w));
		
	}
	/**
	 * function that returns the correct tile to be used for a section of river
	 * 
	 * @param n boolean indicating whether the tile to the north is a river
	 * @param e boolean indicating whether the tile to the east is a river
	 * @param s boolean indicating whether the tile to the south is a river
	 * @param w boolean indicating whether the tile to the west is a river
	 */
	private BufferedImage selectRiverImage(boolean n, boolean e, boolean s, boolean w){
		//NOTE: Else if statements are unnecessary here because of the return statements in each condition.
		
		//TODO: find a way to improve this - ugly!
		
		if(n && !e && s && !w){
			return Images.RIVER_VERTICAL;
		}
		if(!n && e && !s && w){
			return Images.RIVER_HORIZONTAL;
		}
		
		if(n && !e && !s && !w){
			return Images.RIVER_N;
		}
		if(!n && e && !s && !w){
			return Images.RIVER_E;
		}
		if(!n && !e && s && !w){
			return Images.RIVER_S;
		}
		if(!n && !e && !s && w){
			return Images.RIVER_W;
		}
		
		
		if(n && e && !s && !w){
			return Images.RIVER_N_E;
		}
		if(!n && e && s && !w){
			return Images.RIVER_E_S;
		}
		if(!n && !e && s && w){
			return Images.RIVER_S_W;
		}
		if(n && !e && !s && w){
			return Images.RIVER_W_N;
		}
		
		if(n && e && s && !w){
			return Images.RIVER_N_E_S;
		}
		if(!n && e && s && w){
			return Images.RIVER_E_S_W;
		}
		if(n && !e && s && w){
			return Images.RIVER_S_W_N;
		}
		if(n && e && !s && w){
			return Images.RIVER_W_N_E;
		}
		
		if(n && e && s && w){
			return Images.RIVER_N_E_S_W;
		}
		
		//catchall 
		return Images.RIVER;
		
	}
}
