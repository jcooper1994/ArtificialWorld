package uk.ac.reading.xj008217;

import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.reading.xj008217.animations.AnimatedEffect;
import uk.ac.reading.xj008217.entities.Food;
import uk.ac.reading.xj008217.entities.LifeForm;
import uk.ac.reading.xj008217.entities.LifeForm_Sheep;
import uk.ac.reading.xj008217.entities.LifeForm_Wolf;
import uk.ac.reading.xj008217.entities.SheepController;
import uk.ac.reading.xj008217.entities.Tile;
import uk.ac.reading.xj008217.entities.Tile_Border;
import uk.ac.reading.xj008217.entities.Tile_Forest;
import uk.ac.reading.xj008217.entities.Tile_Grass;
import uk.ac.reading.xj008217.entities.Tile_River;
import uk.ac.reading.xj008217.utilities.PointDouble;
import uk.ac.reading.xj008217.utilities.ProgramConstants;

/**
 * Runs the simulation
 * 
 * @author xj008217
 *
 */
public class World implements Runnable{
	
	
	private WorldConfig config;
		
	private Tile[][] grid;
	
	//Random number generator for world generation.
	private Random rand;
	
	private List<LifeForm> lifeForms = new ArrayList<LifeForm>();
	private List<SheepController> sheepControllers = new ArrayList<SheepController>();
	private List<Food> food = new ArrayList<Food>();
	private List<AnimatedEffect> animatedEffects = new ArrayList<AnimatedEffect>();
		
	//things that need removing from the world at the end of the cycle
	private List<LifeForm> lifeFormsToRemove = new ArrayList<LifeForm>();
	private List<Food> foodToRemove = new ArrayList<Food>();
	private List<AnimatedEffect> animatedEffectsToRemove = new ArrayList<AnimatedEffect>();
	
	private long startTime = 0;
	private double deltaTime = 0;
	
	private boolean paused = false;
	
	/**
	 * Generates a standard world with a default world config object
	 */
	public World(){
		
		this.config = new WorldConfig();
		
		grid = new Tile[config.getWidth()][config.getHeight()];
		
		generateWorld(0);
	}
	
	/**
	 * Generates a world using the specified configuration object.
	 * @param config The WorldConfig object that specifies this worlds parameters.
	 */
	public World(WorldConfig config){
		this.config = config;
		
		grid = new Tile[config.getWidth()][config.getHeight()];
		
		generateWorld(0);
	}
	
	/**
	 * Starts the world's simulation and updates all neccesary objects within the world.
	 * Once started this function runs in a loop until the world panel is closed.
	 * 
	 * @Override
	 */
	public void run() {
		startTime = System.nanoTime();
		// TODO Add all world update logic
		while(true){
			if(!paused){
				//calculate and store start time for this cycle
				startTime = System.nanoTime();
				
				//update all tiles
				for(int y = 0; y < config.getHeight(); ++y){
					for(int x = 0; x < config.getWidth(); ++x){
						grid[x][y].update();
					}
				}
				//update all life forms
				int numLifeForms = lifeForms.size();
				for(int i = 0; i < numLifeForms; ++i){
					lifeForms.get(i).update();
				}
				
				//update all sheep herd controllers
				int numSheepControllers = sheepControllers.size();
				for(int i = 0; i < numSheepControllers; ++i){
					sheepControllers.get(i).update();
				}
				
				//update all food
				int numFood = food.size();
				for(int i = 0; i < numFood; ++i){
					food.get(i).update();
				}
				
				//update all animated effects
				int numAnimatedEffects = animatedEffects.size();
				for(int i = 0; i < numAnimatedEffects; ++i){
					animatedEffects.get(i).update();
				}
				
				
				//remove all life forms that need removing
				int numLifeFormsToRemove = lifeFormsToRemove.size();
				for(int i = 0; i < numLifeFormsToRemove; ++i){
					lifeForms.remove(lifeFormsToRemove.get(i));
				}
				//set to a new empty list
				lifeFormsToRemove = new ArrayList<LifeForm>();
				
				
				//remove all food
				int numFoodToRemove = foodToRemove.size();
				for(int i = 0; i < numFoodToRemove; ++i){
					food.remove(foodToRemove.get(i));
				}
				//set to a new empty list
				foodToRemove = new ArrayList<Food>();
				
				//remove all animations
				int numEffectsToRemove = animatedEffectsToRemove.size();
				for(int i = 0; i < numEffectsToRemove; ++i){
					animatedEffects.remove(animatedEffects.get(i));
				}
				//set to a new empty list
				animatedEffectsToRemove = new ArrayList<AnimatedEffect>();
				
				//set the delta time as number of seconds that the cycle took
				deltaTime = (System.nanoTime() - startTime) / 1e9;
			}else{
				//sleep the thread to save over processing
				try {
					Thread.sleep(ProgramConstants.PAUSE_SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	/**
	 * Generates the world through a series of procedural generation techniques. 
	 * 
	 * @param randSeed sets a seed to generate the world, if 0 then a random seed is selected.
	 */
	private void generateWorld(long randSeed){
		//seed RNG.
		if(randSeed != 0){
			rand = new Random(randSeed);
		}else{
			rand = new Random();
		}
		
		//initialise all tiles as open grass.
		initialiseGrass();
		//generate forests on top of the grass
		generateForests();
		//generate rivers on top of grass and through forests
		generateRivers();
		//add the aesthetic 'wooden' border to the world
		addBorder();
		
		//loop through grass and update growth rates, also set some grass to poisoned
		for(int y = 0; y < config.getHeight();++y){
			for(int x = 0; x < config.getWidth(); ++x){
				Tile tile = getGridPosition(x, y);
				if(tile.getId() == "GRASS"){
					((Tile_Grass) tile).updateGrowthPerSecond();
					if(rand.nextInt(10000) < config.getGrassPoisonChance()){
						((Tile_Grass) tile).setPoisoned(true);
					}
				}
			}
		}
		//generate each type of animal (if the world should have them, as specified in its config).
		if(config.hasSheep())
			generateSheep();
		
		if(config.hasWolves())
			generateWolves();
		
	}
	

	/**
	 * Adds border tiles to the edges of the world. The border prevents LifeForms from leaving the world.
	 */
	private void addBorder() {
		for(int y = 0; y < config.getHeight(); ++y){
			for(int x = 0; x < config.getWidth(); ++x){
				if(x == 0 || x == config.getWidth()-1 || y == 0 || y == config.getHeight()-1){
					grid[x][y] = new Tile_Border(this,x,y);
				}
			}
		}
	}

	/**
	 * Generates and places all wolves in the world as specified by the world's config object.
	 */
	private void generateWolves() {
		for(int wolf = 0; wolf < config.getWolfCount(); ++wolf){
			//select a location for the new wolf. Must be placed on a walkable tile.
			PointDouble wolfPosition;
			boolean pointIsValid = true;
			
			do{
				pointIsValid = true;
				wolfPosition = new PointDouble(rand.nextInt(config.getWidth() * ProgramConstants.TILE_WIDTH),rand.nextInt(config.getHeight() * ProgramConstants.TILE_WIDTH));
				
				if(getTileAtWorldPosition(wolfPosition.x, wolfPosition.y).isWalkable() == false){
					pointIsValid = false;
				}

			}while(!pointIsValid);
			
			//create new wolf and add it to life form list so that it can be accessed by the world
			LifeForm_Wolf newWolf = new LifeForm_Wolf(this,wolfPosition);
			lifeForms.add(newWolf);

		}
	}
	
	/**
	 * Generates and places all sheep in the world (in their respective herds with SheepControllers).
	 */
	private void generateSheep() {
		for(int herd = 0; herd < config.getSheepHerds(); ++herd){
			//select a location for the sheep controller. Must be placed on walkable tiles.
			PointDouble controllerPoint;
			boolean pointIsValid = true;
			
			do{
				pointIsValid = true;
				controllerPoint = new PointDouble(rand.nextInt(config.getWidth() * ProgramConstants.TILE_WIDTH),rand.nextInt(config.getHeight() * ProgramConstants.TILE_WIDTH));
				//check cells in a square around the controller
				
				int herdSpacing = config.getSheepHerdSpacing()*ProgramConstants.TILE_HEIGHT;
				for(int y = (int) (controllerPoint.y - (herdSpacing)); y < controllerPoint.y + (herdSpacing); ++y){
					for(int x = (int) (controllerPoint.x - (herdSpacing)); x < controllerPoint.x + (herdSpacing); ++x){
						if(x >= 0 && x < config.getWidth() * ProgramConstants.TILE_WIDTH && y >= 0 && y < config.getHeight() * ProgramConstants.TILE_WIDTH){
							if(getTileAtWorldPosition(x, y).isWalkable() == false){
								pointIsValid = false;
							}
						}else{
							pointIsValid = false; 
						}
					}
				}
			}while(!pointIsValid);
			
				
			//create a sheep controller for this herd:
			SheepController sheepController = new SheepController(this,controllerPoint); //the sheep controller allows herds to communicate with eachother
			sheepControllers.add(sheepController);
			//calculate the size for this particular herd of sheep
			int sheepHerdSizeWithVariance = config.getSheepHerdSize() + (rand.nextInt(config.getSheepHerdVariance()*2) - config.getSheepHerdVariance());
			
			
			//loop through sheep in herd
			for(int sheep = 0; sheep < sheepHerdSizeWithVariance; ++sheep){
				// the world location that the sheep will be placed
				int varianceSize = (config.getSheepHerdSpacing() * ProgramConstants.TILE_WIDTH);
				int xVariance = rand.nextInt(varianceSize) - varianceSize/2;
				int yVariance = rand.nextInt(varianceSize) - varianceSize/2;
				PointDouble sheepPoint = new PointDouble(controllerPoint.x + xVariance,controllerPoint.y + yVariance);
				// instantiate a new sheep, passing the world into it as a parameter.
				LifeForm_Sheep newSheep = new LifeForm_Sheep(this,sheepPoint,sheepController);
				// add the sheep to the world's life form list 
				lifeForms.add(newSheep);
				sheepController.addSheep(newSheep);
			}

		}
	}
	/**
	 * Fill all spaces in the world with grass tiles.
	 */
	private void initialiseGrass(){
		for(int y = 0; y < config.getHeight(); ++y){
			for(int x = 0; x < config.getWidth(); ++x){
				grid[x][y] = new Tile_Grass(this,x,y);
			}
		}
	}
	
	/**
	 * Generates rivers that run in fairly straight lines with some corners
	 * Uses a bias value that prevents the river from turning back on its self.
	 */	
	private void generateRivers(){
		
		//Loop for river count.
		for(int river = 0;river<config.getRiverCount();++river){
			
			//turn bias used to make the river less likely to turn on its self.
			int turnBias = 50;
			//pick start location for river.
			int riverX = rand.nextInt(config.getWidth());
			int riverY = rand.nextInt(config.getHeight());
			
			//pick start direction (0 = north, ascending clockwise).
			int dir = rand.nextInt(4);
			//pick an edge of the world for the river to start on:
			int startEdge = rand.nextInt(4);
			//start direction for the river (moving away from edge)
			dir = (startEdge + 2) % 4;
			
			//if start is north
			if(startEdge == 0){
				riverX = rand.nextInt(config.getWidth());
				riverY = 0;
			}else if(startEdge == 1){
				riverX = config.getWidth()-1;
				riverY = rand.nextInt(config.getHeight());
			}else if(startEdge == 2){
				riverX = rand.nextInt(config.getWidth());
				riverY = config.getHeight()-1;
			}else if(startEdge == 3){
				riverX = 0;
				riverY = rand.nextInt(config.getHeight());
			}
			//set start position of river on the grid
			grid[riverX][riverY] = new Tile_River(this,riverX,riverY);
			
			//variable used to determine whether river has just turned (to stop river turning twice in a row)
			boolean riverCanTurn = true;
			
			while(true){
				// check whether the river should turn here and if so increment/decrement direction.
				//TODO: add logic to turn bias to prevent the river turning towards adjacent river
				if(riverCanTurn){
					if(rand.nextInt(100) < config.getRiverVariance()){
						if(rand.nextInt(100) < turnBias){
							++dir;
							turnBias -= 50;
						}else{
							--dir;
							turnBias += 50;
						}
					}
					riverCanTurn = false;
				}else{
					riverCanTurn = true;
				}
				//prevent direction from being negative or over 3.
				if(dir > 3){
					dir = 0;
				}else if(dir < 0){
					dir = 3;
				}
				
				//Increment the new river position based on the direction variable.
				if(dir == 0){
					--riverY;
				}else if(dir == 1){
					++riverX;
				}else if(dir == 2){
					++riverY;
				}else if(dir == 3){
					--riverX;
				}
				
				//Check to see whether the river has reached a map edge - if so break out of the while loop.
				if(riverX == config.getWidth() || riverX == -1 || riverY == config.getHeight() || riverY == -1){
					break;
				}

				//add a river tile to this position.
				grid[riverX][riverY] =  new Tile_River(this,riverX,riverY);

			}
			
		}
						
		//make a pass over the world to correct all river joins (Make sure the turns look correct).
		for(int y = 0; y < config.getHeight(); ++y){
			for(int x = 0; x < config.getWidth(); ++x){
				if(grid[x][y].getId() == "RIVER"){
					
					//get the correct river image
					 ((Tile_River) grid[x][y]).calculateImage();
				}
			}
		}
		
	}

	/**
	 * Generates forests in the world, as specified by variables in the world's config object.
	 */
	private void generateForests(){
		//Do an initial pass of the map adding forests based on forest density value.
		for(int y = 0; y < config.getWidth(); ++y){
			for(int x = 0; x < config.getHeight(); ++x){
				//in this case the random value is out of 1000 to force a low forest count
				if(rand.nextInt(1000) < config.getForestDensity()){
					grid[x][y] = new Tile_Forest(this,x,y);
				}
			}
		}
		
		//loop through the map again multiple times, expanding forests at random.
		for(int forestCount = 0; forestCount < config.getForestSize(); ++forestCount){
			for(int y = 1; y < config.getWidth()-1; ++y){
				for(int x = 1; x < config.getHeight()-1; ++x){
					if(grid[x][y].getId() == "FOREST"){
						if(rand.nextInt(100) < config.getForestThickness()){
							grid[x+1][y] = new Tile_Forest(this,x+1,y);
							grid[x-1][y] = new Tile_Forest(this,x-1,y);
							grid[x][y+1] = new Tile_Forest(this,x,y+1);
							grid[x][y-1] = new Tile_Forest(this,x,y-1);
						}
					}
				}
			}
		}
		
		
		//make a pass over the world to adjust forest density images (make forests look thinner towards their edges).	
		for(int y = 0; y < config.getHeight(); ++y){
			for(int x = 0; x < config.getWidth(); ++x){
				if(grid[x][y].getId() == "FOREST"){
					//check how many nearby tiles have grass in them
					((Tile_Forest) grid[x][y]).calculateImage();
					
				}
			}
		}
		
	}
	
	/**
	 * translates an x/y screen coordinate pair into a tile from the world grid
	 * @param x the x coordinate to be translated
	 * @param y the y coordinate to be translated
	 * @return Tiles the tile found at that position
	 */
	public Tile getTileAtScreenPosition(int x, int y){
		
		Point isometricPoint = new Point(x,y);
		Point cartesianPoint = isometricToCartesian(isometricPoint);
		
		double newX = cartesianPoint.x;
		double newY = cartesianPoint.y;
		
		newX /= ProgramConstants.TILE_WIDTH;
		newY /= ProgramConstants.TILE_HEIGHT;
		
		cartesianPoint.x = (int) newX;
		cartesianPoint.y = (int) newY;

		return getGridPosition(cartesianPoint.y,cartesianPoint.x);
	}
	
	/**
	 * translates an x/y world coordinate pair (stored as integers) into a tile from the world grid
	 * @param x the x coordinate to be translated
	 * @param y the y coordinate to be translated
	 * @return Tiles the tile found at that position
	 */
	public Tile getTileAtWorldPosition(int x, int y){
        
        double newX = x;
        double newY = y;
        
        newX = Math.floor(newX / ProgramConstants.TILE_WIDTH);
        newY = Math.floor(newY / ProgramConstants.TILE_HEIGHT);
        
        x = (int) Math.floor(newX);
        y = (int) Math.floor(newY);

        return getGridPosition(x,y);
	 }
	
	/**
	 * translates an x/y world coordinate pair (stored as doubles) into a tile from the world grid
	 * @param x the x coordinate to be translated
	 * @param y the y coordinate to be translated
	 * @return Tiles the tile found at that position
	 */
	public Tile getTileAtWorldPosition(double x, double y){
				
		x = Math.floor(x / ProgramConstants.TILE_WIDTH);
		y = Math.floor(y / ProgramConstants.TILE_HEIGHT);
		
		int xInt = (int) Math.floor(x);
		int yInt = (int) Math.floor(y);

		return getGridPosition(xInt,yInt);
	}
	
	/**
	 * Returns the next random int between 0 and val from the world's random number generator.
	 * @param val the maximum int that can be returned.
	 * @return next random int between 0 and val.
	 */
	public int nextRandomInt(int val){
		return(rand.nextInt(val));
	}
	/**
	 * Returns the next random double between 0.0 and val from the world's random number generator.
	 * @param val the maximum number (as an integer) that can be returned.
	 * @return next random double between 0.0 and val.
	 */
	public double nextRandomDouble(int val){
		return(rand.nextDouble() * val);
	}
	/**
	 * Returns the next random double between 0.0 and val from the world's random number generator.
	 * @param val the maximum double that can be returned.
	 * @return next random double between 0.0 and val.
	 */
	public double nextRandomDouble(double val){
		return(rand.nextDouble() * val);
	}
	
	/**
	 * Returns the tile at a particular grid position
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return The tile that exists at x and y.
	 */
	public Tile getGridPosition(int x, int y){
		return grid[x][y];
	}

	/**
	 * Converts cartesian coordinates to isometric coordinates
	 * 
	 * @param point The Point in cartesian space
	 * @return The Point in isometric space
	 */
	public Point cartesianToIsometric(Point point){
		Point returnPoint = new Point(0,0);
		
		returnPoint.x = point.x - point.y;
		returnPoint.y = (point.x + point.y) / 2;
		
		return(returnPoint);
	}
	/**
	 * Converts cartesian coordinates to isometric coordinates
	 * 
	 * @param point The PointDouble in cartesian space
	 * @return The PointDouble in isometric space
	 */
	public PointDouble cartesianToIsometric(PointDouble point){
		PointDouble returnPoint = new PointDouble(0,0);
		
		returnPoint.x = point.x - point.y;
		returnPoint.y = (point.x + point.y) / 2;
		
		return(returnPoint);
	}
	
	/**
	 * checks to see whether a straight line between a start and end point is walkable.
	 * @param start The PointDouble at the start of the path.
	 * @param end The PointDouble at the end of the path
	 * @return boolean true if the entire path is walkable, false otherwise.
	 */
	public boolean isPathWalkable(PointDouble start, PointDouble end){
		
		int loops = (int) getDistanceBetweenPoints(start,end);
		double xPos = start.x;
		double yPos = start.y;
		
		double xInc = (start.x - end.x)/loops;
		double yInc = (start.y - end.y)/loops;
		
		for(int i = 0; i < loops; ++i){
			if(!getTileAtWorldPosition(start.x,start.y).isWalkable()){
				return false;
			}
			xPos += xInc;
			yPos += yInc;
		}
		return true;
	}
	
	/**
	 * Returns the distance between two points.
	 * @param start The PointDouble at the start of the line.
	 * @param end The PointDouble at the end of the line.
	 * @return The distance between both PointDoubles
	 */
	protected double getDistanceBetweenPoints(PointDouble start, PointDouble end){
		double dx = start.x - end.x;
		double dy = start.y - end.y;
		
		return Math.sqrt(dx*dx + dy * dy);
	}
	
	/**
	 * Converts cartesian coordinates to isometric coordinates.
	 * @param point The point in isometric space.
	 * @return point The point in cartesian space.
	 */
	public Point isometricToCartesian(Point point){
		Point returnPoint = new Point(0,0);
		
		returnPoint.x = (2 * point.y + point.x) / 2;
		returnPoint.y = (2 * point.y - point.x) / 2;
		
		return(returnPoint);
	}
	
	public double getDeltaTime() {
		return deltaTime;
	}

	public void setDeltaTime(double deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	public List<AnimatedEffect> getAnimatedEffects() {
		return animatedEffects;
	}

	public void setAnimatedEffects(List<AnimatedEffect> animatedEffects) {
		this.animatedEffects = animatedEffects;
	}
	
	public void addAnimatedEffect(AnimatedEffect effect){
		this.animatedEffects.add(effect);
	}
	
	public void removeAnimatedEffect(AnimatedEffect effect){
		this.animatedEffectsToRemove.add(effect);
	}

	public List<LifeForm> getLifeForms() {
		return lifeForms;
	}

	public List<SheepController> getSheepControllers() {
		return sheepControllers;
	}
	
	public void addFood(Food foodItem){
		food.add(foodItem);
	}

	public List<Food> getFood() {
		return food;
	}
	
	public WorldConfig getConfig() {
		return config;
	}

	public void setConfig(WorldConfig config) {
		this.config = config;
	}

	/**
	 * adds a life form to the list of life forms that need removing. 
	 * This prevents errors that occur when removing an object during a cycle. 
	 * @param lifeForm The LifeForm to be removed.
	 */
	public void removeLifeForm(LifeForm lifeForm) {
		lifeFormsToRemove.add(lifeForm);
	}
	
	public void removeFood(Food foodItem){
		foodToRemove.add(foodItem);
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void pause(){
		paused = true;
	}
	public void play(){
		paused = false;
	}


}


