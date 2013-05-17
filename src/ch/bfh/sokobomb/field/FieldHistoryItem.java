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

	public FieldHistoryItem(LinkedList<TileCoordinate> bombPositions, TileCoordinate playerPosition) {
		this.bombPositions  = bombPositions;
		this.playerPosition = playerPosition;
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
}