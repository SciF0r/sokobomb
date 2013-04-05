package ch.bfh.sokobomb.states;

import java.io.IOException;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.datamapper.Highscore;
import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.HighscoreList;

/**
 * Shows the current highscore
 *
 * @author Denis Simonet
 */
public class HighscoreState extends State {

	private HighscoreList highscoreList;
	
	public HighscoreState(Field field) {
		super(field);

		try {
			Highscore highscore = new Highscore();

			this.highscoreList = new HighscoreList(
				super.field.getWidth(),
				super.field.getHeight(),
				highscore.getItems()
			);
		}
		catch (SlickException e) {
			super.field.setState(new PlayState(super.field));
		}
		catch (SQLException e) {
			super.field.setState(new PlayState(super.field));
		}
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
	public void draw() throws IOException{
		super.field.drawField();
		super.drawTransparentOverlay();
		this.highscoreList.draw();
	}
}