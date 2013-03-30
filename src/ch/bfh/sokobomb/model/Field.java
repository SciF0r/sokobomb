package ch.bfh.sokobomb.model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;
import ch.bfh.sokobomb.states.PlayState;
import ch.bfh.sokobomb.states.State;
import ch.bfh.sokobomb.util.FieldCache;

/**
 * Contains all required information to draw a field and handle input
 *
 * @author Denis Simonet
 */
public class Field implements Cloneable {

	private Stack<Field> fieldHistory   = new Stack<Field>();
	private LinkedList<FieldItem> items = new LinkedList<FieldItem>();
	private LinkedList<Bomb> bombs      = new LinkedList<Bomb>();
	private FieldCache cache            = null;

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
		this.width  = width;
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
	public LinkedList<FieldItem> getItems() {
		return items;
	}

	/**
	 * @return the bombs
	 */
	public LinkedList<Bomb> getBombs() {
		return bombs;
	}

	/**
	 * Undo the last move
	 */
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			Field field = this.fieldHistory.pop();
			this.items  = field.getItems();
			this.bombs  = field.getBombs();
			this.player = field.getPlayer();
		}
	}

	/**
	 * Moves the player in a certain direction
	 *
	 * @param x
	 * @param y
	 */
	public void movePlayer(int dx, int dy) {
		try {
			this.fieldHistory.push((Field)this.clone());
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
		}

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
		else {
			// In the end the player could not move
			this.fieldHistory.pop();
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
		this.cache = new FieldCache(this, width, height);
	}

	/**
	 * @return The field cache
	 */
	public FieldCache getCache() {
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
		try {
			int type = this.cache.getTypeAtCoordinate(coordinate);

			return type == Token.FLOOR || type == Token.TARGET;
		}
		catch (InvalidCoordinateException e) {
			return false;
		}
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

	/**
	 * Return width of the whole window
	 *
	 * @return The width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Return height of the whole window
	 *
	 * @return The height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Calls setPlayerPosition() on the current state
	 *
	 * @param coordinate
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		if (this.state.setPlayerPosition(coordinate)) {
			try {
				this.fieldHistory.push((Field)this.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Clone bombs and player
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Field field = (Field)super.clone();
		field.player = (Player)this.player.clone();

		LinkedList<Bomb> bombs = new LinkedList<Bomb>();
		for (Bomb bomb: this.bombs) {
			bombs.add((Bomb)bomb.clone());
		}
		field.bombs = bombs;

		return field;
	}
}