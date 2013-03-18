package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;

public class Field {

	private List<FieldItem> items = new LinkedList<FieldItem>();
	private FieldItem player;

	/**
	 * The constructor sets the player
	 *
	 * @param path The path to the file with the field to be loaded
	 */
	public Field(String path) {
		this.parse(path);
	}

	/**
	 * Draw the field
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		// TODO need some kind of openGL object
		for (FieldItem item: this.items) {
			item.draw();
		}
	}

	/**
	 * Parse a level file
	 *
	 * @param path
	 */
	private void parse(String path) {
		Parser parser = new Parser();
		parser.parse(path, this);
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

	/**
	 * Add a field by token
	 *
	 * @param token
	 * @throws RuntimeException
	 * @return
	 */
	public void addItemByToken(Token token) {
		List<FieldItem> items = new LinkedList<FieldItem>();

		switch (token.type) {
			case Token.WALL:
				items.add(new Wall());
				break;
			case Token.PLAYER_START:
				items.add(new Player());
				items.add(new Floor());
				break;
			case Token.TARGET:
				items.add(new Target());
				break;
			case Token.BOMB_START:
				items.add(new Bomb());
				items.add(new Floor());
				break;
			case Token.BOMB_TARGET:
				items.add(new Bomb());
				items.add(new Target());
				break;
			case Token.PLAYER_TARGET:
				items.add(new Player());
				items.add(new Target());
				break;
			case Token.FLOOR:
				items.add(new Floor());
				break;
			default:
				throw new RuntimeException(
					"Illegal token"
				);
		}

		for (FieldItem item: items) {
			item.setPosition(token.x, token.y);
			this.addItem(item);
			
			if (item.getClass() == Player.class) {
				if (this.player != null) {
					throw new RuntimeException(
						"More than one player defined"
					);
				}

				this.player = item;
			}
		}
	}
}