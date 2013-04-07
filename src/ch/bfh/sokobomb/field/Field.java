package ch.bfh.sokobomb.field;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import org.lwjgl.opengl.Display;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.model.Bomb;
import ch.bfh.sokobomb.model.FieldItem;
import ch.bfh.sokobomb.model.Floor;
import ch.bfh.sokobomb.model.Player;
import ch.bfh.sokobomb.model.Target;
import ch.bfh.sokobomb.model.TileCoordinate;
import ch.bfh.sokobomb.model.Wall;
import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;

/**
 * A general field with items, bombs and player, cache, etc.
 *
 * @author Denis Simonet
 */
public abstract class Field implements Cloneable {

	protected Stack<Field> fieldHistory   = new Stack<Field>();
	protected LinkedList<FieldItem> items = new LinkedList<FieldItem>();
	protected LinkedList<Bomb> bombs      = new LinkedList<Bomb>();
	protected FieldCache cache            = null;
	protected Player player;

	/**
	 * Parse a level file
	 *
	 * @param path
	 */
	protected void parse(String path) {
		this.resetObject();

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
	 * @param itemRestart
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
	 * Returns a bomb if it is at a certain position
	 *
	 * @param coordinate
	 * @return
	 */
	public Bomb findBomb(TileCoordinate coordinate) {
		for (Bomb bomb: this.bombs) {
			if (bomb.getCoordinate().equals(coordinate)) {
				return bomb;
			}
		}

		return null;
	}

	/**
	 * Resets object variables
	 */
	public void resetObject() {
		this.player = null;
		this.bombs.clear();
		this.items.clear();
		this.fieldHistory.clear();
	}

	/**
	 * Draw the field according to state  
	 */
	public void draw() {
		Application.getStateController().draw();
		Display.update();
		Application.getStateController().pollInput();
	};

	/**
	 * Draw the field (tiles, bombs, player)
	 *
	 * @throws IOException 
	 */
	public void drawField() throws IOException {
		for (FieldItem item: this.getItems()) {
			item.draw();
		}

		for (Bomb bomb: this.getBombs()) {
			bomb.draw();
		}

		Player player = this.getPlayer();
		if (player != null) {
			player.draw();
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
				item.setType(Token.WALL);
				break;
			case Token.PLAYER_START:
				player = new Player();
				player.setType(Token.PLAYER_START);
				item   = new Floor();
				item.setType(Token.FLOOR);
				break;
			case Token.TARGET:
				item = new Target();
				item.setType(Token.TARGET);
				break;
			case Token.BOMB_START:
				item = new Floor();
				item.setType(Token.FLOOR);
				bomb = new Bomb();
				bomb.setType(Token.BOMB_START);
				break;
			case Token.BOMB_TARGET:
				bomb = new Bomb();
				bomb.setType(Token.BOMB_START);
				item = new Target();
				item.setType(Token.TARGET);
				break;
			case Token.PLAYER_TARGET:
				player = new Player();
				player.setType(Token.PLAYER_START);
				item   = new Target();
				item.setType(Token.TARGET);
				break;
			case Token.FLOOR:
				item = new Floor();
				item.setType(Token.FLOOR);
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
	 * Adds current field to history
	 */
	abstract public void addFieldToHistory();

	/**
	 * Returns whether the player may enter a field
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean mayEnter(TileCoordinate coordinate) {
		try {
			if (this.findBomb(coordinate) != null) {
				return false;
			}

			int type = this.cache.getTypeAtCoordinate(coordinate);
			return type == Token.FLOOR || type == Token.TARGET;
		}
		catch (InvalidCoordinateException e) {
			return false;
		}
	}

	/**
	 * Clone bombs and player
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Field field = (Field)super.clone();

		if (this.player != null) {
			field.player = (Player)this.player.clone();
		}
		
		LinkedList<Bomb> bombs = new LinkedList<Bomb>();
		for (Bomb bomb: this.bombs) {
			bombs.add((Bomb)bomb.clone());
		}
		field.bombs = bombs;

		return field;
	}

	/**
	 * Undo the last move
	 */
	abstract public void undo();
}