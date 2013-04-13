package ch.bfh.sokobomb.field;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import org.lwjgl.opengl.Display;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Bomb;
import ch.bfh.sokobomb.model.tiles.Floor;
import ch.bfh.sokobomb.model.tiles.Player;
import ch.bfh.sokobomb.model.tiles.Target;
import ch.bfh.sokobomb.model.tiles.Tile;
import ch.bfh.sokobomb.model.tiles.Wall;
import ch.bfh.sokobomb.parser.Parser;
import ch.bfh.sokobomb.parser.Token;
import ch.bfh.sokobomb.solver.DijkstraNode;

/**
 * A general field with items, bombs and player, cache, etc.
 *
 * @author Denis Simonet
 */
public abstract class Field implements Cloneable {

	protected Stack<Field> fieldHistory = new Stack<Field>();
	protected LinkedList<Bomb> bombs    = new LinkedList<Bomb>();
	protected LinkedList<Tile> targets  = new LinkedList<Tile>();
	protected FieldCache cache          = new FieldCache();
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
	public void addNode(DijkstraNode node) {
		this.cache.addNode(node);
	}

	/**
	 * Removes a field item from the field
	 *
	 * @param node The coordinate where the node shall be removed
	 */
	public void removeNode(TileCoordinate coordinate) {
		this.cache.removeNode(coordinate);
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

		this.cache.clear();
		this.bombs.clear();
		this.fieldHistory.clear();
	}

	/**
	 * Draw the field according to state  
	 */
	public void draw() {
		Application.getStateController().pollInput();
		Application.getStateController().draw();
		Display.sync(60);
	};

	/**
	 * Draw the field (tiles, bombs, player)
	 *
	 * @throws IOException 
	 */
	public void drawField() throws IOException {
		this.cache.draw();

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
	public void addTileByToken(Token token) {
		Player player     = null;
		Bomb bomb         = null;
		DijkstraNode node = null;

		switch (token.type) {
			case Token.WALL:
				node = new Wall(Token.WALL, token.coordinate);
				break;
			case Token.PLAYER_START:
				player = new Player(Token.PLAYER_START, token.coordinate);
				node   = new Floor(Token.FLOOR, token.coordinate);
				break;
			case Token.TARGET:
				node = new Target(Token.TARGET, token.coordinate);
				
				break;
			case Token.BOMB_START:
				node = new Floor(Token.FLOOR, token.coordinate);
				bomb = new Bomb(Token.BOMB_START, token.coordinate);
				break;
			case Token.BOMB_TARGET:
				bomb = new Bomb(Token.BOMB_START, token.coordinate);
				node = new Target(Token.TARGET, token.coordinate);
				break;
			case Token.PLAYER_TARGET:
				player = new Player(Token.PLAYER_TARGET, token.coordinate);
				node   = new Target(Token.TARGET, token.coordinate);
				break;
			case Token.FLOOR:
				node = new Floor(Token.FLOOR, token.coordinate);
				break;
			default:
				throw new RuntimeException(
					"Illegal token"
				);
		}

		if (node != null) {
			this.addNode(node);
		}

		if (bomb != null) {
			this.addBomb(bomb);
		}

		if (player != null) {
			this.addPlayer(player);
		}
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
	 * @param coordinate
	 * @return
	 */
	public boolean mayEnter(TileCoordinate coordinate) {
		try {
			if (this.findBomb(coordinate) != null) {
				return false;
			}

			int type = this.cache.getNodeAtCoordinate(coordinate).getType();
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

	/**
	 * Adds current field to history
	 */
	abstract public void addFieldToHistory();

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Field)) {
			return false;
		}

		Field other = (Field)obj;

		if (!this.bombs.equals(other.bombs)) {
			return false;
		}

		if (this.player.getCoordinate() != other.player.getCoordinate()) {
			return false;
		}

		return true;
	}
}