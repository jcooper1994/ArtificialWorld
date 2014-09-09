package uk.ac.reading.xj008217.entities;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.Images;

/**
 * A grass tile is the basic and most abundant tile in the simulation, herbivores eat the grass. 
 * The grass grows at a steady rate, and it grows faster near rivers. 
 * 
 * @author Jon
 *
 */
public class Tile_Grass extends Tile{

	//variable showing how much food is left on a patch of grass
	private double foodLeft = 0;
	
	//maximum amount of food that grass can sustain
	private double maxFood = 50;
	
	//variable showing how much food the grass generates each second.
	private double growthPerSecond = 0.1;
	private double riverGrowthBonus = 0.3;
	
	private boolean poisoned = false;
	private static double poisonMultiplier = 25.0;
	
	/**
	 * Initialises a new grass tile
	 * @param world The world that the tile exists in.
	 * @param x The tile's X Coordinate
	 * @param y The tile's Y Coordinate
	 */
	public Tile_Grass(World world, int x, int y){
		super(world,x,y);
		setId("GRASS");
		setWalkable(true);
		setImage(Images.GRASS);
		
		//pick random food amount for the grass
		foodLeft = world.nextRandomDouble((int)maxFood);
		update();
	}
	
	/**
	 * Updates the grass, causing it to grow and change image if the percentage of food remaining passes set of thresholds.
	 */
	@Override 
	public void update(){
		if(poisoned){
			setImage(Images.GRASS_POISONED);
		}else{
			if(foodLeft < maxFood*0.25){
				setImage(Images.GRASS_LOW);
			}else if(foodLeft < maxFood*0.5){
				setImage(Images.GRASS);
			}else if(foodLeft < maxFood*0.8){
				setImage(Images.GRASS_HIGH);
			}else{
				setImage(Images.GRASS_VERY_HIGH);
			}
			//TODO logic for grass growth rates (EG: faster near water)
			if(foodLeft < maxFood){
				foodLeft += growthPerSecond * world.getDeltaTime();
			}
		}
	}
	
	/**
	 * Calculate growth per second based on surrounding features. EG: Rivers cause grass to grow faster.
	 */
	public void updateGrowthPerSecond(){
		double addedGrowth = 0.0;
		
		//no need to check bounds since the border tiles will definitely not be grass	
		addedGrowth = (world.getGridPosition(gridPosition.x, gridPosition.y+1).getId() == "RIVER") ? addedGrowth + riverGrowthBonus : addedGrowth;
		addedGrowth = (world.getGridPosition(gridPosition.x, gridPosition.y-1).getId() == "RIVER") ? addedGrowth + riverGrowthBonus : addedGrowth;
		addedGrowth = (world.getGridPosition(gridPosition.x+1, gridPosition.y).getId() == "RIVER") ? addedGrowth + riverGrowthBonus : addedGrowth;
		addedGrowth = (world.getGridPosition(gridPosition.x-1, gridPosition.y).getId() == "RIVER") ? addedGrowth + riverGrowthBonus : addedGrowth;
			
		
		growthPerSecond += addedGrowth;
	}
	
	/**
	 * Returns an amount of food based on a requested amount. 
	 * @param amount The amount of food that a life form is requesting to eat.
	 * @return The amount of food that can be eaten (will be negative if the grass is poisonous!).
	 */
	public double eatGrass(double amount){
		if(poisoned){
			return -amount * poisonMultiplier; //reduces the sheeps energy when it is eaten!
		}else{
			if(foodLeft > amount){
				foodLeft -= amount;
				return amount;
			}else{
				double returnValue = foodLeft;
				foodLeft = 0.0;
				return returnValue;
			}
		}
	}

	public boolean isPoisoned() {
		return poisoned;
	}

	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
	}

	public double getFoodLeft() {
		return foodLeft;
	}

	public void setFoodLeft(double foodLeft) {
		this.foodLeft = foodLeft;
	}
	
	
	
}
