package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.util.Tiles;


/**
 * A bomb
 *
 * @author Denis Simonet
 */
public class Bomb extends FieldItem {

	public Bomb() {
		this.setImage(Tiles.BOMB);
	}
}