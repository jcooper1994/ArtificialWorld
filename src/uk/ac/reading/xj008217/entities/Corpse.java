package uk.ac.reading.xj008217.entities;

import java.awt.image.BufferedImage;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * A dead animal (acts as food)
 * 
 * @author Jon
 *
 */
public class Corpse extends Food{

	/**
	 * Creates a new corpse. The corpse is in fact a Food object that has specific attributes.
	 * @param position The world location of the corpse.
	 * @param image The image for the corpse.
	 * @param world The world that the corpse exists in.
	 * @param food The amount of energy that the corpse is able to provide.
	 */
	public Corpse(PointDouble position, BufferedImage image,World world, double food){
		super(position,image,world);
		
		this.setDecays(true);
		this.setDecayRate(5.0);
		this.setFoodLeft(food);
	}
	
	/**
	 * Calls update on the parent class (Food).
	 */
	public void update(){
		super.update();
	}
	
}
