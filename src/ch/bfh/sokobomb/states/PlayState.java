package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.util.Tiles;

public class PlayState extends PlayFieldState {

	public PlayState() {
		this.stateId = State.PLAY;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RIGHT:
				this.getField().movePlayer(1, 0);
				break;
			case Keyboard.KEY_LEFT:
				this.getField().movePlayer(-1, 0);
				break;
			case Keyboard.KEY_UP:
				this.getField().movePlayer(0, -1);
				break;
			case Keyboard.KEY_DOWN:
				this.getField().movePlayer(0, 1);
				break;
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PAUSE);
				break;
			case Keyboard.KEY_H:
				Application.getStateController().setState(State.HIGHSCORE);
				break;
			case Keyboard.KEY_D:
				this.getField().resetObject();
				Application.getStateController().setState(State.DESIGN);
				break;
			case Keyboard.KEY_U:
				this.getField().undo();
				break;
			case Keyboard.KEY_R:
				this.getField().restartLevel();
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
		this.getField().drawField();
	}

	/**
	 * Moves player to a certain field
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		if (this.getField().mayEnter(coordinate)) {
			Application.getStateController().setState(State.PLAYER_MOVING);
			this.getField().setPlayerPosition(coordinate);
			this.getField().addFieldToHistory();
		}
	}
}