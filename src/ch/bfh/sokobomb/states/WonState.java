package ch.bfh.sokobomb.states;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;

import ch.bfh.sokobomb.exception.NoNextLevelException;
import ch.bfh.sokobomb.model.Field;

/**
 * Shows that the player has won
 *
 * @author Denis Simonet
 *
 */
public class WonState extends State {

	final private String textWon = "You win!";
	private AngelCodeFont font;

	public WonState(Field field) throws SlickException {
		super(field);

		this.font = new AngelCodeFont("res/font/sokofont.fnt", "res/font/sokofont_0.png");
	}

	@Override
	public void handleKeyPress(int key) {
		switch (key) {
			case Keyboard.KEY_RETURN:
				try {
					super.field.loadNextLevel();
					super.field.setState(new PlayState(super.field));
				}
				catch (NoNextLevelException e) {
					try {
						super.field.setState(new EndGameState(super.field));
					} catch (SlickException e1) {
						System.out.println("Couldn't load EndGameState: " + e1.getMessage());
					}
				}
				break;
		}
	}

	@Override
	public void draw() throws IOException {
		super.field.drawField();
		super.drawTransparentOverlay();

		int titleWidth  = this.font.getWidth(this.textWon);
		int titleHeight = this.font.getHeight(this.textWon);

		int x = (super.field.getWidth()  - titleWidth) / 2;
		int y = (super.field.getHeight() - titleHeight) / 2;
		this.font.drawString(x, y, this.textWon);
	}
}