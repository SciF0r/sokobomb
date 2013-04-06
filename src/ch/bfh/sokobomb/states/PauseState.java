package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.model.Coordinate;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuButton;

/**
 * Shows the pause screen
 *
 * @author Christoph Bruderer
 */
public class PauseState extends State {

	private MenuButton mb;
	private Menu m;

	public PauseState() {
		
		try {
			this.m = new Menu("....Titel.....", null);
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
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
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();
		 
		m.draw();
	}
}