package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;

public class PlayState extends State {

	public PlayState(Field field) {
		super(field);
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RIGHT:
				super.field.movePlayer(1, 0);
				break;
			case Keyboard.KEY_LEFT:
				super.field.movePlayer(-1, 0);
				break;
			case Keyboard.KEY_UP:
				super.field.movePlayer(0, -1);
				break;
			case Keyboard.KEY_DOWN:
				super.field.movePlayer(0, 1);
				break;
			case Keyboard.KEY_ESCAPE:
				super.field.setState(new PauseState(field));
				break;
			case Keyboard.KEY_H:
				super.field.setState(new HighscoreState(field));
				break;
			case Keyboard.KEY_U:
				super.field.undo();
				break;
			case Keyboard.KEY_R:
				super.field.restart();
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		this.setPlayerPosition(coordinate);
	}

	/**
	 * Draw the field
	 *
	 * @throws IOException 
	 */
	public void draw() throws IOException {
		super.field.drawField();
	}

	/**
	 * Moves player to a certain field
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		if (super.field.mayEnter(coordinate)) {
			super.field.setState(new PlayerMovingState(super.field));
			super.field.setPlayerPosition(coordinate);
			super.field.addFieldToHistory();
		}
	}
}