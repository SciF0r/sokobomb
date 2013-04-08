package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.OutOfBoundsException;
import ch.bfh.sokobomb.field.DesignField;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuItem;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.DeltaCoordinate;

/**
 * Home screen
 *
 * @author Christoph Bruderer
 */
public class HomeState extends State {

	final public static int START_GAME  = 1;
	final public static int DESIGN_MODE = 2;
	final public static int END_GAME    = 3;
	final public static int HIGHSCORE   = 4;

	private Menu homeMenu = new Menu("!!Welcome to SokoBomb!!");

	public HomeState() {
		this.homeMenu.addMenuItem(new MenuItem("Start Game",     HomeState.START_GAME));
		this.homeMenu.addMenuItem(new MenuItem("Highscore",      HomeState.HIGHSCORE));
		this.homeMenu.addMenuItem(new MenuItem("Level Designer", HomeState.DESIGN_MODE));
		this.homeMenu.addMenuItem(new MenuItem("Exit Game",      HomeState.END_GAME));

		this.stateId = State.HOME;
	}

	/**
	 * @param action Perform action
	 */
	public void performAction(int action){
		switch (action){
			case HomeState.START_GAME:
				Application.getStateController().setState(State.PLAY);
				break;
			case HomeState.HIGHSCORE:
				Application.getStateController().setState(State.HIGHSCORE);
				break;
			case HomeState.DESIGN_MODE:
				Application.getStateController().setState(State.DESIGN);
				Application.getFieldController().setField(new DesignField());
				break;
			case HomeState.END_GAME:
				System.exit(0);
			case MenuItem.NO_ACTION:
				// Do nothing
				break;
		}
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
		case Keyboard.KEY_ESCAPE:
			System.exit(0);
			break;
		case Keyboard.KEY_DOWN:
			homeMenu.nextItem(Menu.DOWN);
			break;
		case Keyboard.KEY_UP:
			homeMenu.nextItem(Menu.UP);
			break;
		case Keyboard.KEY_RETURN:
			this.performAction(homeMenu.getSelectedItem().getAction());
			break;
		}
	}

	@Override
	public void handleMouseMoved(Coordinate coordinate, DeltaCoordinate delta) {
		homeMenu.selectAtPosition(coordinate);
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		try {
			this.performAction(homeMenu.getItemAtPosition(coordinate).getAction());
		} catch (OutOfBoundsException e) {
			// Ignore
		}
	}

	@Override
	public void draw() throws IOException {
		this.drawTransparentOverlay();
		this.homeMenu.draw();
	}
}