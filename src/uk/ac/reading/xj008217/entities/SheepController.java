package uk.ac.reading.xj008217.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.utilities.PointDouble;
import uk.ac.reading.xj008217.utilities.ProgramConstants;

/**
 * A sheep controller is responsible for controlling a single herd of sheep, and giving them new movement targets
 * when they request them. It helps the sheep move as a herd towards longer grass.
 * 
 * @author xj008217
 *
 */
public class SheepController {

	private World world; 
	private ArrayList<LifeForm_Sheep> sheepList = new ArrayList<LifeForm_Sheep>();
	
	private PointDouble position;
	
	private boolean herdIsPanicked = false;
	private List<LifeForm> threats = new ArrayList<LifeForm>();
	
	private int herdSpacing = 3;
	//distance that the herd can detect grass
	private int sightDistance = 2;
	
	private double timeBetweenGrassChecks = 5.0;
	private double nextGrassCheck = 0.0;
	
	/**
	 * Initialises a new SheepController
	 * @param world The world that this controller exists within.
	 * @param controllerPoint The location in the world that this controller exists (controls the origin of the herd).
	 */
	public SheepController(World world,PointDouble controllerPoint){
		this.world = world;
		this.position = controllerPoint; 
		
		this.herdSpacing = world.getConfig().getSheepHerdSpacing();
		//randomise time until next check to make it look more natural
		nextGrassCheck = world.nextRandomDouble(timeBetweenGrassChecks);
	}
	
	/**
	 * Alerts all sheep in the herd to a threat specified by threat.
 	 * @param threat The threat that the sheep will be alerted to.
	 */
	public void alertToThreat(LifeForm threat){

		
		//set all sheep to panicked and give them new targets
		for(int i = 0; i < sheepList.size(); ++i){
			sheepList.get(i).setThreat(threat);
			sheepList.get(i).setPanicked(true);
		}
		
		
	}
	
	/**
	 * Resets the position of the controller to an average of its sheep's positions.
	 * Helps the herd recover after an attack.
	 */
	public void refreshLocation(){
		PointDouble averageLocation = new PointDouble();
		
		for(int i = 0; i < sheepList.size(); ++i){
			averageLocation.x += sheepList.get(i).getPosition().x;
			averageLocation.y += sheepList.get(i).getPosition().y;
		}
		averageLocation.x /= sheepList.size();
		averageLocation.y /= sheepList.size();
		
		setPosition(averageLocation);
		
	}
	
	
	public void update(){
		
		/*
		 * when the herd has not detected threats the controller
		 * will attempt to find grass that has more food left
		 */
		if(!herdIsPanicked){
			//check for grass every x seconds
			nextGrassCheck -= world.getDeltaTime();
			if(nextGrassCheck < 0.0){
				moveTowardsGrass();
				nextGrassCheck = timeBetweenGrassChecks;
			}
		}
		
	}
	/**
	 * Moves the sheep controller towards an area with more grass. This is achieved by calculating a weighted average
	 * of the grass tiles near by. EG: A grass tile with 50 food will have its position multiplied by 50 while a grass tile
	 * with 30 food will have it multiplied by 30. The total of all weighted averages added together is divided by the total
	 * amount of food in grass tiles in the area. The controller is then moved to this new position which will be closer to thicker grass.
	 * 
	 * This technique works well because the controller will move faster when the thicker grass is further away.
	 */
	public void moveTowardsGrass(){
	
		double totalFood = 0.0;
		PointDouble grassDirection = new PointDouble(0.0,0.0);
		
		int tileWidth = ProgramConstants.TILE_WIDTH;
		int tileHeight = ProgramConstants.TILE_HEIGHT;
		int worldPixelWidth = world.getConfig().getWidth() * tileWidth;
		int worldPixelHeight = world.getConfig().getHeight() * tileHeight;
		
		
		for(int y = (int) (position.y - ((herdSpacing+sightDistance)*tileHeight)); y < position.y + ((herdSpacing+sightDistance)*tileHeight); y += tileHeight){
			for(int x = (int) (position.x - ((herdSpacing+sightDistance)*tileWidth)); x < position.x + ((herdSpacing+sightDistance)*tileWidth); x += tileWidth){
				if(x > 0 && x < worldPixelWidth-1 && y > 0 && y < worldPixelHeight-1){
				Tile tile = world.getTileAtWorldPosition(x, y);
					if(tile.getId() == "GRASS"){
						totalFood += ((Tile_Grass) tile).getFoodLeft();
						grassDirection.x += x * ((Tile_Grass) tile).getFoodLeft();
						grassDirection.y += y * ((Tile_Grass) tile).getFoodLeft();
					}
				}
			}
		}
		
		grassDirection.x /= totalFood;
		grassDirection.y /= totalFood;
		
		
		//make sure that the path between current position and end position is walkable (dont lead sheep over rivers!).
		if(world.isPathWalkable(position,grassDirection)){
			position = grassDirection;
		}
		
	}
	
	/**
	 * Returns a new target location within the bounds of the herd.
	 * @return A PointDouble within the bounds of the herd.
	 */
	public PointDouble getNewTarget(){
		
		PointDouble newPoint = new PointDouble(0.0,0.0);
		
		newPoint.x = position.x + world.nextRandomDouble((int)herdSpacing*ProgramConstants.TILE_WIDTH*2)-herdSpacing*ProgramConstants.TILE_WIDTH;
		newPoint.y = position.y + world.nextRandomDouble((int)herdSpacing*ProgramConstants.TILE_HEIGHT*2)-herdSpacing*ProgramConstants.TILE_HEIGHT;
		
		return(newPoint);		
	}
	
	/**
	 * Sets the herd into a panic state
	 */
	public void panic(){
		herdIsPanicked = true;
	}

	public PointDouble getPosition() {
		return position;
	}

	public void setPosition(PointDouble position) {
		this.position = position;
	}
	public List<LifeForm> getThreats() {
		return threats;
	}

	public void addSheep(LifeForm_Sheep newSheep){
		sheepList.add(newSheep);
	}
	public void removeSheep(LifeForm_Sheep sheep){
		sheepList.remove(sheep);
	}
	
}
