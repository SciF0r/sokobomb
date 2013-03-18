package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.util.Tiles;

/**
 * A floor field
 *
 * @author Denis Simonet
 */
public class Floor extends FieldItem {

	public Floor() {
		this.setImage(Tiles.FLOOR);
	}
}