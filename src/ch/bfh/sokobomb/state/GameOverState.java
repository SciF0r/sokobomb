package ch.bfh.sokobomb.state;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.field.PlayField;

/**
 * Show the game over screen
 *
 * @author Christoph Bruderer
 */
public class GameOverState extends State{
	final private String textGameOver = "!!GAME OVER!!\nPress ENTER to restart";
	private AngelCodeFont font;

	public GameOverState() {
		try {
			this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
			
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.stateId = State.GAME_OVER;
	}

	@Override
	public void handleKeyPress(int key) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		switch (key) {
			case Keyboard.KEY_RETURN:
				field.restartLevel();
				Application.getStateController().setState(State.PLAY);
				break;
			case Keyboard.KEY_ESCAPE:
				field.restartLevel();
				Application.getStateController().setState(HOME);
				break;
		}
	}

	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();

		int titleWidth  = this.font.getWidth(this.textGameOver);
		int titleHeight = this.font.getHeight(this.textGameOver);

		int x = (Display.getWidth()  - titleWidth) / 2;
		int y = (Display.getHeight() - titleHeight) / 2;
		this.font.drawString(x, y, this.textGameOver);
	}
}