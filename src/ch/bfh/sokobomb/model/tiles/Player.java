package ch.bfh.sokobomb.model.tiles;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.path.DijkstraNode;
import ch.bfh.sokobomb.util.Tiles;


/**
 * The player
 *
 * @author Denis Simonet
 */
public class Player extends DijkstraNode {

	public Player(int type, TileCoordinate coordinate) {
		super(type, coordinate);
		this.setImage(Tiles.PLAYER);
	}
}