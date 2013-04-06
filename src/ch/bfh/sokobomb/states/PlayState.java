package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.util.Tiles;

public class PlayState extends PlayFieldState {

	public PlayState() {
		super.stateId = State.PLAY;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RIGHT:
				super.getField().movePlayer(1, 0);
				break;
			case Keyboard.KEY_LEFT:
				super.getField().movePlayer(-1, 0);
				break;
			case Keyboard.KEY_UP:
				super.getField().movePlayer(0, -1);
				break;
			case Keyboard.KEY_DOWN:
				super.getField().movePlayer(0, 1);
				break;
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PAUSE);
				break;
			case Keyboard.KEY_H:
				Application.getStateController().setState(State.HIGHSCORE);
				break;
			case Keyboard.KEY_D:
				super.getField().resetObject();
				Application.getStateController().setState(State.DESIGN);
				break;
			case Keyboard.KEY_U:
				super.getField().undo();
				break;
			case Keyboard.KEY_R:
				super.getField().restartLevel();
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		this.setPlayerPosition(new Coordinate(
			coordinate.getX() / Tiles.WIDTH,
			coordinate.getY() / Tiles.HEIGHT
		));
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		super.getField().drawField();
	}

	/**
	 * Moves player to a certain field
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		if (super.getField().mayEnter(coordinate)) {
			Application.getStateController().setState(State.PLAYER_MOVING);
			super.getField().setPlayerPosition(coordinate);
			super.getField().addFieldToHistory();
		}
	}
}