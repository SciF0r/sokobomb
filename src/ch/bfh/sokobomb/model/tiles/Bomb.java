package ch.bfh.sokobomb.model.tiles;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.util.Tiles;


/**
 * A bomb
 *
 * @author Denis Simonet
 */
public class Bomb extends DijkstraNode {

	public Bomb(int type, TileCoordinate coordinate) {
		super(type, coordinate);
		this.setImage(Tiles.BOMB);
	}
}