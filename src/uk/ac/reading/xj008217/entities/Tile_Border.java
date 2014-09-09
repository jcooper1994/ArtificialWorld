package uk.ac.reading.xj008217.entities;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.Images;

/**
 * A tile that is designed to give the world a boundary, both aesthetically and physically to stop LifeForms escaping.
 * @author xj008217
 *
 */
public class Tile_Border extends Tile{

	public Tile_Border(World world, int x, int y) {
		super(world, x, y);
		setId("BORDER");
		setWalkable(false);
		calculateImage();
	}
	
	/**
	 * Sets the border to the correct image.
	 */
	private void calculateImage(){
		 setImage(Images.BORDER);
	}

}
