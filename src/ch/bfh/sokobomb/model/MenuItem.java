package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.model.coordinate.Coordinate;

/**
 * A menu item
 * 
 * @author Christoph Bruderer
 *
 */
public class MenuItem {

	final public static int NO_ACTION = Integer.MIN_VALUE;

	final private String text;
	final private int action;

	private Coordinate position;
	private Coordinate maxCoordinate;

	public MenuItem(String text, int action, Coordinate position, int width, int height) {
		this.text     = text;
		this.action   = action;
		this.position = position;

		this.maxCoordinate = new Coordinate(
			position.getX() + width,
			position.getY() + height
		);
	}

	public String getText() {
		return this.text;
	}

	public int getAction() {
		return this.action;
	}

	public Coordinate getMinCoordinate() {
		return this.position;
	}

	public Coordinate getMaxCoordinate() {
		return this.maxCoordinate;
	}

	public boolean containsCoordinate(Coordinate coord) {
		return coord.getX() > this.getMinCoordinate().getX() && coord.getX() < this.getMaxCoordinate().getX() &&
               coord.getY() > this.getMinCoordinate().getY() && coord.getY() < this.getMaxCoordinate().getY();
	}
}