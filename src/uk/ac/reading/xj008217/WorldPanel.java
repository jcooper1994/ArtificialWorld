package uk.ac.reading.xj008217;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import uk.ac.reading.xj008217.animations.AnimatedEffect;
import uk.ac.reading.xj008217.entities.Food;
import uk.ac.reading.xj008217.entities.LifeForm;
import uk.ac.reading.xj008217.entities.SheepController;
import uk.ac.reading.xj008217.utilities.Images;
import uk.ac.reading.xj008217.utilities.PointDouble;
import uk.ac.reading.xj008217.utilities.ProgramConstants;

/**
 * Displays the simulation as a JPanel
 * 
 * @author xj008217
 *
 */
public class WorldPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	private int originX = 0;
	private int originY = 0;
	
	private World world;
	
	private Thread animationThread;
	private Thread worldThread;
	
	//variables to handle mouse movement
	private int mouseDragStartX = 0;
	private int mouseDragStartY = 0;
	
	/**
	 * Creates a new world panel, initialises a world using the supplied config object.
	 * @param config The config file that describes the world.
	 */
	public WorldPanel(WorldConfig config){
		super();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setBackground(new Color(119,196,255));
		setDoubleBuffered(true);
		world = new World(config);
		animationThread = new Thread(this);
		animationThread.start();
		worldThread = new Thread(world);
		worldThread.start();
	}
	/**
	 * Calls repaint on the JPanel, pausing for ProgramConstants.UPDATE_TIME between repaints. 
	 * This in turn will call the paint function on the WorldPanel, causing the view to update.
	 */
	public void run() {
		while (true) {
			try {
			
				repaint();
				Thread.sleep(ProgramConstants.UPDATE_TIME); 
				
			} catch (InterruptedException e1) {
				
			}
		}
	}
	
	/**
	 * 
	 * Overrides JPanel paint function. Handles all drawing by looking at the current world state and working out
	 * how to draw the results onto the screen in a meaningful way.
	 * 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//TODO: Initialise rendering list (keeps objects in depth order)
		
		int xMax = world.getConfig().getWidth();
		int yMax = world.getConfig().getHeight();
		
		//draw floor tiles first
		for(int y = 0; y < yMax; ++y){
			for(int x = 0; x < xMax; ++x){
				//set isometric drawing positions
				Point drawPoint = new Point(x * ProgramConstants.TILE_WIDTH,y * ProgramConstants.TILE_HEIGHT);
				drawPoint = world.cartesianToIsometric(drawPoint);
				
				//get image by relating the tile enum to an image in the tileImageMap
				BufferedImage newImage = world.getGridPosition(x,y).getImage();
				
				//correct x and y coordinates for images that are higher than 16 pixels
				int imgHeight = newImage.getHeight();
				if(imgHeight > 17){
					drawPoint.y -= (imgHeight-17);
				}
				
				// deduct the panel origin to convert real coordinates into screen coordinates
				drawPoint.x -= originX;
				drawPoint.y -= originY;
				g2d.drawImage(newImage, drawPoint.x, drawPoint.y, this);
			}
		}
		//draw food
		for(int i = 0; i < world.getFood().size(); ++i){
			Food newFood = world.getFood().get(i);
			//selects correct direction image
			BufferedImage newImage = world.getFood().get(i).getImage();
			PointDouble drawPoint = new PointDouble(newFood.getPosition());
			
			//convert to isometric screen coordinates
			drawPoint = world.cartesianToIsometric(drawPoint);
			
			//adjust values for screen pan
			drawPoint.x -= originX;
			drawPoint.y -= originY;
			
			drawPoint.x -= (newImage.getWidth()/2);
			drawPoint.y -= (newImage.getHeight()/2);
			
			
			//Draw the life form to the panel
			g2d.drawImage(newImage, (int) drawPoint.x, (int) drawPoint.y, this);
		}
		//draw life forms
		//TODO optimisations below
		for(int i = 0; i < world.getLifeForms().size(); ++i){
			LifeForm newLifeForm = world.getLifeForms().get(i);
			//selects correct direction image
			BufferedImage newImage = world.getLifeForms().get(i).getImage()[world.getLifeForms().get(i).getDirection()];
			PointDouble drawPoint = new PointDouble(newLifeForm.getPosition().x,newLifeForm.getPosition().y);
			
			//convert to isometric screen coordinates
			drawPoint = world.cartesianToIsometric(drawPoint);
			
			//adjust values for screen pan
			drawPoint.x -= originX;
			drawPoint.y -= originY;
			
			//adjust values for life form image sizes
			drawPoint.x -= (newImage.getWidth()/2);
			drawPoint.y -= (newImage.getHeight()/2);
			
			//Draw the life form to the panel
			g2d.drawImage(newImage, (int) drawPoint.x, (int) drawPoint.y, this);
		}
		
		
		//draw animated effects 
		for(int i = 0; i < world.getAnimatedEffects().size(); ++i){
			AnimatedEffect newEffect = world.getAnimatedEffects().get(i);
			//selects correct direction image
			BufferedImage newImage = world.getAnimatedEffects().get(i).getImage();
			PointDouble drawPoint = new PointDouble(newEffect.getPosition());
			
			//convert to isometric screen coordinates
			drawPoint = world.cartesianToIsometric(drawPoint);
			
			//adjust values for screen pan
			drawPoint.x -= originX;
			drawPoint.y -= originY;
			
			drawPoint.x -= (newImage.getWidth()/2);
			drawPoint.y -= (newImage.getHeight()/2);
			
			
			//Draw the life form to the panel
			g2d.drawImage(newImage, (int) drawPoint.x, (int) drawPoint.y, this);
		}
				
				
		//debug images (only drawn when debug mode is set to true).
		if(ProgramConstants.DEBUG_MODE){
			//draw sheep controllers as pink squares
			for(int i = 0; i < world.getSheepControllers().size(); ++i){
				SheepController newController = world.getSheepControllers().get(i);
				//selects correct direction image
				BufferedImage newImage = Images.DEBUG_SQUARE;
				PointDouble drawPoint = new PointDouble(newController.getPosition().x,newController.getPosition().y);
				
				//convert to isometric screen coordinates
				drawPoint = world.cartesianToIsometric(drawPoint);
				
				//adjust values for screen pan
				drawPoint.x -= originX;
				drawPoint.y -= originY;
				//Draw the life form to the panel
				g2d.drawImage(newImage, (int) drawPoint.x, (int) drawPoint.y, this);
			}
			
			for(int i = 0; i < world.getLifeForms().size(); ++i){
				LifeForm lifeForm = world.getLifeForms().get(i);
				
				PointDouble drawPoint = new PointDouble(lifeForm.getPosition().x,lifeForm.getPosition().y);
				
				//convert to isometric screen coordinates
				drawPoint = world.cartesianToIsometric(drawPoint);
				
				//adjust values for screen pan
				drawPoint.x -= originX;
				drawPoint.y -= originY;
				
				
				g2d.drawString("E:" + (int) lifeForm.getEnergy(), (int) drawPoint.x, (int) drawPoint.y);
			}
			
		}
		
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	
	/**
	 * Sets the JPanels originX and originY, this allows the user to pan through the world by clicking and dragging.
	 */
	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Add bounds so that the user cant leave the world
		originX = (mouseDragStartX - m.getX());
		originY = (mouseDragStartY - m.getY());
	}
	@Override
	public void mouseMoved(MouseEvent m) {
		
	}
	@Override
	public void mouseClicked(MouseEvent m) {
		
	}
	@Override
	public void mouseEntered(MouseEvent m) {
		
	}
	@Override
	public void mouseExited(MouseEvent m) {
		
	}
	/**
	 * Sets the JPanels originX and originY, this allows the user to pan through the world by clicking and dragging.
	 */
	@Override
	public void mousePressed(MouseEvent m) {
		mouseDragStartX = originX + m.getX();
		mouseDragStartY = originY + m.getY();
	}
	@Override
	public void mouseReleased(MouseEvent m) {

	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}

	
}
