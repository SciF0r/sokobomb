package ch.bfh.sokobomb.field;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.model.Header;
import ch.bfh.sokobomb.model.Time;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Bomb;
import ch.bfh.sokobomb.model.tiles.DijkstraNode;
import ch.bfh.sokobomb.model.tiles.Floor;
import ch.bfh.sokobomb.model.tiles.Player;
import ch.bfh.sokobomb.model.tiles.Target;
import ch.bfh.sokobomb.model.tiles.Wall;
import ch.bfh.sokobomb.parser.FieldToken;
import ch.bfh.sokobomb.parser.LevelInformationToken;
import ch.bfh.sokobomb.parser.Parser;

/**
 * A general field with items, bombs and player, cache, etc.
 *
 * @author Denis Simonet
 */
public abstract class Field implements Cloneable {

	protected Stack<FieldHistoryItem> fieldHistory = new Stack<FieldHistoryItem>();
	protected LinkedList<Bomb>        bombs        = new LinkedList<Bomb>();
	protected LinkedList<Target>      targets      = new LinkedList<Target>();
	protected FieldCache              cache        = new FieldCache();

	protected Player player;
	private Header header;
	private Time time;
	private String title;

	/**
	 * Parse a level file
	 *
	 * @param path
	 */
	protected void parse(String path) {
		this.resetObject();

		Parser parser = new Parser();

		parser.parseField(path, this);
		parser.parseLevelInformation(path, this);
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
	 * 
	 * @return time for this field
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}

	/**
	 * 
	 * @return title for this field
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	/**
	 * @return the header for this field
	 */
	public Header getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(Header header) {
		this.header = header;
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
	 * @return the targets
	 */
	public LinkedList<Target> getTargets() {
		return this.targets;
	}

	/**
	 * @return the targets
	 */
	public LinkedList<Target> getFreeTargets() {
		LinkedList<Target> targets = new LinkedList<Target>();

		for (Target target: this.targets) {
			if (this.findBomb(target.getCoordinate()) != null) {
				continue;
			}

			targets.add(target);
		}

		return targets;
	}

	/**
	 * @return the bombs
	 */
	public LinkedList<Bomb> getBombs() {
		return this.bombs;
	}

	/**
	 * @return The coordinates of all bombs
	 */
	public LinkedList<TileCoordinate> getBombCoordinates() {
		LinkedList<TileCoordinate> bombCoordinates = new LinkedList<TileCoordinate>();
		for (Bomb bomb: this.bombs) {
			bombCoordinates.add((TileCoordinate)bomb.getCoordinate().clone());
		}

		return bombCoordinates;
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
		Application.getStateController().processCommands();
		Application.getStateController().draw();
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
	public void addTileByToken(FieldToken token) {
		Player player     = null;
		Bomb bomb         = null;
		DijkstraNode node = null;

		switch (token.type) {
			case FieldToken.WALL:
				node = new Wall(FieldToken.WALL, token.coordinate);
				break;
			case FieldToken.PLAYER_START:
				player = new Player(FieldToken.PLAYER_START, token.coordinate);
				node   = new Floor(FieldToken.FLOOR, token.coordinate);
				break;
			case FieldToken.TARGET:
				node = new Target(FieldToken.TARGET, token.coordinate);
				this.targets.add((Target)node);
				break;
			case FieldToken.BOMB_START:
				node = new Floor(FieldToken.FLOOR, token.coordinate);
				bomb = new Bomb(FieldToken.BOMB_START, token.coordinate);
				break;
			case FieldToken.BOMB_TARGET:
				bomb = new Bomb(FieldToken.BOMB_START, token.coordinate);
				node = new Target(FieldToken.TARGET, token.coordinate);
				break;
			case FieldToken.PLAYER_TARGET:
				player = new Player(FieldToken.PLAYER_TARGET, token.coordinate);
				node   = new Target(FieldToken.TARGET, token.coordinate);
				break;
			case FieldToken.FLOOR:
				node = new Floor(FieldToken.FLOOR, token.coordinate);
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
	 * Set an attribute by token
	 *
	 * @param token
	 */
	public void setAttributeByToken(LevelInformationToken token) {
		switch (token.type) {
			case LevelInformationToken.TIME:
				this.setTime(new Time(token.intValue));
				break;
			case LevelInformationToken.TITLE:
				this.setTitle(token.stringValue);
				break;
			default:
				throw new RuntimeException(
					"Illegal token"
				);
		}
	}

	/**
	 * @return The field cache
	 */
	public FieldCache getCache() {
		return this.cache;
	}

	/**
	 * Returns whether the player may enter a field item according to it's type
	 *
	 * This function doesn't verify whether there is a path to the field
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
			return type == FieldToken.FLOOR || type == FieldToken.TARGET;
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