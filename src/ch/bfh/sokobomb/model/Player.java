package ch.bfh.sokobomb.model;

import ch.bfh.sokobomb.util.Tiles;

/**
 * The player
 *
 * @author Denis Simonet
 */
public class Player extends FieldItem {

	public Player() {
		this.setImage(Tiles.PLAYER);
	}
}