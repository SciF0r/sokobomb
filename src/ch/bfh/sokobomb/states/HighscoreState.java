package ch.bfh.sokobomb.states;

import java.io.IOException;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.datamapper.Highscore;
import ch.bfh.sokobomb.model.HighscoreItem;
import ch.bfh.sokobomb.model.Menu;
import ch.bfh.sokobomb.model.MenuItem;

/**
 * Shows the current highscore
 *
 * @author Denis Simonet
 */
public class HighscoreState extends State {

	final public static int RESUME_GAME = 1;
	final public static int RESET_LIST  = 2;

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
			this.highscoreMenu.addMenuItem("Resume game", HighscoreState.RESUME_GAME);
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

	/**
	 * @param action Perform action
	 */
	public void performAction(int action){
		switch (action){
			case HighscoreState.RESUME_GAME:
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