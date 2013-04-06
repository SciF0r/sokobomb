package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.MenuButton;

/**
 * Shows the pause screen
 *
 * @author Christoph Bruderer
 */
public class PauseState extends PlayFieldState {

	private MenuButton mb;

	public PauseState() {
		mb = new MenuButton(50, 70, 300, 80, "Return to the Game");
		this.stateId = State.PAUSE;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PLAY);
				break;
		}
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		int x = coordinate.getX();
		int y = coordinate.getY();

		if (mb.getX() < x && (mb.getX() + mb.getWidth()) > x && mb.getY() < y && (mb.getY() + mb.getHeight()) > y) {
			Application.getStateController().setState(State.PLAY);
		}
	}

	@Override
	public void draw() throws IOException {
		this.getField().drawField();
		this.drawTransparentOverlay();
		 
		mb.draw();
	}
}