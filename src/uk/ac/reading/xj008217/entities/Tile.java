package uk.ac.reading.xj008217.entities;

import java.awt.Point;
import java.awt.image.BufferedImage;

import uk.ac.reading.xj008217.World;

/**
 * A background/environment tile.
 * @author Jon
 *
 */
public class Tile extends Entity{
	
	private boolean isWalkable;
	private BufferedImage image;
	private String id;
	protected World world;
	protected Point gridPosition = new Point(0,0);
	
	/**
	 * updates the tile (image/food amounts etc).
	 */
	public void update(){
		
	}
	
	public boolean isWalkable() {
		return isWalkable;
	}
	public void setWalkable(boolean walkable) {
		this.isWalkable = walkable;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Tile(World world,int x,int y){
		this.world = world;
		this.gridPosition.x = x;
		this.gridPosition.y = y;
	}
	
}
