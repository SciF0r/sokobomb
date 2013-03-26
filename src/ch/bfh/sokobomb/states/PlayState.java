package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import ch.bfh.sokobomb.model.Bomb;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.FieldItem;

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
		for (FieldItem item: super.field.getItems()) {
			item.draw();
		}

		for (Bomb bomb: super.field.getBombs()) {
			bomb.draw();
		}

		super.field.getPlayer().draw();

		Display.update();

		super.pollInput();
	}

	/**
	 * Moves player to a certain field
	 */
	public void setPlayerPosition(Coordinate coordinate) {
		if (super.field.mayEnter(coordinate)) {
			super.field.setState(new PlayerMovingState(super.field));
			super.field.setPlayerPosition(coordinate);
		}
	}
}