package ch.bfh.sokobomb.field;

import java.util.LinkedList;

import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

/**
 * Store the relevant information for undo
 *
 * @author Denis Simonet
 */
public class FieldHistoryItem {

	private LinkedList<TileCoordinate> bombPositions;
	private TileCoordinate playerPosition;
	private FieldCache cache;

	/**
	 * 
	 * @param bombPositions
	 * @param playerPosition
	 * @param cache
	 */
	public FieldHistoryItem(LinkedList<TileCoordinate> bombPositions, TileCoordinate playerPosition, FieldCache cache) {
		this.bombPositions  = bombPositions;
		this.playerPosition = playerPosition;
		this.cache          = cache;
	}

	/**
	 * @return the bombPositions
	 */
	public LinkedList<TileCoordinate> getBombPositions() {
		return bombPositions;
	}

	/**
	 * @return the playerPosition
	 */
	public TileCoordinate getPlayerPosition() {
		return playerPosition;
	}

	/**
	 * @return the cache
	 */
	public FieldCache getCache() {
		return cache;
	}
}