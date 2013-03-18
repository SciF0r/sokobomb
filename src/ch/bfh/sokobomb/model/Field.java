package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;

public class Field {

	private List<FieldItem> items = new LinkedList<FieldItem>();
	private List<Bomb>      bombs = new LinkedList<Bomb>();
	private Player          player;

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
		for (FieldItem item: this.items) {
			item.draw();
		}

		for (Bomb bomb: this.bombs) {
			bomb.draw();
		}

		this.player.draw();
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
	 * Add a bomb to the field
	 *
	 * @param bomb
	 */
	public void addBomb(Bomb bomb) {
		this.bombs.add(bomb);
	}

	/**
	 * Add a player to the field
	 *
	 * The current implementation only allows one player
	 *
	 * @param player
	 */
	public void addPlayer(Player player) {
		if (this.player != null) {
			throw new RuntimeException(
				"More than one player defined"
			);
		}

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

	/**
	 * Add a field by token
	 *
	 * @param token
	 * @throws RuntimeException
	 * @return
	 */
	public void addItemByToken(Token token) {
		Player    player = null;
		Bomb      bomb   = null;
		FieldItem item   = null;

		switch (token.type) {
			case Token.WALL:
				item = new Wall();
				break;
			case Token.PLAYER_START:
				player = new Player();
				item   = new Floor();
				break;
			case Token.TARGET:
				item = new Target();
				break;
			case Token.BOMB_START:
				item = new Floor();
				bomb = new Bomb();
				break;
			case Token.BOMB_TARGET:
				bomb = new Bomb();
				item = new Target();
				break;
			case Token.PLAYER_TARGET:
				player = new Player();
				item   = new Target();
				break;
			case Token.FLOOR:
				item = new Floor();
				break;
			default:
				throw new RuntimeException(
					"Illegal token"
				);
		}

		if (item != null) {
			item.setPosition(token.x, token.y);
			this.addItem(item);
		}

		if (bomb != null) {
			bomb.setPosition(token.x, token.y);
			this.addBomb(bomb);
		}

		if (player != null) {
			player.setPosition(token.x, token.y);
			this.addPlayer(player);
		}
	}
}