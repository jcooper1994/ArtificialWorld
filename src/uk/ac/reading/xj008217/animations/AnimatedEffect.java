package uk.ac.reading.xj008217.animations;

import java.awt.image.BufferedImage;

import uk.ac.reading.xj008217.World;
import uk.ac.reading.xj008217.entities.Entity;
import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * Displays an animated sequence of images on the screen.
 * @author Jon
 *
 */
public class AnimatedEffect extends Entity{

	private int currentFrame = 0;
	private int endFrame = 10;
	private BufferedImage[] images;
	private double secondsPerFrame = 0.1;
	private double secondsPassed = 0.0;
	private World world;
	
	/**
	 * Creates a new animated effect with the specified images, position and in the specified world.
	 * @param animation 
	 * @param position
	 * @param world
	 */
	public AnimatedEffect(BufferedImage[] animation,PointDouble position,World world){
		this.images = animation;
		this.endFrame = animation.length-1;
		this.setPosition(position);
		this.world = world;
	}
	
	/**
	 * Updates the animation, moving to the next frame on if enough time has passed.
	 */
	public void update(){
		secondsPassed += world.getDeltaTime();
		
		currentFrame = (int) (secondsPassed/secondsPerFrame);
		
		if(currentFrame > endFrame){
			world.removeAnimatedEffect(this);
		}
	}


	/**
	 * Returns the animations current frame
	 * @return A BufferedImage of the current frame.
	 */
	public BufferedImage getImage() {
		if(currentFrame <= endFrame){
			return images[currentFrame];
		}else{
			return images[endFrame];
		}
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}


	public double getSecondsPerFrame() {
		return secondsPerFrame;
	}

	public void setSecondsPerFrame(double secondsPerFrame) {
		this.secondsPerFrame = secondsPerFrame;
	}
	
	
	
}
