package uk.ac.reading.xj008217.entities;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.Images;

/**
 * A forest tile - acts as a barrier that is generated in natural forest patters, that feather out towards the edges.
 * @author Jon
 *
 */
public class Tile_Forest extends Tile{

	/**
	 * Initialises a new forest tile
 	 * @param world The world that the tile exists in.
 	 * @param x The tiles X Coordinate
	 * @param y The tiles Y Coordinate
	 */
	public Tile_Forest(World world, int x, int y){
		super(world,x,y);
		setId("FOREST");
		setImage(Images.FOREST);
		setWalkable(false);
	}
	
	/**
	 * Calculates the image to use for the tile. The closer to the edge of a forest the tile is, the fewer trees appear in its image.
	 */
	public void calculateImage(){
		int count = 0;
		int forLoopStart = -world.getConfig().getForestThicknessSquareSize();
		int forLoopEnd = world.getConfig().getForestThicknessSquareSize();
		for(int y2 = forLoopStart; y2 <= forLoopEnd; ++y2){
			for(int x2 = forLoopStart; x2 <= forLoopEnd; ++x2){
				//check that the grid position exists in the world to prevent array index out of bounds exceptions
				if(!(gridPosition.x+x2 < 0 || gridPosition.x+x2 >= world.getConfig().getWidth() || gridPosition.y+y2 < 0 || gridPosition.y+y2 >= world.getConfig().getHeight())){
					//if grass is found then increment the counter (indicates that this tile is near the edge of the forest)
					if(world.getGridPosition(gridPosition.x+x2,gridPosition.y+y2).getId() == "GRASS")
						++count;
				}
				
			}
		}
		//assign new forest image based on the number of grass tiles found
		if(count > world.getConfig().getForestThicknessThreshold()[3]){
			setImage(Images.FOREST_VERY_THIN);
		}else if(count > world.getConfig().getForestThicknessThreshold()[2]){
			setImage(Images.FOREST_THIN);
		}else if(count > world.getConfig().getForestThicknessThreshold()[1]){
			setImage(Images.FOREST_MEDIUM);
		}else if(count > world.getConfig().getForestThicknessThreshold()[0]){
			setImage(Images.FOREST_DENSE);
		}
	}
}
