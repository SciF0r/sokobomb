package ch.bfh.sokobomb.field;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.exception.NoNextLevelException;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;
import ch.bfh.sokobomb.model.tiles.Bomb;
import ch.bfh.sokobomb.parser.FieldToken;
import ch.bfh.sokobomb.state.State;
import ch.bfh.sokobomb.util.Levels;

/**
 * Implements a playable Field and handles levels
 *
 * @author Denis Simonet
 */
public class PlayField extends Field implements Cloneable {

	private Levels levels;

	/**
	 * The constructor sets the player
	 *
	 * @param path The path to the file with the field to be loaded
	 */
	public PlayField() {
		this.startGame();
	}

	/**
	 * Start game
	 */
	public void startGame() {
		this.levels = new Levels();
		try {
			this.loadNextLevel();
		} catch (NoNextLevelException e) {
			// TODO Handle correctly
		}
	}

	/**
	 * Loads the next level
	 *
	 * @return Whether a level was loaded
	 */
	public void loadNextLevel() throws NoNextLevelException {
		this.parse(this.levels.getNextLevel());
	}

	/**
	 * Restart level
	 */
	public void restartLevel() {
		if (!this.fieldHistory.isEmpty()) {
			FieldHistoryItem historyItem = this.fieldHistory.firstElement();

			int i = 0;
			for (TileCoordinate bombCoordinate: historyItem.getBombPositions()) {
				this.bombs.get(i++).setPosition(bombCoordinate);
			}

			this.player.setPosition(historyItem.getPlayerPosition());
			this.fieldHistory.clear();
		}

		this.getTime().reset();
	}

	/**
	 * Moves the player in a certain direction
	 *
	 * @param x
	 * @param y
	 */
	public void movePlayer(int dx, int dy) {
		this.addFieldToHistory();

		TileCoordinate coordinate = new TileCoordinate(
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
			// The player could not move
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
		TileCoordinate coordinate = new TileCoordinate(
			bomb.getPositionX() + dx,
			bomb.getPositionY() + dy
		);

		Bomb newBomb = this.findBomb(coordinate);
		if (newBomb != null) {
			return;
		}

		if (this.mayEnter(coordinate)) {
			bomb.setPosition(coordinate);
		}
	}

	@Override
	public void draw() {
		super.draw();

		if (Application.getStateController().isState(State.PLAY) && this.hasWon()) {
			Application.getStateController().setState(State.WON);
		}
	}

	/**
	 * Verifies whether all bombs are on a target field
	 *
	 * @return Whether the player has won
	 */
	final public boolean hasWon() {
		for (Bomb bomb: this.bombs) {
			try {
				if (this.cache.getNodeAtCoordinate(bomb.getCoordinate()).getType() != FieldToken.TARGET) {
					return false;
				}
			} catch (InvalidCoordinateException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		return true;
	}

	/**
	 * Calls setPlayerPosition() on the current state
	 *
	 * @param coordinate
	 */
	public void setPlayerPosition(TileCoordinate coordinate) {
		Application.getStateController().setPlayerPosition(coordinate);
	}

	@Override
	public void addFieldToHistory() {
		this.fieldHistory.push(
			new FieldHistoryItem(
				this.getBombCoordinates(),
				(TileCoordinate)this.player.getCoordinate().clone(),
				this.getCache()
			)
		);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return (PlayField)super.clone();
	}

	@Override
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			FieldHistoryItem historyItem = this.fieldHistory.pop();

			int i = 0;
			for (TileCoordinate bombPosition: historyItem.getBombPositions()) {
				this.bombs.get(i++).setPosition(bombPosition);
			}

			this.player.setPosition(historyItem.getPlayerPosition());
		}
	}
}