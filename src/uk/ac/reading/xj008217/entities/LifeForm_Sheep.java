package uk.ac.reading.xj008217.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.Images;
import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * A herbivore that eats grass
 * @author Jon
 *
 */
public class LifeForm_Sheep extends LifeForm{


	private double grassEatenPerSecond = 4.0;
	private static double sightRange = 60;
	private static double escapeDistance = 120;
	private LifeForm threat = null;

	//the controller used to control a herd of sheep
	private SheepController controller;
	/**
	 * Initialise attributes that make sheep different to other life forms.
	 */
	public LifeForm_Sheep(World world,PointDouble position,SheepController controller){
		super(world,position);
		this.controller = controller;
		//set life form identifier from parent class
		setId("SHEEP");
		//set sheep image
		setImage(Images.SHEEP);
		setCorpseImage(Images.SHEEP_DEAD);
		setFoodInCorpse(200.0);
		//set sheep specific speed values
		setRestSpeed(3);
		setPanicSpeed(10);
		//set sheep specific energy loss values
		setRestEnergyLoss(2);
		setPanicEnergyLoss(4);
	}
	
	/**
	 * Update the sheep and run its artificial intelligence logic, deciding what to do this cycle.
	 */
	@Override
	public void update(){
		super.update();
		
		double distanceToTarget = Math.abs(getDistanceToTarget());
		
		if(isPanicked()){
			setTarget(threat.position);
			moveAwayFromTarget();			
			setDirection(calculateOppositeDirection(getTarget()));
			
			distanceToTarget = Math.abs(getDistanceToTarget());
			if(distanceToTarget > escapeDistance){
				setPanicked(false);
				controller.refreshLocation();
				setTarget(controller.getNewTarget());
			}
			
		}else{//if the sheep is not panicked
			
			LifeForm newThreat = findThreats();
			if(newThreat != null){
				controller.alertToThreat(newThreat);
			}
			
			eatGrass();
			
			if(distanceToTarget > 1.0){
				if(moveTowardsTarget() == false){ // if the sheep hits a wall then it needs a new target
					//TODO replace with logic that moves them into the nearest open area
					setTarget(controller.getNewTarget());
				}
			}else{ //the sheep has reached its target
				setTarget(controller.getNewTarget());
			}
			//TODO optimisation - dont call set direction when unneccesary
			setDirection(calculateDirection(getTarget()));
			
		}

	}
	
	/**
	 * Attempts to eat the grass square that the sheep is currently stood on.
	 */
	public void eatGrass(){
		Tile tile = getWorld().getTileAtWorldPosition(position.x,position.y);
		
		if(tile.getId() == "GRASS"){
			double energy = getEnergy();
			double addedEnergy;
			addedEnergy = ((Tile_Grass) tile).eatGrass(grassEatenPerSecond * getWorld().getDeltaTime());
			setEnergy(energy + addedEnergy);
		}
	}
	
	/**
	 * Searches for threats that are closer than the sheep's sightRange.
	 * @return The closest threat to the sheep
	 */
	public LifeForm findThreats(){
		
		LifeForm closestThreat = null;
		
		List<LifeForm> lifeArray = getWorld().getLifeForms();
		
		int lifeArrayLength = lifeArray.size();
		
		for(int i = 0; i < lifeArrayLength; ++i){
			LifeForm life = lifeArray.get(i);
			if(life.getId() == "WOLF"){
				
				double distanceToLifeForm = getDistanceToLifeForm(life);
				
				if(closestThreat != null){
					//if this prey can be seen and is closer than the previous closest prey
					if(distanceToLifeForm < sightRange && distanceToLifeForm < getDistanceToLifeForm(closestThreat)){
						closestThreat = life;
					}
				}else{
					//if this prey can be seen
					if(distanceToLifeForm < sightRange)
						closestThreat = life;
				}
			}
		}
		
		return closestThreat;
		
	}
	
	
	/**
	 * Causes the sheep to die (returns a food object because the death spawns a corpse!).
	 */
	public Food die(){
		controller.removeSheep(this);
		return(super.die());
		
	}
	
	
	public LifeForm getThreat() {
		return threat;
	}

	public void setThreat(LifeForm threat) {
		this.threat = threat;
	}

	public SheepController getController() {
		return controller;
	}
	public void setController(SheepController controller) {
		this.controller = controller;
	}
	
}
