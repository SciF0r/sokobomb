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
	
	public HighscoreState() {
		try {
			Highscore highscore = new Highscore();
			for (HighscoreItem item: highscore.getItems()) {
				this.highscoreList.addMenuItem(new MenuItem(item.getName(), MenuItem.NO_ACTION));
			}

			this.stateId = State.HIGHSCORE;
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
				Application.getStateController().setState(State.PLAY);
				break;
		}
	}

	@Override
	public void draw() throws IOException{
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();
		this.highscoreList.draw();
	}
}