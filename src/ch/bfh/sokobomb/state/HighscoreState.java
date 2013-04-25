package ch.bfh.sokobomb.state;

import java.io.IOException;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.datamapper.Highscore;
import ch.bfh.sokobomb.exception.OutOfBoundsException;
import ch.bfh.sokobomb.model.HighscoreItem;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuItem;
import ch.bfh.sokobomb.model.coordinate.Coordinate;
import ch.bfh.sokobomb.model.coordinate.DeltaCoordinate;

/**
 * Shows the current highscore
 *
 * @author Denis Simonet
 */
public class HighscoreState extends State {

	final public static int BACK = 1;

	private Menu highscoreList = new Menu("Highscore");
	private Menu highscoreMenu = new Menu("");
	
	public HighscoreState() {
		try {
			Highscore highscore = new Highscore();
			for (HighscoreItem item: highscore.getItems()) {
				this.highscoreList.addMenuItem(item.toString(), MenuItem.NO_ACTION);
			}

			this.highscoreList.setSelectable(false);

			this.stateId = State.HIGHSCORE;

			this.highscoreMenu.setMenuOffset(this.highscoreList.getNextY() + 20);
			this.highscoreMenu.addMenuItem("Return to menu", HighscoreState.BACK);
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_ESCAPE:
				Application.getStateController().setState(State.HOME);
				break;
			case Keyboard.KEY_RETURN:
				this.performAction(this.highscoreMenu.getSelectedItem().getAction());
				break;
		}
	}

	@Override
	public void handleMouseMoved(Coordinate coordinate, DeltaCoordinate delta) {
		this.highscoreMenu.selectAtPosition(coordinate);
	}

	@Override
	public void handleLeftClick(Coordinate coordinate) {
		try {
			this.performAction(highscoreMenu.getItemAtPosition(coordinate).getAction());
		} catch (OutOfBoundsException e) {
			// Ignore
		}
	}

	/**
	 * @param action Perform action
	 */
	public void performAction(int action){
		switch (action){
			case HighscoreState.BACK:
				Application.getStateController().setState(State.HOME);
				break;
			case MenuItem.NO_ACTION:
				// Do nothing
				break;
		}
	}

	@Override
	public void draw() throws IOException{
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();
		this.highscoreList.draw();
		this.highscoreMenu.draw();
	}
}