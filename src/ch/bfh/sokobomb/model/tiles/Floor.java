package ch.bfh.sokobomb.model.tiles;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.path.DijkstraNode;
import ch.bfh.sokobomb.util.Tiles;


/**
 * A floor field
 *
 * @author Denis Simonet
 */
public class Floor extends DijkstraNode {

	public Floor(int type, TileCoordinate coordinate) {
		super(type, coordinate);
		this.setImage(Tiles.FLOOR);
	}
}