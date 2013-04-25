package ch.bfh.sokobomb.model.tiles;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.util.Tiles;


/**
 * A wall field
 *
 * @author Denis Simonet
 */
public class Wall extends DijkstraNode {

	public Wall(int type, TileCoordinate coordinate) {
		super(type, coordinate);
		this.setImage(Tiles.WALL);
	}
}