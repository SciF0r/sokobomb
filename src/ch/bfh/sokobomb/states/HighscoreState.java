package ch.bfh.sokobomb.states;

import java.io.IOException;
import java.sql.SQLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.model.Field;
import ch.bfh.sokobomb.model.HighscoreList;
import ch.bfh.sokobomb.util.Highscore;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void draw() throws IOException{
		this.highscoreList.draw();

		Display.update();

		this.pollInput();
	}
}
