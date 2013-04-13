package ch.bfh.sokobomb.model.coordinate;

import ch.bfh.sokobomb.util.Tiles;

/**
 * Stores a coordinate
 *
 * @author Denis Simonet
 */
public class Coordinate {

	final private int x;
	final private int y;
	final private int hash;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;

    	// Cantor pairing function http://en.wikipedia.org/wiki/Cantor_pairing_function
		this.hash = (int)(0.5 * (x + y) * (x + y + 1) + y);
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

		if (object == null) {
			return false;
		}
		else if (!(object instanceof Coordinate)) {
			return false;
		}

		Coordinate coordinate = (Coordinate)object;

		if (this.hashCode() != coordinate.hashCode()) {
			return false;
		}

		return true;
	}

    @Override
    public int hashCode() {
    	return this.hash;
    }
}