package uk.ac.reading.xj008217.entities;

import java.util.ArrayList;
import java.util.List;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.Images;
import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * A predator that eats sheep
 * 
 * @author Jon
 *
 */
public class LifeForm_Wolf extends LifeForm{

	private double timer = 0.0;
	private LifeForm prey = null;
	private Food food = null;
	private int sightRange = 150;
	private int randomMovementRange = 150;
	private double eatingSpeed = 25.0;
	private boolean eating = false;
	
	private double hungerThreshold = 100;	
	
	/**
	 * initialise the attributes that make the wolf unique
	 */
	public LifeForm_Wolf(World world,PointDouble position){
		super(world,position);
		//set life form identifier from parent class
		setId("WOLF");
		//set wolf image
		setImage(Images.WOLF);
		setCorpseImage(Images.WOLF_DEAD);
		//set wolf specific speed values
		setRestSpeed(8);
		setPanicSpeed(25);
		//set wolf specific energy loss values\
		setRestEnergyLoss(2);
		setPanicEnergyLoss(4);
	}
	
	/**
	 * Update the wolf and run its artificial intelligence logic, deciding what to do this cycle.
	 */
	@Override
	public void update(){
		super.update();
		
		//if the wolf is not currently eating
		if(!eating){
			//if the wolf is hungry and has no prey in sight
			if(getEnergy() < hungerThreshold && prey == null){
				prey = findPrey();
			}
			//if the wolf doesn't currently have prey:
			if(prey == null){
				setPanicked(false);
				moveRandomly();
			}else{ //wolf has prey
				setPanicked(true);
				moveTowardsPrey();
			}
		}else{
			//the wolf is currently eating
			eat();	
		}
		
		setDirection(calculateDirection(getTarget()));
		
		
	}
	
	/**
	 * Eat the currently selected corpse.
	 */
	private void eat(){
		double amount = eatingSpeed * getWorld().getDeltaTime();
		
		double addedEnergy = food.eat(amount);
		
		if(addedEnergy < amount){
			eating = false;
		}
		
		setEnergy(getEnergy() + addedEnergy);
	}
	
	/**
	 * Returns a new target PointDouble. 
	 * @return A PointDouble representing the new target location.
	 */
	public PointDouble getNewTarget(){
		
		double xPos = getWorld().nextRandomDouble(randomMovementRange*2) - randomMovementRange;
		double yPos = getWorld().nextRandomDouble(randomMovementRange*2) - randomMovementRange;
		
		return new PointDouble(getPosition().x + xPos,getPosition().y + yPos);
	}
	
	/**
	 * Returns the closest prey within sightRange. If no prey can be found then the function returns null.
	 * @return Closest prey if it can be found, if not returns null.
	 */
	public LifeForm findPrey(){
		
		LifeForm closestPrey = null;
		
		List<LifeForm> lifeArray = getWorld().getLifeForms();
		
		int lifeArrayLength = lifeArray.size();
		
		for(int i = 0; i < lifeArrayLength; ++i){
			LifeForm life = lifeArray.get(i);
			if(life.getId() == "SHEEP"){
				
				double distanceToLifeForm = getDistanceToLifeForm(life);
				
				if(closestPrey != null){
					//if this prey can be seen and is closer than the previous closest prey
					if(distanceToLifeForm < sightRange && distanceToLifeForm < getDistanceToLifeForm(closestPrey)){
						closestPrey = life;
					}
				}else{
					//if this prey can be seen
					if(distanceToLifeForm < sightRange)
						closestPrey = life;
				}
			}
		}
		
			
		
		return closestPrey;
		
	}
	
	/**
	 * moves the wolf towards its currently targeted prey.
	 */
	public void moveTowardsPrey(){
		//set target to the preys current position
		setTarget(prey.position);
		//get the distance to the preys position
		double distanceToTarget = Math.abs(getDistanceToTarget());
		//kill the prey!
		if(distanceToTarget < 1.0){
			food = prey.die();
			prey = null;
			eating = true;
		}else{
			moveTowardsTarget();
		}
	}
	
	/**
	 * causes the wolf to wander around, used when it is not hungry or cant find any sheep.
	 */
	public void moveRandomly(){
		
		double distanceToTarget = Math.abs(getDistanceToTarget());
		//if not at target, move towards target
		if(distanceToTarget > 1.0){
			if(moveTowardsTarget() == false){
				//TODO a pathfinder would be really really useful here
				setTarget(getNewTarget());
			}
			setDirection(calculateDirection(getTarget()));
		}else{
			setTarget(getNewTarget());
		}
		
	}
	
	/**
	 * Causes the wolf to die.
	 */
	public Food die(){
		return(super.die());
	}
	
}
