package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.OutOfBoundsException;
import ch.bfh.sokobomb.field.PlayField;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuItem;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.DeltaCoordinate;

/**
 * Shows the pause screen
 *
 * @author Christoph Bruderer
 */
public class PauseState extends State {

	final public static int RESUME_GAME = 1;
	final public static int RESET_LEVEL = 2;
	final public static int EXIT_GAME   = 3;

	private Menu pauseMenu = new Menu(":: Game paused ::");

	public PauseState() {
		this.pauseMenu.addMenuItem(new MenuItem("Resume Game",      PauseState.RESUME_GAME));
		this.pauseMenu.addMenuItem(new MenuItem("Reset this Level", PauseState.RESET_LEVEL));
		this.pauseMenu.addMenuItem(new MenuItem("End this Game",    PauseState.EXIT_GAME));

		this.stateId = State.PAUSE;
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.PLAY);
				break;
			case Keyboard.KEY_DOWN:
				pauseMenu.nextItem(Menu.DOWN);
				break;
			case Keyboard.KEY_UP:
				pauseMenu.nextItem(Menu.UP);
				break;
			case Keyboard.KEY_RETURN:
				this.performAction(pauseMenu.getSelectedItem().getAction());
				break;
		}
	}

	/**
	 * @param action Perform action
	 */
	public void performAction(int action){
		switch (action){
			case PauseState.EXIT_GAME:
				Application.getStateController().setState(State.HOME);
				break;
			case PauseState.RESET_LEVEL:
				PlayField field = (PlayField)Application.getFieldController().getField();
				field.restartLevel();
				Application.getStateController().setState(State.PLAY);
				break;
			case PauseState.RESUME_GAME:
				Application.getStateController().setState(State.PLAY);
				break;
			case MenuItem.NO_ACTION:
				// Do nothing
				break;
		}
	}

	@Override
	public void handleMouseMoved(Coordinate coordinate, DeltaCoordinate delta) {
		pauseMenu.selectAtPosition(coordinate);
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		try {
			this.performAction(pauseMenu.getItemAtPosition(coordinate).getAction());
		} catch (OutOfBoundsException e) {
			// Ignore
		}
	}

	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();
		this.pauseMenu.draw();
	}
}