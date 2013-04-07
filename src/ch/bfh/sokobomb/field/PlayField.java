package ch.bfh.sokobomb.field;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.InvalidCoordinateException;
import ch.bfh.sokobomb.exception.NoNextLevelException;
import ch.bfh.sokobomb.model.Bomb;
import ch.bfh.sokobomb.model.TileCoordinate;
import ch.bfh.sokobomb.parser.Token;
import ch.bfh.sokobomb.states.State;
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
		this.loadNextLevel();
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
			PlayField field = (PlayField)this.fieldHistory.firstElement();
			this.bombs  = field.getBombs();
			this.player = field.getPlayer();
			this.fieldHistory.clear();
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
			this.fieldHistory.push((PlayField)this.clone());
		} catch (CloneNotSupportedException e) {
			System.err.println(e.getMessage());
		}

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
	final private boolean hasWon() {
		try {
			for (Bomb bomb: this.bombs) {
				if (this.cache.getTypeAtCoordinate(bomb.getCoordinate()) != Token.TARGET) {
					return false;
				}
			}
		}
		catch (InvalidCoordinateException e) {
			return false;
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
		try {
			this.fieldHistory.push((PlayField)this.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clone bombs and player
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return (PlayField)super.clone();
	}

	/**
	 * Undo the last move
	 */
	public void undo() {
		if (!this.fieldHistory.isEmpty()) {
			PlayField field = (PlayField)this.fieldHistory.pop();
			this.items  = field.getItems();
			this.bombs  = field.getBombs();
			this.player = field.getPlayer();
		}
	}
}