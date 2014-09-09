package uk.ac.reading.xj008217.utilities;

/**
 * A point double contains x and y coordinates as doubles.
 * @author xj008217
 *
 */
public class PointDouble {
	public double x;
	public double y;
	
	/**
	 * Initialises a default PointDouble (x = 0.0 and y = 0.0)
	 */
	public PointDouble(){
		x = 0.0;
		y = 0.0;
	}
	
	/**
	 * Initialises a PointDouble
	 * @param x The x value of the PointDouble
	 * @param y The y value of the PointDouble
	 */
	public PointDouble (double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Initialises a PointDouble
	 * @param position The point double to get x and y data from.
	 */
	public PointDouble(PointDouble position) {
		this.x = position.x;
		this.y = position.y;
	}
	
}
