package uk.ac.reading.xj008217.entities;

import java.awt.Point;

import uk.ac.reading.xj008217.utilities.PointDouble;

/**
 * Entity describes anything that can exist in a world with a specific location within that world.
 * 
 * @author xj008217
 */
public abstract class Entity {
	
	protected PointDouble position;

	public PointDouble getPosition() {
		return position;
	}

	public void setPosition(PointDouble position) {
		this.position = position;
	}

}
