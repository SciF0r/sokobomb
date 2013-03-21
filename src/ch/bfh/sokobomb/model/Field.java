package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;

/**
 * Contains all required information to draw a field
 *
 * @author Denis Simonet
 */
public class Field {

	private List<FieldItem> items = new LinkedList<FieldItem>();
	private List<Bomb>      bombs = new LinkedList<Bomb>();
	private Integer[][]     cache = null;

	private Player player;

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
	 *
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
	 * Getter for player
	 *
	 * @return The player
	 */
	public Player getPlayer() {
		return this.player;
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
	 * Moves the player to a certain position
	 *
	 * @param x
	 * @param y
	 */
	public void movePlayer(int dx, int dy) {
		int newX = player.getPositionX() + dx;
		int newY = player.getPositionY() + dy; 
		
		Bomb bomb = this.findBomb(newX, newY);
		if (bomb != null) {
			this.moveBomb(bomb, dx, dy);
		}

		if (this.mayEnter(newX, newY)) {
			this.player.setPosition(newX, newY);
		}
	}

	/**
	 * Moves the player to a certain position
	 *
	 * @param x
	 * @param y
	 */
	public void moveBomb(Bomb bomb, int dx, int dy) {
		int newX = bomb.getPositionX() + dx;
		int newY = bomb.getPositionY() + dy;

		Bomb newBomb = this.findBomb(newX, newY);
		if (newBomb != null) {
			this.moveBomb(newBomb, dx, dy);
		}

		if (this.mayEnter(newX, newY)) {
			bomb.setPosition(newX, newY);
		}
	}

	/**
	 * Add a field by token
	 *
	 * @param token
	 */
	public void addItemByToken(Token token) {
		Player    player = null;
		Bomb      bomb   = null;
		FieldItem item   = null;

		switch (token.type) {
			case Token.WALL:
				item = new Wall();
				item.tokenType = Token.WALL;
				break;
			case Token.PLAYER_START:
				player = new Player();
				player.tokenType = Token.PLAYER_START;
				item   = new Floor();
				item.tokenType = Token.FLOOR;
				break;
			case Token.TARGET:
				item = new Target();
				item.tokenType = Token.TARGET;
				break;
			case Token.BOMB_START:
				item = new Floor();
				item.tokenType = Token.FLOOR;
				bomb = new Bomb();
				bomb.tokenType = Token.BOMB_START;
				break;
			case Token.BOMB_TARGET:
				bomb = new Bomb();
				bomb.tokenType = Token.BOMB_START;
				item = new Target();
				item.tokenType = Token.TARGET;
				break;
			case Token.PLAYER_TARGET:
				player = new Player();
				player.tokenType = Token.PLAYER_START;
				item   = new Target();
				item.tokenType = Token.TARGET;
				break;
			case Token.FLOOR:
				item = new Floor();
				item.tokenType = Token.FLOOR;
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

	/**
	 * Builds the field cache
	 */
	public void buildFieldCache(int width, int height) {
		this.cache = new Integer[width][height];

		for (FieldItem item: items) {
			this.cache[item.positionX][item.positionY] = item.tokenType;
		}
	}

	/**
	 * Returns whether the player may enter a field
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean mayEnter(int x, int y) {
		if (this.cache.length <= x || this.cache[x].length <= y || x < 1 || y < 1) {
			return false;
		}

		Integer type = this.cache[x][y];
		
		if (type == null || this.findBomb(x, y) != null) {
			return false;
		}

		return type == Token.FLOOR || type == Token.TARGET;
	}

	/**
	 * Returns a bomb if it is at a certain position
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Bomb findBomb(int x, int y) {
		for (Bomb bomb: this.bombs) {
			if (bomb.getPositionX() == x && bomb.getPositionY() == y) {
				return bomb;
			}
		}

		return null;
	}
}