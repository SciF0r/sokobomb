package ch.bfh.sokobomb.model;

import java.util.LinkedList;
import java.util.List;

public class Field {

	private List<FieldItem> items = new LinkedList<FieldItem>();
	private FieldItem player;

	/**
	 * The constructor sets the player
	 *
	 * @param player The player object
	 */
	public Field(FieldItem player) {
		this.player = player;
	}

	/**
	 * Adds a field item to the field
	 *
	 * @param item
	 */
	public void addItem(FieldItem item) {
		this.items.add(item);
	}

	/**
	 * Removes a field item from the field
	 *
	 * @param item
	 * @return Whether removal was successful
	 */
	public boolean removeItem(FieldItem item) {
		return this.items.remove(item);
	}

	/**
	 * Moves the player to a certain target
	 *
	 * @param x
	 * @param y
	 */
	public void movePlayer(int x, int y) {
		this.player.setPosition(x, y);
	}
}