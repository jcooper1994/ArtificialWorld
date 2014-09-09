package uk.ac.reading.xj008217.entities;

import java.awt.image.BufferedImage;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * Food describes any source of food in the world.
 * 
 * @author xj008217
 */
public class Food extends Entity{
	
	
	private BufferedImage image;
	
	private double foodLeft = 0.0;
	
	private boolean decays = false;
	private double decayRate = 0.0;
			
	private World world;
	
	public Food(PointDouble position,BufferedImage image,World world){
		this.position = position;
		this.image = image;
		this.world = world;
	}
	
	/**
	 * Updates the food object. Removes the food if there is no food left and lowers the amount of food left
	 * if this food object is set to decay.
	 */
	public void update(){
		if(decays){
			foodLeft -= decayRate * world.getDeltaTime();
		}
		if(foodLeft <= 0.0){
			world.removeFood(this);
		}
	}

	/**
	 * Takes an amount of food (the requested amount to be eaten) and returns the actual amount eaten, accounting for
	 * any potential lack of food. EG: the amount may be more than food left.
	 * @param amount The amount of food requested
	 * @return The amount of food given
	 */
	public double eat(double amount){
		if(amount < foodLeft){
			foodLeft -= amount;
			return amount;
		}else{
			foodLeft = 0.0;

			return foodLeft;
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public double getFoodLeft() {
		return foodLeft;
	}

	public void setFoodLeft(double foodLeft) {
		this.foodLeft = foodLeft;
	}

	public boolean isDecays() {
		return decays;
	}

	public void setDecays(boolean decays) {
		this.decays = decays;
	}

	public double getDecayRate() {
		return decayRate;
	}

	public void setDecayRate(double decayRate) {
		this.decayRate = decayRate;
	}
	
	
}
