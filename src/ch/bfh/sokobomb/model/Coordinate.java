package ch.bfh.sokobomb.model;

public class Coordinate {

	final private int x;
	final private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y coordinate
	 */
	public int getY() {
		return y;
	}

	public String toString() {
		return String.format("%d/%d", this.x, this.y);
	}
}