package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.util.Tiles;

/**
 * A wall field
 *
 * @author Denis Simonet
 */
public class Wall extends FieldItem {

	public Wall() {
		this.setImage(Tiles.WALL);
	}
}