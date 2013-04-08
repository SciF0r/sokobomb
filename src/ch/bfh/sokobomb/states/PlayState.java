package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.TileCoordinate;

public class PlayState extends State {

	public PlayState() {
		this.stateId = State.PLAY;
	}

	@Override
	public void handleKeyPress(int key) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		switch (key) {
			case Keyboard.KEY_RIGHT:
				field.movePlayer(1, 0);
				break;
			case Keyboard.KEY_LEFT:
				field.movePlayer(-1, 0);
				break;
			case Keyboard.KEY_UP:
				field.movePlayer(0, -1);
				break;
			case Keyboard.KEY_DOWN:
				field.movePlayer(0, 1);
				break;
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PAUSE);
				break;
			case Keyboard.KEY_U:
				field.undo();
				break;
			case Keyboard.KEY_R:
				field.restartLevel();
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		this.setPlayerPosition(new Coordinate(
			coordinate.getX(),
			coordinate.getY()
		).getTileCoordinate());
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		Application.getFieldController().drawField();
	}

	/**
	 * Moves player to a certain field
	 */
	public void setPlayerPosition(TileCoordinate coordinate) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		if (field.mayEnter(coordinate)) {
			Application.getStateController().setState(State.PLAYER_MOVING);
			field.setPlayerPosition(coordinate);
			field.addFieldToHistory();
		}
	}
}