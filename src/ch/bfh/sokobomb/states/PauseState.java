package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.MenuButton;

/**
 * Shows the pause screen
 *
 * @author Christoph Bruderer
 *
 */
public class PauseState extends State {

	private MenuButton mb;

	public PauseState(Field field) {
		super(field);
		mb = new MenuButton(50, 70, 300, 80, "Return to the Game");
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				super.field.setState(new PlayState(super.field));
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		int x = coordinate.getX();
		int y = coordinate.getY();

		if (mb.getX() < x && (mb.getX() + mb.getWidth()) > x && mb.getY() < y && (mb.getY() + mb.getHeight()) > y) {
			super.field.setState(new PlayState(super.field));
		}
	}

	@Override
	public void draw() throws IOException {
		super.field.drawField();
		super.drawTransparentOverlay();
		 
		mb.draw();
	}
}