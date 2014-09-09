package uk.ac.reading.xj008217.entities;

import java.awt.image.BufferedImage;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.animations.AnimatedEffect;
import uk.ac.reading.xj008217.utilities.Images;
import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * A life form is any mobile life form (eg: not plants) that can interact with the world
 * 
 * 
 * @author xj008217
 */
public class LifeForm extends Entity{

	//life form identifier
	private String id;
	//the life forms current display image
	private BufferedImage[] image;
	private BufferedImage corpseImage;
	
	//the world that this life form exists in
	private World world;
	
	//two speeds - when relaxed and when in danger/chasing prey
	private double restSpeed = 0;
	private double panicSpeed = 0; 
	private double restEnergyLoss = 0;
	private double panicEnergyLoss = 0;
	private boolean isPanicked = false;
	
	//variance (to make all life forms slightly different)
	private static double speedVariance = 0.1;

	
	//target location
	private PointDouble target = new PointDouble(0.0,0.0);

	//the direction that the life form is facing (0 - 8 as N - NE - E ... NW)
	private int direction = 0;
	
	//life forms have energy (when energy is 0 the life form dies).
	private double energy = 100.0;
	
	private double foodInCorpse = 100.0;
	
	/**
	 * Initialises the life form.
	 * @param world The world that the life form exists in.
 	 * @param position The position of the life form within the world.
	 */
	LifeForm(World world,PointDouble position){
		this.world = world;
		this.position = position;
		this.target = position;
	}
	
	/**
	 * Calculates one cycle for the life form. One set of actions/movements will be performed when this function is called. 
	 */
	public void update(){
		double energyLoss = (isPanicked) ? panicEnergyLoss*world.getDeltaTime() : restEnergyLoss*world.getDeltaTime();
		energy -= energyLoss;
		//System.out.println(getEnergy());
		if(energy <= 0.0){
			die();
		}

	}
	
	/**
	 * Works out the opposite of which of the 8 cardinal directions the LifeForm is facing relative to a target PointDouble.
	 * @param target The target to face away from.
	 * @return A number from 0-7 representing the direction away from target.
	 */
	public int calculateOppositeDirection(PointDouble target){
		double a = getAngle(target);
		a += 180;
		
		a = (a < 0) ? a += 360 : a;
	    a = (a > 360) ? a -= 360 : a;
		
		
		a = Math.floor(a / 45); // divide the angle by 45 for 8 directions
		
		return (int) a;
	}
	
	/**
	 * Works out in which of the 8 cardinal directions the LifeForm is facing relative to a target PointDouble.
	 * @param target A PointDouble target to point towards.
	 * @return A number from 0-7 representing the direction towards target.
	 */
	public int calculateDirection(PointDouble target){
		double a = getAngle(target);
		
		a = Math.floor(a / 45); // divide the angle by 45 for 8 directions
		
		return (int) a;
	}
	/**
	 * Returns the angle from this LifeForms position to a PointDouble.
	 * @param target The target to measure an angle to.
	 * @return The angle between the target and this LifeForm.
	 */
	public double getAngle(PointDouble target) {
	    double a = (double) Math.toDegrees(Math.atan2(target.y - position.y, target.x - position.x));
	    
	    //correct angle for orientation of worlds north.
	    a += 90;
   
	    a = (a < 0) ? a += 360 : a;
	    a = (a > 360) ? a -= 360: a;
	    
	    return a;
	}
	
	/**
	 * Returns the distance between this LifeForm and another LifeForm.
	 * @param life The life form to measure distance to.
	 * @return The distance between this life form and the life form specified by life.
	 */
	protected double getDistanceToLifeForm(LifeForm life){
		double dx = life.position.x - position.x;
		double dy = life.position.y - position.y;
		
		return Math.sqrt(dx*dx + dy * dy);
	}
	
	/**
	 * Returns the distance between this LifeForm and its target.
	 * @return The distance between this LifeForm and its target.
	 */
	protected double getDistanceToTarget(){
		double dx = target.x - position.x;
		double dy = target.y - position.y;
		
		return Math.sqrt(dx*dx + dy * dy);
	}
	
	/**
	 * Moves the LifeForm away from its current target (eg: when fleeing a predator).
	 * @return True if movement is succesful, false if something is in the way.
	 */
	protected boolean moveAwayFromTarget(){
		//calculate distance between target and current position
		double xMove = position.x - target.x;
		double yMove = position.y - target.y;
		
		//calculate speed required
		double speed = (isPanicked) ? panicSpeed*world.getDeltaTime() : restSpeed*world.getDeltaTime();
		
		double multiplier = speed / Math.sqrt(xMove * xMove + yMove * yMove);
		
		return move((xMove * multiplier),(yMove * multiplier));
		
	}
	
	/**
	 * Moves towards the LifeForm's current target.
	 * @return True if the movement is succesful, false if something is in the way.
	 */
	protected boolean moveTowardsTarget(){
		//calculate distance between target and current position
		double xMove = target.x - position.x;
		double yMove = target.y - position.y;
		
		//calculate speed required
		double speed = (isPanicked) ? panicSpeed*world.getDeltaTime() : restSpeed*world.getDeltaTime();
		
		double multiplier = speed / Math.sqrt(xMove * xMove + yMove * yMove);
		
		return move((xMove * multiplier),(yMove * multiplier));
		
	}
	/**
	 * attempts to move the life form by xVal on the x axis and yVal on the y axis (corrected for isometric view)
	 * @param xVal amount to move on x axis
	 * @param yVal amount to move on y axis
	 * @return boolean true if successful and false if an unwalkable tile is encountered
	 */
	private boolean move(double xVal, double yVal){
		//TODO: Make sure this actually works!
		Tile tile = world.getTileAtWorldPosition(position.x+xVal,position.y+yVal);
		if(tile.isWalkable()){
			position.x += xVal;
			position.y += yVal;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Causes the LifeForm to die.
	 * @return A food object representing the corpse of the LifeForm.
	 */
	public Food die(){
		//instantiate corpse food object
		Corpse corpse = new Corpse(position,corpseImage,world,foodInCorpse);
		world.addFood(corpse);
		world.removeLifeForm(this);
		
		AnimatedEffect blood = new AnimatedEffect(Images.BLOOD_SPLASH,position,world);
		world.addAnimatedEffect(blood);
		
		return(corpse);
	}
	

	/**
	 * sets the rest energy loss attribute and adds a small amount of variance (as specified by speedVariance)
	 * @param restEnergyLoss
	 */
	public void setRestEnergyLoss(double restEnergyLoss) {
		
		double variance = world.nextRandomDouble(speedVariance*2)-speedVariance;
		
		this.restEnergyLoss = restEnergyLoss + (restEnergyLoss * variance);
	}
	public double getPanicEnergyLoss() {
		return panicEnergyLoss;
	}
	/**
	 * sets the panic energy loss attribute and adds a small amount of variance (as specified by speedVariance)
	 * @param panicEnergyLoss
	 */
	public void setPanicEnergyLoss(double panicEnergyLoss) {
		double variance = world.nextRandomDouble(speedVariance*2)-speedVariance;
		
		this.panicEnergyLoss = panicEnergyLoss + (panicEnergyLoss * variance);
	}
	public double getRestSpeed() {
		return restSpeed;
	}
	public void setRestSpeed(int restSpeed) {
		this.restSpeed = restSpeed;
	}
	public double getPanicSpeed() {
		return panicSpeed;
	}
	public void setPanicSpeed(int panicSpeed) {
		this.panicSpeed = panicSpeed;
	}
	public boolean isPanicked() {
		return isPanicked;
	}
	public void setPanicked(boolean isPanicked) {
		this.isPanicked = isPanicked;
	}
	public PointDouble getTarget() {
		return target;
	}
	public void setTarget(PointDouble target) {
		this.target = target;
	}
	public BufferedImage getCorpseImage() {
		return corpseImage;
	}
	public void setCorpseImage(BufferedImage corpseImage) {
		this.corpseImage = corpseImage;
	}
	public double getFoodInCorpse() {
		return foodInCorpse;
	}
	public void setFoodInCorpse(double foodInCorpse) {
		this.foodInCorpse = foodInCorpse;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public BufferedImage[] getImage() {
		return image;
	}
	public void setImage(BufferedImage image[]) {
		this.image = image;
	}
	
	public double getRestEnergyLoss() {
		return restEnergyLoss;
	}
}
