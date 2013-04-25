package ch.bfh.sokobomb.state;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.Application;

/**
 * Shows that the game has ended
 *
 * @author Denis Simonet
 *
 */
public class EndGameState extends State {

	final private String textEndGame = "Finished";
	private AngelCodeFont font;

	public EndGameState() {
		try {
			this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
			this.stateId = State.END_GAME;
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RETURN:
				Application.getFieldController().startGame();
				Application.getStateController().setState(State.PLAY);
				break;
		}
	}

	@Override
	public void draw() throws IOException {
		Application.getFieldController().drawField();
		this.drawTransparentOverlay();

		int titleWidth  = this.font.getWidth(this.textEndGame);
		int titleHeight = this.font.getHeight(this.textEndGame);

		int x = (Display.getWidth()  - titleWidth) / 2;
		int y = (Display.getHeight() - titleHeight) / 2;
		this.font.drawString(x, y, this.textEndGame);
	}
}