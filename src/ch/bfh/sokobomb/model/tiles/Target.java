package ch.bfh.sokobomb.model.tiles;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.path.DijkstraNode;
import ch.bfh.sokobomb.util.Tiles;


/**
 * A target field
 *
 * @author Denis Simonet
 */
public class Target extends DijkstraNode {

	public Target(int type, TileCoordinate coordinate) {
		super(type, coordinate);
		this.setImage(Tiles.TARGET);
	}
}