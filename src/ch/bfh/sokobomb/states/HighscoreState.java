package ch.bfh.sokobomb.states;

import java.io.IOException;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.datamapper.Highscore;
import ch.bfh.sokobomb.model.HighscoreList;

/**
 * Shows the current highscore
 *
 * @author Denis Simonet
 */
public class HighscoreState extends PlayFieldState {

	private HighscoreList highscoreList;
	
	public HighscoreState() {
		try {
			Highscore highscore = new Highscore();

			this.highscoreList = new HighscoreList(
				Display.getWidth(),
				Display.getHeight(),
				highscore.getItems()
			);

			super.stateId = State.HIGHSCORE;
		}
		catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
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
		super.getField().drawField();
		super.drawTransparentOverlay();
		this.highscoreList.draw();
	}
}