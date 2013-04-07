package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.util.Tiles;

/**
 * Stores a coordinate
 *
 * @author Denis Simonet
 */
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

	public TileCoordinate getTileCoordinate() {
		return new TileCoordinate(
			this.x / Tiles.WIDTH,
			this.y / Tiles.HEIGHT
		);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		else if (object instanceof Coordinate) {
			Coordinate coordinate = (Coordinate)object;

			if (this.x == coordinate.x && this.y == coordinate.y) {
				return true;
			}
	  
		}

		return false;
	}
}