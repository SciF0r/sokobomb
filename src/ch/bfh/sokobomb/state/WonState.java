package ch.bfh.sokobomb.state;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;
import ch.bfh.sokobomb.exception.NoNextLevelException;
import ch.bfh.sokobomb.field.PlayField;

/**
 * Shows that the player has won
 *
 * @author Denis Simonet
 *
 */
public class WonState extends State {

	final private String textWon = "You win!";
	private AngelCodeFont font;

	public WonState() {
		try {
			this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}

		this.stateId = State.WON;
	}

	@Override
	public void handleKeyPress(int key) {
		PlayField field = (PlayField)Application.getFieldController().getField();
		switch (key) {
			case Keyboard.KEY_RETURN:
				try {
					field.loadNextLevel();
					Application.getStateController().setState(State.PLAY);
				}
				catch (NoNextLevelException e) {
						Application.getStateController().setState(State.END_GAME);
				}
				break;
		}
	}

	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();

		int titleWidth  = this.font.getWidth(this.textWon);
		int titleHeight = this.font.getHeight(this.textWon);

		int x = (Display.getWidth()  - titleWidth) / 2;
		int y = (Display.getHeight() - titleHeight) / 2;
		this.font.drawString(x, y, this.textWon);
	}
}