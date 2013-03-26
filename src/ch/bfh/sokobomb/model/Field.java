package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;
import ch.bfh.sokobomb.states.PlayState;
import ch.bfh.sokobomb.states.State;

/**
 * Contains all required information to draw a field and handle input
 *
 * @author Denis Simonet
 */
public class Field {

	private List<FieldItem> items = new LinkedList<FieldItem>();
	private List<Bomb>      bombs = new LinkedList<Bomb>();
	private Integer[][]     cache = null;

	private Player player;

	/**
	 * The width of the field
	 */
	private int width;

	/**
	 * The height of the field
	 */
	private int height;

	/**
	 * Current state
	 */
	private State state = new PlayState(this);

	/**
	 * The constructor sets the player
	 *
	 * @param path The path to the file with the field to be loaded
	 */
	public Field(String path, int width, int height) {
		this.width = width;
		this.height = height;
		this.parse(path);
		this.state.entry();
		
	}

	/**
	 * Sets a new state
	 *
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
		this.state.entry();
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
	 * @return the items
	 */
	public List<FieldItem> getItems() {
		return items;
	}

	/**
	 * @return the bombs
	 */
	public List<Bomb> getBombs() {
		return bombs;
	}

	/**
	 * Moves the player to a certain position
	 *
	 * @param x
	 * @param y
	 */
	public void movePlayer(int dx, int dy) {

		Coordinate coordinate = new Coordinate(
			player.getPositionX() + dx,
			player.getPositionY() + dy
		);

		Bomb bomb = this.findBomb(coordinate);
		if (bomb != null) {
			this.moveBomb(bomb, dx, dy);
		}

		if (this.mayEnter(coordinate)) {
			this.player.setPosition(coordinate);
		}
	}

	/**
	 * Moves the player to a certain position
	 *
	 * @param x
	 * @param y
	 */
	public void moveBomb(Bomb bomb, int dx, int dy) {
		Coordinate coordinate = new Coordinate(
			bomb.getPositionX() + dx,
			bomb.getPositionY() + dy
		);

		Bomb newBomb = this.findBomb(coordinate);
		if (newBomb != null) {
			this.moveBomb(newBomb, dx, dy);
		}

		if (this.mayEnter(coordinate)) {
			bomb.setPosition(coordinate);
		}
	}

	/**
	 * Draw the field according to state
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		this.state.draw();
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
			item.setPosition(token.coordinate);
			this.addItem(item);
		}

		if (bomb != null) {
			bomb.setPosition(token.coordinate);
			this.addBomb(bomb);
		}

		if (player != null) {
			player.setPosition(token.coordinate);
			this.addPlayer(player);
		}
	}

	/**
	 * Builds the field cache
	 */
	public void buildCache(int width, int height) {
		this.cache = new Integer[width][height];

		for (FieldItem item: items) {
			this.cache[item.getPositionX()][item.getPositionY()] = item.tokenType;
		}
	}

	/**
	 * @return The field cache
	 */
	public Integer[][] getCache() {
		return this.cache;
	}

	/**
	 * Returns whether the player may enter a field
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean mayEnter(Coordinate coordinate) {
		if (this.cache.length <= coordinate.getX() || this.cache[coordinate.getX()].length <= coordinate.getY() || coordinate.getX() < 1 || coordinate.getY() < 1) {
			return false;
		}

		Integer type = this.cache[coordinate.getX()][coordinate.getY()];
		
		if (type == null || this.findBomb(coordinate) != null) {
			return false;
		}

		return type == Token.FLOOR || type == Token.TARGET;
	}

	/**
	 * Returns a bomb if it is at a certain position
	 *
	 * @param coordinate
	 * @return
	 */
	public Bomb findBomb(Coordinate coordinate) {
		for (Bomb bomb: this.bombs) {
			if (bomb.getPositionX() == coordinate.getX() && bomb.getPositionY() == coordinate.getY()) {
				return bomb;
			}
		}

		return null;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Calls setPlayerPosition() on the current state
	 *
	 * @param coordinate
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		this.state.setPlayerPosition(coordinate);
	}
}